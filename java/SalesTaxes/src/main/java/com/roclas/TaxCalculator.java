package com.roclas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by carlos on 3/04/15.
 */
public class TaxCalculator {
    
    private static String regexFreeProducts=".*(chocolate|pills|book|medical|food).*";
    private static String regexImported=".*imported.*";
    private static Pattern pattern = Pattern.compile("^([0-9]+)(.*?)([0-9]+\\.[0-9]+)\\s*$");


    /**
     * decomposes a String into different parts* 
     * @param product
     * @return
     */
    public static ProductLine getProductLine(String product) {
        Matcher m = pattern.matcher(product);
        ProductLine result=new ProductLine();
        if (m.find()){
            result.setAmmount(Integer.parseInt(m.group(1)));
            result.setDescription(m.group(2));
            result.setPrice(Float.parseFloat(m.group(3)));
            result.setTaxRate(getTax(product));
            result.setTax(roundUp2Closest5Cents(result.getTaxRate()*result.getAmmount()*result.getPrice()));
        }
        return result;
    }
    

    /**
     * @param product
     * @return the tax percentage (0 to 1) of the product
     */
    public static float getTax(String product) {
        float rate= (float) 0.0;
       String lowerCaseProduct = product.toLowerCase();
       if(lowerCaseProduct.matches(regexImported))
           rate+=0.05;
       if(!lowerCaseProduct.matches(regexFreeProducts))
           rate+=0.1;
       return rate;
    }

    /**
     * @param number 
     * @return the rounded number UP to the closest 5 cents
     */
    public static float roundUp2Closest5Cents(float number) {
        return ((float) (long) (number* 20 + 0.99)) / 20; //round UP (make sure we really round UP)
    }
    
     /**
     * @param number 
     * @return the rounded number to the closest cent
     */
    public static float round2ClosestCent(float number) {
        int sign=number>0?1:-1;
        return ((float) (long) (number* 100 + 0.5)) / 100;
    }
}
