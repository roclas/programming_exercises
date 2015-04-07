package com.roclas;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * the main application 
 * run it without any arguments to see the usage message*
 */
public class App 
{
    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String newline= "\n";

    /**
     * Main method*
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException {
        if(args.length<1){
            String jarName=new java.io.File(App.class.getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getPath())
            .getName();
            System.out.println(String.format("Proper Usage is: java -jar %s filename",jarName));
            System.exit(0);
        }
        ShoppingBasket basket=new ShoppingBasket();
        List<ProductLine> products = basket.readString(readSmallTextFile(args[0]));
        float price = basket.calculatePrice(products);
        System.out.println(price);
    }
    
  /**
   Note: the javadoc of Files.readAllLines says it's intended for small
   files. But its implementation uses buffering, so it's likely good
   even for fairly large files.
  */
  static String readSmallTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
      List<String> strlist= Files.readAllLines(path, ENCODING);
      StringBuffer sb = new StringBuffer();
      for (String s : strlist) {
          sb.append(s);
          sb.append(newline);
       }
      return sb.toString();
  }
}
