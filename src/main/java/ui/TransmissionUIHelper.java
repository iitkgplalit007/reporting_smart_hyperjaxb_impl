package ui;

import dataprocessor.DatabaseAccessor;
import dataprocessor.ElementsDetails;
import dataprocessor.Queries;
import pojo.ElementDetailsPojo;
import pojo.ElementKeyPojo;
import pojo.TransmissionPoJO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TransmissionUIHelper {
    static  java.util.ArrayList<String> columns = null ;


    public static boolean isTransmissionRecord(ElementKeyPojo transmission, String firstLine){
        try{
            String recordTypeCode = new String(firstLine.getBytes(), transmission.get_recordNumberOffSet()-1, transmission.get_recordNumberLength());

            if(recordTypeCode.equals(transmission.get_recordNumber())){
                return true;
            }
        }catch (NumberFormatException e){
            System.out.println(firstLine.substring(transmission.get_recordNumberOffSet(), transmission.get_recordNumberLength())  +  " cannot be converted into number");
            e.printStackTrace();
        }
        return false;
    }

    public static java.util.HashMap<String, String> getTransmissionRecord(){
        java.util.HashMap<String, String> columnValueMap = new java.util.HashMap<String, String>();
        Connection conn = new DatabaseAccessor().getConnection();
        try{
            String transmissionquery = createTransmissionRetrievalQuery("Transmission");
            PreparedStatement oldtransmission = conn.prepareStatement(transmissionquery);
            ResultSet rs =  oldtransmission.executeQuery();
            columnValueMap = getTransmissionData(rs);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return columnValueMap;
    }
    static String createTransmissionRetrievalQuery(String SubjectHeader){
        ArrayList<ElementDetailsPojo> details  = getColums(SubjectHeader);
        StringBuffer columns = new StringBuffer();
        for(int i=0; i<details.size(); i++){
            columns.append(details.get(i).get_elementName());
            if(i != details.size()-1){
                columns.append(", ");
            }
        }
        String query = Queries.transmission.replace("${columns}", columns.toString());
        return query;
    }

    static  ArrayList<ElementDetailsPojo> getColums (String SubjectHeader){
        HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> elementDetails =   ElementsDetails.getmetadataInformation(SubjectHeader, false);
        return elementDetails.get(SubjectHeader);
    }
    static java.util.HashMap<String, String> getTransmissionData(ResultSet rs){
        java.util.HashMap<String, String> columnValueMap = new java.util.HashMap<String, String>();
        try{
            String[] columns = getColumnsName(rs.getMetaData());
           while (rs.next()){
              for(int i=0; i < columns.length;i++) {
                  String column =  columns[i];
                  String value  =  rs.getString(columns[i]);
                  columnValueMap.put(column, value);
              }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return columnValueMap;
    }

    public static String[] getColumnsName(ResultSetMetaData rsm){
        java.util.ArrayList<String> columns = new java.util.ArrayList<String>();
        try{
            int colCount = rsm.getColumnCount();
            for (int i=1; i<=colCount;i++) {
                String  columnName = rsm.getColumnName(i) ;
                columns.add(columnName);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String[] newColumnsString  =  new String[columns.size()];
        return columns.toArray(newColumnsString);
    }

    public static void persistTransmission(java.util.HashMap<String, String> dataMap){
        try{
            java.util.Set<String> columns = dataMap.keySet();
            Connection conn = new DatabaseAccessor().getConnection();
            PreparedStatement insertTransmission  = conn.prepareStatement(Queries.newTransmissionEntry(dataMap.keySet()));
            int counter = 1;
            for (String col :columns) {
                if(!col.equals("ID") ){
                    insertTransmission.setString(counter, dataMap.get(col));
                    ++counter;
                }
            }
            insertTransmission.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static java.util.HashMap<String, String> getTramissionData(TransmissionPoJO transmission){
        java.util.HashMap<String, String> columsMap =  getTransmissionRecord();
        for (String column :columsMap.keySet()) {
            switch(column.toLowerCase()){
                case "label":
                    String val = transmission.getLabel();
                    columsMap.put(column, val);
                    break;
                case "data_provider_contact_email__0":
                    String data_provider_contact_email__0 = transmission.getData_provider_contact_email__0();
                    columsMap.put(column, data_provider_contact_email__0);
                    break;
                case "record_type_code":
                    String record_type_code = transmission.getRecord_type_code();
                    columsMap.put(column, record_type_code);
                    break;
                case "data_type_code":
                    String data_type_code = transmission.getData_type_code();
                    columsMap.put(column, data_type_code);
                    break;
                case "data_receiver_code":
                    String data_receiver_code = transmission.getData_receiver_code();
                    columsMap.put(column, data_receiver_code);
                    break;
                case "transmission_version_identif_0":
                    String transmission_version_identif_0 = transmission.getTransmission_version_identif_0();
                    columsMap.put(column, transmission_version_identif_0);
                    break;
                case "submission_type_code":
                    String submission_type_code = transmission.getSubmission_type_code();
                    columsMap.put(column, submission_type_code);
                    break;
                case "submission_replacement_identifier":
                    String submission_replacement_identifier = transmission.getSubmission_replacement_identifier();
                    columsMap.put(column, submission_replacement_identifier);
                    break;
                case "data_provider_code":
                    String data_provider_code = transmission.getData_provider_code();
                    columsMap.put(column, data_provider_code);
                    break;
                case "name_of_data_provider_contact":
                    String name_of_data_provider_contact = transmission.getName_of_data_provider_contact();
                    columsMap.put(column, name_of_data_provider_contact);
                    break;
                case "phone_number":
                    String phone_number = transmission.getPhone_number();
                    columsMap.put(column, phone_number);
                    break;
                case "phone_number_extension":
                    String phone_number_extension = transmission.getPhone_number_extension();
                    columsMap.put(column, phone_number_extension);
                    break;
                case "fax_number":
                    String fax_number = transmission.getFax_number();
                    columsMap.put(column, fax_number);
                    break;
                case "processed_date_item":
                    String processed_date_item = transmission.getProcessed_date_item().toString();
                    columsMap.put(column, processed_date_item);
                    break;
                case "address_of_contact_street":
                    String address_of_contact_street = transmission.getAddress_of_contact_street();
                    columsMap.put(column, address_of_contact_street);
                    break;
                case "address_of_contact_city":
                    String address_of_contact_city = transmission.getAddress_of_contact_city();
                    columsMap.put(column, address_of_contact_city);
                    break;
                case "address_of_contact_state":
                    String address_of_contact_state = transmission.getAddress_of_contact_state();
                    columsMap.put(column, address_of_contact_state);
                    break;
                case "address_of_contact_zip_code":
                    String address_of_contact_zip_code = transmission.getAddress_of_contact_zip_code();
                    columsMap.put(column, address_of_contact_zip_code);
                    break;
                case "data_provider_type_code":
                    String data_provider_type_code = transmission.getData_provider_type_code();
                    columsMap.put(column, data_provider_type_code);
                    break;
                case "third_party_entity_federal_e_0":
                    String third_party_entity_federal_e_0 = transmission.getThird_party_entity_federal_e_0();
                    columsMap.put(column, third_party_entity_federal_e_0);
                    break;
                default:
                    break;
                // do nothing for now
            }
        }
      return columsMap;
    }
}
