package com.roclas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 4/04/15.
 */
public class ShoppingBasket {
    
    /**
     * reads input string (representing a shopping basket)* 
     * @param input0
     * @return
     */
    public List<ProductLine> readString(String input0) {
        ArrayList<ProductLine> result=new ArrayList<ProductLine>();
        for(String line:input0.split("\\n")){
            result.add(TaxCalculator.getProductLine(line));
        }
        return result;
    }

    /**
     * Calculates whole price* 
     * @param products
     * @return
     */
    public float calculatePrice(List<ProductLine> products) {
        float total=(float)0.0;
        for(ProductLine product:products){
            total+= TaxCalculator.round2ClosestCent(product.getAmmount()*product.getPrice()+product.getTax());
        }
        return total;
    }

    /**
     * Only Calculates taxes* 
     * @param products
     * @return
     */
    public float calculateTax(List<ProductLine> products) {
        float total=(float)0.0;
        for(ProductLine product:products)total+=product.getTax();
        return TaxCalculator.roundUp2Closest5Cents(total);

    }
}
