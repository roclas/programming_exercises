package com.roclas;

import java.util.List;

import junit.framework.TestCase;

 /**
 * Created by carlos on 4/04/15.
 */
public class ShoppingBasketTest extends TestCase {
     
     private String input0 = String.format("%s\n%s\n%s",
        "1 book at 12.49",
        "1 music CD at 14.99",
        "1 chocolate bar at 0.85"
     );

     private String input1 = String.format("%s\n%s",
        "1 imported box of chocolates at 10.00",
        "1 imported bottle of perfume at 47.50"
     );

     private String input2 = String.format("%s\n%s\n%s\n%s",
        "1 imported bottle of perfume at 27.99",
        "1 bottle of perfume at 18.99",
        "1 packet of headache pills at 9.75",
        "1 box of imported chocolates at 11.25"
    );
     
    public void testInterpretLines()
    {
        ShoppingBasket basket=new ShoppingBasket();
        List<ProductLine> products = basket.readString(input0);
        assertEquals(products.get(0).getAmmount(),1);
        assertEquals(products.get(1).getPrice(),(float)14.99);
        assertEquals(products.get(2).getTaxRate(),(float)0.0);
    }

    public void testCalculateBasketTax()
    {
        ShoppingBasket basket=new ShoppingBasket();
        
        List<ProductLine> products = basket.readString(input0);
        assertEquals(basket.calculateTax(products),(float)1.5);

        products = basket.readString(input1);
        assertEquals(basket.calculateTax(products),(float)7.65);
        
        products = basket.readString(input2);
        assertEquals(basket.calculateTax(products),(float)6.70);
        
    }
     
     public void testCalculateBasketPrice()
    {
        ShoppingBasket basket=new ShoppingBasket();
        
        List<ProductLine> products = basket.readString(input0);
        assertEquals(basket.calculatePrice(products),(float)29.83);
        
        products = basket.readString(input1);
        assertEquals(basket.calculatePrice(products),(float)65.15);

        products = basket.readString(input2);
        assertEquals(basket.calculatePrice(products),(float)74.68);
    }

}
