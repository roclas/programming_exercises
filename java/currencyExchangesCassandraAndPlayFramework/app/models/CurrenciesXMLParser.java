package models;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by carlos on 8/03/15
 */
public class CurrenciesXMLParser {
    final static private SAXParserFactory factory = SAXParserFactory.newInstance();
    private SAXParser saxParser;

    //private String currenciesXmlURL="http://localhost/currencies.html";
    List<Currency> list=new ArrayList<Currency>();

    /**
     * Class constructor*
     */
    public CurrenciesXMLParser() {
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an XML string and converts it to a list of Currencies (our entity Class)*
     * @param s
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public List<Currency>readXML(String s) throws ParserConfigurationException, SAXException, IOException {
        final List<Currency>result=new ArrayList<Currency>();
        DefaultHandler handler = new DefaultHandler() {
                Stack<String> elementStack = new Stack<String>();
                Stack<Attributes> attributeStack = new Stack<Attributes>();
                String currentTime=null;
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    elementStack.push(qName);
                    attributeStack.push(attributes);
                    if(qName.equalsIgnoreCase("cube")) {
                        int timeindex = attributes.getIndex("time");
                        if (timeindex == -1 && attributes.getLength()>0) {//if "time" attribute doesn't exist
                            Currency currentCurrency=new Currency();
                            currentCurrency.setColumn1(currentTime);
                            for (int i = 0; attributes.getLength() > i; i++) {
                                String name = attributes.getQName(i);
                                String value =attributes.getValue(i);
                                if (name.equalsIgnoreCase("currency")) {
                                    currentCurrency.setKey(value);
                                } else if (name.equalsIgnoreCase("rate")) {
                                    currentCurrency.setValue(Double.parseDouble(value));
                                }
                            }
                            result.add(currentCurrency);
                        }else currentTime = attributes.getValue(timeindex);
                    }
                }
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    elementStack.pop();
                    attributeStack.pop();
                }
        };
        saxParser.parse(new InputSource(new StringReader(s)), handler);
        return result;
    }
}
