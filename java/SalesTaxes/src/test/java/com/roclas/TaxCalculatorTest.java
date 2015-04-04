package com.roclas;

import junit.framework.TestCase;

/**
 * Created by carlos on 3/04/15.
 */
public class TaxCalculatorTest  extends TestCase {
    

    public void testRoundingNumbers()
    {
        assertEquals(TaxCalculator.roundUp2Closest5Cents((float)16.31),(float)16.35);
        assertEquals(TaxCalculator.roundUp2Closest5Cents((float)12.2488888),(float)12.25);
    }
    
    public void testGetPrice()
    {
        assertEquals(TaxCalculator.getProductLine("1 chocolate bar at 14.99").getPrice(),(float)14.99);
        assertEquals(TaxCalculator.getProductLine("2asdfasdfasdfasd24.398  ").getPrice(),(float)24.398);
    }
    
    public void testGetAmmount()
    {
        assertEquals(TaxCalculator.getProductLine("1 chocolate bar at 14.99").getAmmount(),1);
        assertEquals(TaxCalculator.getProductLine("2asdfasdfasdfasd24.398  ").getAmmount(),2);
    }
}
