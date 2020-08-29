package dataprocessor;

import org.apache.commons.lang.StringUtils;
import pojo.ElementDetailsPojo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class ElementsDetailsDAO {
    static HashMap<String, ArrayList<ElementDetailsPojo>> _elementdetailsmap;

    public ElementsDetailsDAO(HashMap<String, ArrayList<ElementDetailsPojo>> elements) {
        _elementdetailsmap = elements;
    }

    void prepareSubjectHeaderDataQuery() throws SQLException {
       String columnNames = null;
       Set<String> keys = _elementdetailsmap.keySet();
      for (String key: keys) {
          ArrayList<ElementDetailsPojo> values = _elementdetailsmap.get(key);
          for (ElementDetailsPojo e : values) {
                  if (columnNames == null) {
                      columnNames = e.get_elementName();
                  } else {
                      columnNames = columnNames + ", " + e.get_elementName();
                  }
          }
           Connection conn = new DatabaseAccessor().getConnection();
           //ElementsDetails.getmetadataInformation(key,)
          String prefix = null;
          String suffix =  null;
          if(key.contains("TRANSMISSION")){
               prefix = "TOP 1 ";
               suffix =  " ORDER BY UPDATE_TIME DESC";
          }

           try{
               String dataQuery ="SELECT " + prefix + columnNames + " FROM "  + key + suffix;
               //String dataQuery ="select "+ columnNames + " from " + keys.toArray()[0] + " link " + " join " +  keys.toArray()[1] + " loss on loss.linkid = link.id";
               //System.out.println(dataQuery);
               PreparedStatement statement = conn.prepareStatement(dataQuery);
               ResultSet rs = statement.executeQuery();
               processResultSet(rs, columnNames, _elementdetailsmap.values());
           }catch (SQLException e){
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } finally{
               conn.close();
           }

       }

  }
   void processResultSet(ResultSet rs, String columns, Collection<ArrayList<ElementDetailsPojo>> collection) throws IOException {
      ArrayList<ElementDetailsPojo> values = falttenMapValues(collection);
      String[] columnNames =  columns.split(",");
      Connection conn = new DatabaseAccessor().getConnection();
      File file = createFile();
      //FileWriter writer = new FileWriter(file);
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      try{
              while(rs.next()){
              StringBuilder sb = new StringBuilder();
              for (String col:columnNames) {
                  java.sql.Date date = null;
                  String value = null;
                  BigDecimal numeric = null;
                  String datatype =  getElementType(col, values);
                  //System.out.println(col + ":" + datatype);
                  if(datatype.equals("datetime")){
                      date =  rs.getDate(col.trim());
                      if(date != null){
                          value = getDateFormat(date);
                      }
                  }else if(datatype.equals("numeric")){
                      numeric = rs.getBigDecimal(col.trim());
                      if(value != null){
                          value = numeric.toString();
                      }
                  }
                  else{
                       value =  rs.getString(col.trim());
                  }

                  System.out.println(col + " : " +  value);
                  int startPosition = getStartPosition(col, values)-1;
                  int endPosition = getEndPosition(col, values);
                  int length = getLength(col, values);

                  String newValue =  prepareOutputFile(value,startPosition, endPosition, length, sb, datatype);
                  System.out.println(col + " Buffer Length :" + newValue.length() + " : " +  length);
              }
              sb.append(System.getProperty("line.separator"));
              writer.write(sb.toString());
          }
          writer.close();
      }catch (SQLException e){
          e.printStackTrace();
      }

  }
   int getStartPosition(String columnName , ArrayList<ElementDetailsPojo>  values){
        for (ElementDetailsPojo e: values) {
            if(e.get_elementName().equals(columnName.trim())){
                return e.get_startPosition();
            }
        }
        return 0;
    }
     String getElementType(String columnName , ArrayList<ElementDetailsPojo>  values){
        for (ElementDetailsPojo e: values) {
            if(e.get_elementName().trim().equals(columnName.trim())){
                return e.get_elementType();
            }
        }
        return null;
    }
     int getEndPosition(String columnName , ArrayList<ElementDetailsPojo>  values){
        for (ElementDetailsPojo e: values) {
            if(e.get_elementName().equals(columnName.trim())){
                return e.get_endPosition();
            }
        }
        return 0;
    }
     int getLength(String columnName , ArrayList<ElementDetailsPojo>  values) {
        for (ElementDetailsPojo e : values) {
            if (e.get_elementName().equals(columnName.trim())) {
                return e.get_lenthOfElement();
            }
        }
        return 0;
        }
     String prepareOutputFile(String value, int startPosition, int endPosition, int length, StringBuilder sb, String datatype){
        if(value == null){
            value =" ";
        }
        //value = value.replaceAll(/*"\\W+"*/ ".","");

      String padString = " ";
       if(datatype.equals( "numeric")){
           padString ="0";
           value = value.replace(".","");
       }

        int whiteSpacesToAdd = 0;
        //StringBuilder sb = new StringBuilder();

       /* if(endPosition - startPosition + 1 != length){
            length =  endPosition - startPosition + 1;
        }*/
        if(value.length() != length){
            if(value.length() < length){
                whiteSpacesToAdd =  length -  value.length();
            }else{
                value = value.substring(0,length);
            }
        }
        if(whiteSpacesToAdd !=0 ){
            value =  StringUtils.rightPad(value, whiteSpacesToAdd+ value.length(), " ");
        }
        sb.append(value);
        return value;
}
     File createFile(){
        File file = null;
      try{
           file = new File("C://Users//lknarnaw//Documents//RSmart_HyperJaxb_Impl//reporting_smart_hyperjaxb_impl//output//testFile1.txt");
          file.createNewFile();
      }catch(IOException e){
          e.printStackTrace();
      }
        return file;
    }
     String getDateFormat(java.sql.Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH);
      int day = cal.get(Calendar.DAY_OF_MONTH);
      String yearToString = Integer.toString(year);
      String monthToString = Integer.toString(month);
      String dayToString = Integer.toString(day);
      if(yearToString.length() == 4){
          yearToString = yearToString.substring(2,yearToString.length());
      }
      if(monthToString.length() < 2){
          monthToString = "0"+ monthToString;
      }
        if(dayToString.length() < 2){
            dayToString = "0"+ dayToString;
        }
     return yearToString+ monthToString + dayToString;

    }
     ArrayList<ElementDetailsPojo> falttenMapValues(Collection<ArrayList<ElementDetailsPojo>> collection){
        ArrayList<ElementDetailsPojo> newArray = new ArrayList<ElementDetailsPojo>();
        for (ArrayList<ElementDetailsPojo> ar: collection) {
            newArray.addAll(ar);
        }
        return newArray;
    }
}
