package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.xml.parsers.ParserConfigurationException;

import org.mortbay.log.Log;
import org.xml.sax.SAXException;

import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;

import models.CurrenciesXMLParser;
import models.Currency;
import models.CurrencyDao;
import play.libs.F;
import play.mvc.Controller;
import utils.AsyncURLFetcher;
import utils.Config;
import utils.DateCalculator;

public class Service extends Controller {
    final static CurrencyDao DAO=new CurrencyDao(Config.cassandraHost,Config.cassandraCatalog);
    final static CurrenciesXMLParser CURRENCIES_XML_PARSER = new CurrenciesXMLParser();

    /**
     * Returns the exchanges for the last 90 days, for a given currency symbol* 
     * it first looks in the database, and if it doesn't find what it wants, goes to the third party API* 
     * Everything is done in an asyncronous way*
     * @param symbol
     * @throws ConnectionException
     */
    public static void exchange(String symbol) throws ConnectionException {
        final String symb=symbol;
        final ListenableFuture<OperationResult<ColumnList<String>>>f1 = DAO.readCurrenciesAsync(Config.cassandraTable, symb);
        //Asyncronous callback for querying Cassandra
        final F.Action<OperationResult<ColumnList<String>>> callback1 =
                new F.Action<OperationResult<ColumnList<String>>>() {
            @Override public void invoke(OperationResult<ColumnList<String>> result) {
                final List<Currency> list = new ArrayList<Currency>();
                for (Column<String> e : result.getResult()) {
                    Currency currency = new Currency();
                    currency.setKey("symbol");
                    currency.setColumn1(e.getName());
                    currency.setValue(e.getDoubleValue());
                    list.add(currency);
                }
                if (list.isEmpty()
                        ||list.get(0).getColumn1().compareToIgnoreCase(DateCalculator.getDate90DaysAgo())>0
                        ||list.get(list.size()-1).getColumn1().compareToIgnoreCase(DateCalculator.getYesterday())<0
                        //||true //uncoment line if you want to force reloading from third party API
                    ) {
                            Log.info("we don't cover the whole range we want; Reloading from 3rd party API...");
                    final ArrayList<Currency> list2 = new ArrayList<Currency>();
                    AsyncURLFetcher urlFetcher = new AsyncURLFetcher();
                    Future<String> f2 = urlFetcher.getXML(Config.currenciesXmlURL);
                    //Asyncronous callback for querying the 3rd party API
                    F.Action<String> callback2 = new F.Action<String>() {
                        @Override public void invoke(String result) {
                            try {
                                for (Currency c : CURRENCIES_XML_PARSER.readXML(result)) {
                                    DAO.writeCurrency(Config.cassandraTable, c);
                                    if (c.getKey().equals(symb))list2.add(c);
                                }
                            } catch (
                                    ParserConfigurationException
                                            | SAXException
                                            | ConnectionException
                                            | IOException
                                            e
                                    ) {
                                e.printStackTrace();
                            }
                            renderArgs.put(Config.cassandraTable, list2);
                            render();
                        }
                    };
                    //wait (ASYNCRONOUSLY) for third party API
                    await(f2, callback2);
                }
                renderArgs.put(Config.cassandraTable, list);
                render();
           }
        };
        //wait (ASYNCRONOUSLY) for cassandra
        await(f1, callback1);
    }

    /**
     * returns all the existing symbols in the currencies table*
     * they are fetched in an asyncronous way*
     * @throws ConnectionException
     */
    public static void symbols() throws ConnectionException {
        ListenableFuture<OperationResult<CqlResult<String, String>>> future = DAO.listSymbolsAsync();
        F.Action<OperationResult<CqlResult<String, String>>> callback=
                new F.Action<OperationResult<CqlResult<String, String>>>() {
                    @Override public void invoke(OperationResult<CqlResult<String, String>> result) {
                    List<String> list=new ArrayList<String>();
                    for(Row<String, String> r:result.getResult().getRows()){
                        list.add(r.getKey());
                    }
                    renderArgs.put("symbols", list);
                    render();
                    }
                };
        await(future,callback);
    }

    /**
     * truncates the cassandra table (we will use it to force reloading the data from the third party API)*
     * @throws ConnectionException
     */
    public static void truncate() throws ConnectionException {
        DAO.truncate(Config.cassandraTable);
        //dao.cleanup();
        renderText("ok");
    }
}