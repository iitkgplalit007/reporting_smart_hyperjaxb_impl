package handlers;

public class AmountHandler extends java.math.BigDecimal {
    public AmountHandler(double val) {
        super(val);
    }

    @Override
    public String toString(){
        String originalString  =  super.toString();
        originalString =  originalString.replaceAll(".","");
        return originalString;
    }

}
