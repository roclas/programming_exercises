package models;

/**
 * Created by carlos on 8/03/15
 */
public class Currency {
    //@Component(ordinal = 0) 
    private String key;
    //@Component(ordinal = 1) 
    private String column1;
    //@Component(ordinal = 2)
    private double value;
    
    /**
     * Render method that will make the views development easier
     * @return
     */
    public String render() { return String.format("%s \t%s", column1, value);}
    public String toString() { return String.format("\t%s %s %s", key, column1, value);}

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getColumn1() { return column1; }
    public void setColumn1(String column1) { this.column1 = column1; }
    public double getValue() { return value; }
    public void setValue(double value) {  this.value = value; }
}


