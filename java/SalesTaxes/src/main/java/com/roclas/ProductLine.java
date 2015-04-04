package com.roclas;

/**
 * Created by carlos on 4/04/15.
 */
public class ProductLine {

    private int ammount=1;
    private float price=(float)0.0;
    private float taxRate=(float)0.0;

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    private float tax=(float)0.0;
    private String description=null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    @Override
    public String toString(){
        return String.format("%s price=%s (%s+%s [%s])",
                description,
                TaxCalculator.round2ClosestCent(price+tax),
                price,tax,taxRate);
    }

 }
