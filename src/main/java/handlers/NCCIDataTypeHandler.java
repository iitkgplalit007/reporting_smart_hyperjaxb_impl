package handlers;

import org.apache.commons.lang.StringUtils;
import pojo.ElementDetailsPojo;

public class NCCIDataTypeHandler {
    static String SPACE = " ";
    static String ZERO = "0";
    public String convertToNCCIDataFormat(ElementDetailsPojo elementdetails, String value){
        String dataType = elementdetails.get_class();
        switch (dataType){
            case "AN":
                value = handleAlphanumericAndAlphaTypes(elementdetails, value);
                break;
            case "N":
                value = handleNumeric(elementdetails, value);
                break;
            case "A":
                value = handleAlphanumericAndAlphaTypes(elementdetails, value);
                break;
            default:
                value = handleAlphanumericAndAlphaTypes(elementdetails, value);
        }
      return value;
    }
    /*
    ALPHANUMERIC (AN): Field contains alphabetic and numeric characters. Data field is to be left justified and right blank-filled.
    ALPHA (A): Field contains only alphabetic characters. Data field is to be left justified and right blank-filled.
    */
    public String handleAlphanumericAndAlphaTypes(ElementDetailsPojo elementDetails, String value){
        if(value == null){
            value= SPACE;
        }
        int length = elementDetails.get_lenthOfElement();
        int whitespaceesToAdd = 0;
        if(value.length()<length){
            whitespaceesToAdd = length - value.length();
        }else{
            value =  value.substring(0, length);
        }
        if(whitespaceesToAdd !=0 ){
            value =  StringUtils.rightPad(value, whitespaceesToAdd + value.length(), SPACE);
        }
        return value;
    }

    /*
    NUMERIC (N): Field contains only numeric characters. Data field is to be right justified and left zero-filled.
     */
    public String handleNumeric(ElementDetailsPojo elementDetails,String value){
        if(value == null){
            value= SPACE;
        }
        int length = elementDetails.get_lenthOfElement();
        int whitespaceesToAdd = 0;
        if(value.length()<length){
            whitespaceesToAdd = length - value.length();
        }
        if(whitespaceesToAdd !=0 ){
            value =  StringUtils.leftPad(value, whitespaceesToAdd + value.length(), ZERO);
        }
        String newValue = value.replaceAll("\\s+","");
        return newValue;
    }


}
