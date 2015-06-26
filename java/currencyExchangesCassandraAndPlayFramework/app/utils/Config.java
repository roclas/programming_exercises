package utils;

/**
 * Created by carlos on 11/03/15.
 * This class contains configuration parameters for the application*
 */
public class Config {
    public static String cassandraHost="127.0.0.1";
    public static String cassandraPort="9160";
    public static String cassandraHostAndPort=String.format("%s:%s",cassandraHost,cassandraPort);

    public static String cassandraCatalog="catalogtest";
    public static String cassandraTable="currencies";
    public static String cassandraTestTable="testcurrencies";

    public static String testCassandraClusterName="testClusterName";
    public static String cassandraClusterName="myCluster";
    public static String currenciesXmlURL="http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    public static String dateFormat="yyyy-MM-dd";
}
