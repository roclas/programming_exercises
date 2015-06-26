package models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import com.netflix.astyanax.util.RangeBuilder;

import utils.Config;
import utils.DateCalculator;

/**
 * Inspired on: *
 * https://github.com/boneill42/astyanax-quickstart/blob/master/src/main/java/com/github/boneill42/AstyanaxDao.java *
 */
public class CurrencyDao {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyDao.class);
    private Keyspace keyspace;
    private AstyanaxContext<Keyspace> astyanaxContext;
    private static String connectionPoolName="MyConnectionPool";
    private static String select_key_from_currencies_table=
            String.format("select key from %s.%s;",Config.cassandraCatalog,Config.cassandraTable);

    /**
     * Constructor*
     * @param host
     * @param keyspace
     */
    public CurrencyDao(String host, String keyspace) {
        try {
            this.astyanaxContext = new AstyanaxContext.Builder()
                    .forCluster(Config.cassandraClusterName)
                    .forKeyspace(keyspace)
                    .withAstyanaxConfiguration(new AstyanaxConfigurationImpl().setDiscoveryType(NodeDiscoveryType.NONE))
                    .withConnectionPoolConfiguration(
                            new ConnectionPoolConfigurationImpl(connectionPoolName).setMaxConnsPerHost(5)
                                    .setSeeds(host)).withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                    .buildKeyspace(ThriftFamilyFactory.getInstance());

            this.astyanaxContext.start();
            this.keyspace = this.astyanaxContext.getEntity();
            // test the connection
            this.keyspace.describeKeyspace();
        } catch (Throwable e) {
            LOG.warn("Preparation failed.", e);
            throw new RuntimeException("Failed to prepare Cassandra; is your Cassandra server running?", e);
        }
    }

    /**
     * Shuts down Astyanax Context*
     */
    public void cleanup() { this.astyanaxContext.shutdown(); }

    /**
     * Truncates the whole columnFamily (forcing the application to reload everything)*
     * @param columnFamilyName
     * @throws ConnectionException
     */
    public void truncate(String columnFamilyName) throws ConnectionException {
        keyspace.truncateColumnFamily(columnFamilyName);
    }

    /**
     * Writes currency objects to the database.
     * @param columnFamilyName
     * @param currency
     * @throws ConnectionException
     */
    public void writeCurrency(String columnFamilyName, Currency currency)
            throws ConnectionException {
        MutationBatch mutation = keyspace.prepareMutationBatch();
        ColumnFamily<String, String> CF_CURRENCIES= new ColumnFamily<String, String>(
                Config.cassandraTable, // Column Family Name
                StringSerializer.get(), // Key Serializer
                StringSerializer.get()); // Column Serializer
        mutation.withRow(CF_CURRENCIES, currency.getKey()).putColumn(currency.getColumn1(), currency.getValue());
        mutation.executeAsync(); 
    }

    /**
     * Asyncronous fetching of rows*
     * @param columnFamilyName
     * @param rowKey
     * @return
     * @throws ConnectionException
     */
      public ListenableFuture<OperationResult<ColumnList<String>>> readCurrenciesAsync(String columnFamilyName,
              String rowKey)
              throws ConnectionException{
        String startDate = DateCalculator.getDate90DaysAgo();
        String endDate = DateCalculator.getTodaysDate();
        ColumnFamily<String, String> CF_CURRENCIES= new ColumnFamily<String, String>(
                Config.cassandraTable, // Column Family Name
                StringSerializer.get(), // Key Serializer
                StringSerializer.get()); // Column Serializer
          ListenableFuture<OperationResult<ColumnList<String>>> result = keyspace
                  .prepareQuery(CF_CURRENCIES)
                  .getKey(rowKey)
                  .withColumnRange(new RangeBuilder().setStart(startDate).setEnd(endDate).build())
                  .executeAsync();
          return result;
    }

     /**
     * Lists all currency symbols stored in the database *
      * @return
     * @throws ConnectionException
     */
    public ListenableFuture<OperationResult<CqlResult<String, String>>> listSymbolsAsync() throws ConnectionException {
        ColumnFamily<String, String> CF_CURRENCIES= new ColumnFamily<String, String>(
                Config.cassandraTable, // Column Family Name
                StringSerializer.get(), // Key Serializer
                StringSerializer.get()); // Column Serializer
        return keyspace.prepareQuery(CF_CURRENCIES)
                .withCql(String.format(select_key_from_currencies_table,
                        Config.cassandraCatalog,Config.cassandraTable)).executeAsync();
    }

}
