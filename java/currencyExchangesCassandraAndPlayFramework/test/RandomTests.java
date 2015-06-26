import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

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
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import play.test.UnitTest;
import utils.AsyncURLFetcher;
import utils.Config;

/**
* Proofs of concept, that helped me to build the CurrencyDao
*/
public class RandomTests extends UnitTest{

    @Test
    public void asyncHTTPRequest() throws ExecutionException, InterruptedException {
        AsyncURLFetcher urlFetcher= new AsyncURLFetcher();
        for(int i=0;i<10;i++){
            Future<String> fut= urlFetcher.getXML(Config.currenciesXmlURL);
            System.out.println(new Date()+ "::"+fut.get().substring(0,5)); //blocks call
            System.out.println("---");
        }
    }

    /*
    @Test
    public void createTable() throws ConnectionException {
                AstyanaxContext<Cluster> clusterContext = new AstyanaxContext.Builder()
                .forCluster(Config.testCassandraClusterName)
                .forKeyspace("catalogtest")
                .withAstyanaxConfiguration(
                        new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.NONE))
                .withConnectionPoolConfiguration(
                        new ConnectionPoolConfigurationImpl("MyConnectionPool")
                                .setMaxConnsPerHost(1).setSeeds(
                                "127.0.0.1:9160"))
                .buildCluster(ThriftFamilyFactory.getInstance());
        clusterContext.start();
        Cluster cluster = clusterContext.getEntity();
        cluster.addColumnFamily(cluster.makeColumnFamilyDefinition()
                        .setName("currencies")
                        .setKeyspace("catalogtest")
        );
    }
    */

    @Test
    public void populateCassandra() throws ConnectionException {
        AstyanaxContext<Keyspace> context = createContext();
        context.start();
        Keyspace keyspace = context.getEntity();

        ColumnFamily<String, String> CF_CURRENCIES= new ColumnFamily<String, String>(
                Config.cassandraTestTable, // Column Family Name
                StringSerializer.get(), // Key Serializer
                StringSerializer.get()); // Column Serializer

        MutationBatch m = keyspace.prepareMutationBatch();

        m.withRow(CF_CURRENCIES, "GBP")
                .putColumn("20150301", 1.6)
                .putColumn("20150302", 1.3)
                .putColumn("20150303", 1.8)
                .putColumn("20150304", 1.9)
                .putColumn("20150305", 1.8)
                .putColumn("20150306", 1.8)
                .putColumn("20150307", 1.6)
                .putColumn("20150308", 1.5);

        try {
            OperationResult<Void> result = m.execute();
        } catch (ConnectionException e) {
            System.out.println(e);
        }

    }


    @Test
    public void readFromCassandra() throws ConnectionException {
        AstyanaxContext<Keyspace> context = createContext();
        context.start();
        Keyspace keyspace = context.getEntity();

        ColumnFamily<String, String> CF_CURRENCIES= new ColumnFamily<String, String>(
                Config.cassandraTestTable, // Column Family Name
                StringSerializer.get(), // Key Serializer
                StringSerializer.get()); // Column Serializer

        OperationResult<ColumnList<String>> result = keyspace.prepareQuery(CF_CURRENCIES)
                .getKey("USD")
                //.withColumnRange(new RangeBuilder().setStart("20150301").setEnd("20150305").build())
                .execute();
        ColumnList<String> columns = result.getResult();
        System.out.println(
           String.format(
                   //"...RangeBuilder().setStart(\"20150301\").setEnd(\"20150305\").build()) returns %d results" ,
                   "...getkey(\"USD\") returns %d results",
                   columns.getColumnNames().size())
        );
        OperationResult<CqlResult<String, String>> result2 = keyspace.prepareQuery(CF_CURRENCIES)
                .withCql(String.format("select key from %s.currencies;",Config.cassandraCatalog,
                        Config.cassandraTestTable)).execute();

        for(Row<String, String> r:result2.getResult().getRows()){
            System.out.println(String.format("found currency %s",r.getKey()));
        }

    }

    private AstyanaxContext<Keyspace> createContext() {
       AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(Config.testCassandraClusterName)
                .forKeyspace(Config.cassandraCatalog)
                .withAstyanaxConfiguration(
                        new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.NONE))
                .withConnectionPoolConfiguration(
                        new ConnectionPoolConfigurationImpl("MyConnectionPool")
                                .setMaxConnsPerHost(1).setSeeds(Config.cassandraHostAndPort))
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

       return context;
    }

}

