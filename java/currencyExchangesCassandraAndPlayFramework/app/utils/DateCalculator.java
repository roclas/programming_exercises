package utils;

import java.util.*;
import java.text.*;
/**
 * Created by carlos on 8/03/15.
 */
public class DateCalculator {
   private static DateFormat df = new SimpleDateFormat(Config.dateFormat); //TODO: change date format to
   // config file

    /**
     * returns the date 90 days ago*
     * @return
     */
   public static String getDate90DaysAgo() {
      GregorianCalendar today= new GregorianCalendar();
      today.add(GregorianCalendar.DATE, -89);
      Date d = today.getTime();
      return df.format(d);
   }

    /**
     * returns yesterdays date*
     * @return
     */
   public static String getYesterday() {
      GregorianCalendar today= new GregorianCalendar();
      today.add(GregorianCalendar.DATE, -1);
      Date d = today.getTime();
      return df.format(d);
   }
 
    /**
     * returns today's date*
     * @return
     */
   public static String getTodaysDate() {
       return df.format(new Date());
   }

   
}