import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;

import models.Currency;
import models.CurrencyDao;
import play.test.UnitTest;
import utils.Config;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class CurrencyDaoTests extends UnitTest{

    static CurrencyDao dao =new CurrencyDao(Config.cassandraHostAndPort, Config.cassandraCatalog);

    /**
     * basic test to check that we can instert into database and nothing crashes*
     * @throws ConnectionException
     */
    @Test
    public void insertCurrencyWithDao() throws ConnectionException {
        dao.truncate(Config.cassandraTestTable); // clean the tests table
        Currency currency= new Currency();
        currency.setKey("USD");
        currency.setValue(0.5);
       //dao.writeCurrency("currencies", "USD", "20120101", 1.5);
        try {
            currency.setColumn1("20110101");
            dao.writeCurrency(Config.cassandraTestTable, currency);
            currency.setColumn1("20110102");
            dao.writeCurrency(Config.cassandraTestTable, currency);
            currency.setColumn1("20110103");
            dao.writeCurrency(Config.cassandraTestTable, currency);
            currency.setColumn1("20110104");
            dao.writeCurrency(Config.cassandraTestTable, currency);
        }catch(ConnectionException e){
            assert(false);
        }
        assert(false);
    }

    /**
     * It should be able to asyncronously read from the test table* 
     * @throws ConnectionException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void readCurrencyWithDao() throws ConnectionException, ExecutionException, InterruptedException {
        ListenableFuture<OperationResult<ColumnList<String>>> f = dao
                .readCurrenciesAsync(Config.cassandraTestTable, "USD");
        boolean found = false;
        for(Column<String> c:f.get().getResult()){
            found=true;
            System.out.println(c);
        }
        assert(found);
    }

    /**
     * It should be able to be able to list symbols*
     * @throws ConnectionException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void listSymbolsWithDao() throws ConnectionException, ExecutionException, InterruptedException {
        ListenableFuture<OperationResult<CqlResult<String, String>>> f = dao.listSymbolsAsync();
        assert (f.get().getResult().getNumber()>0);
    }
 

}

