package utils;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncURLFetcher implements Callable<String> {
    static ExecutorService executor = Executors.newFixedThreadPool(10);
    //executor.shutdown();

    /**
     * goes to the third party url and fetches its contents*
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        URL url = new URL(Config.currenciesXmlURL);
        InputStream is = url.openStream();
        int ptr = 0;
        StringBuffer buffer = new StringBuffer();
        while ((ptr = is.read()) != -1) {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }

    public static Future<String> getXML(String url){
        Future<String> future = executor.submit(new AsyncURLFetcher());
        return future;
    }

}
