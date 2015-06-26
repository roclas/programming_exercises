import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import models.CurrenciesXMLParser;
import models.Currency;
import play.test.UnitTest;
import utils.AsyncURLFetcher;
import utils.Config;

/**
* Testing that calls to the API can retrieve Currency EntityEntity Objects
*/
public class ModelsTests extends UnitTest{

    /**
     * Tests third party api XML parsing into a Currency (entity) List*
     * the XML will be fetched asyncronously*
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @Test
    public void listCurrenciesFromExternalAPI()
            throws IOException, ExecutionException, InterruptedException, ParserConfigurationException, SAXException {
        CurrenciesXMLParser myCurrenciesXMLParser = new CurrenciesXMLParser();

        final CurrenciesXMLParser currenciesXMLParser = new CurrenciesXMLParser();
        Future<String> fut= AsyncURLFetcher.getXML(Config.currenciesXmlURL);

        List<Currency> list = currenciesXMLParser.readXML(fut.get());
        assert (list.size() > 0);
        for(Currency c:list)System.out.println(c.toString());
    }

}
   

