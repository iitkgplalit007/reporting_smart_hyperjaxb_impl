package dataprocessor;

import org.apache.commons.lang3.tuple.Pair;
import pojo.ElementDetailsPojo;
import pojo.ElementKeyPojo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElementsDetails {
    public static ArrayList<ElementDetailsPojo> _elementDetails;
    public static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> _elementsMap;


    public static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> set_elementdetails(ResultSet rs){
        System.out.println(rs);
        _elementsMap = new HashMap<ElementKeyPojo,  ArrayList<ElementDetailsPojo>>();

        try{
            while (rs.next()) {
                ElementDetailsPojo element =  ElementDetailsPojo.getInstance();
                element.set_subjectHeader(rs.getString(1));
                element.set_elementName(rs.getString(2));
                element.set_targetSchema(rs.getString(3));
                element.set_targetTable(rs.getString(4));
                element.set_elementType(rs.getString(5));
                element.set_startPosition(rs.getInt(6));
                element.set_endPosition(rs.getInt(7));
                element.set_lenthOfElement(rs.getInt(8));
                element.set_class(rs.getString(9));
                element.set_recordNumber(rs.getString(10));
                ElementKeyPojo key = new ElementKeyPojo();
                key.set_recordNumber(element.get_recordNumber());
                key.set_Schema(element.get_targetSchema());
                key.set_table(element.get_targetTable());
                key.set_subjectHeader(element.get_subjectHeader());
                key.set_recordNumberOffSet(rs.getInt(11));
                key.set_recordNumberLength(rs.getInt(12));

                if(_elementsMap.containsKey(key)){
                    ArrayList<ElementDetailsPojo> ele = _elementsMap.get(key);
                    ele.add(element);

                    _elementsMap.put(key , ele);
                }else {
                    ArrayList<ElementDetailsPojo> newElementDetailsPojo = new ArrayList<ElementDetailsPojo>();
                    newElementDetailsPojo.add(element);
                    _elementsMap.put(key, newElementDetailsPojo);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return _elementsMap;
    }












   /* public static HashMap<String, ArrayList<ElementDetailsPojo>> set_elementdetails(ResultSet rs, boolean byTargetTable, String subjectHeader){
        System.out.println(rs);
        _elementsMap = new HashMap<String,  ArrayList<ElementDetailsPojo>>();

        try{
            while (rs.next()) {
                ElementDetailsPojo element =  ElementDetailsPojo.getInstance();
                element.set_elementName(rs.getString(1));
                element.set_targetSchema(rs.getString(2));
                element.set_targetTable(rs.getString(3));
                element.set_elementType(rs.getString(4));
                element.set_startPosition(rs.getInt(5));
                element.set_endPosition(rs.getInt(6));
                element.set_lenthOfElement(rs.getInt(7));
                String key = null;
                key = byTargetTable?element.get_targetSchema() + "." +  element.get_targetTable() : subjectHeader;

                if(_elementsMap.containsKey(key)){
                    ArrayList<ElementDetailsPojo> ele = _elementsMap.get(byTargetTable?element.get_targetSchema() + "." +  element.get_targetTable() : subjectHeader);
                    ele.add(element);
                    _elementsMap.put(key, ele);
                }else {
                    ArrayList<ElementDetailsPojo> newSubjectHeader = new ArrayList<ElementDetailsPojo>();
                    newSubjectHeader.add(element);
                    _elementsMap.put(key, newSubjectHeader);
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return _elementsMap;
        }*/

    public static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> get_elementdetails(){
        return _elementsMap;
    }

    public static  ElementsDetails getInstance(){
        return new ElementsDetails();
    }


    public static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> getmetadataInformation(String SubjectHeader, boolean byTargetTable){
        HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> elementmap = new HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>>();
        Connection conn = new DatabaseAccessor().getConnection();
        try{
            String queryString = Queries.refmetadata;
            //System.out.println(queryString);
            PreparedStatement statement = conn.prepareStatement(queryString);
            statement.setString(1,SubjectHeader);
            ResultSet rs = statement.executeQuery();
            ElementsDetails elements =  ElementsDetails.getInstance();
            elementmap =   elements.set_elementdetails(rs);
            elementmap = elements.get_elementdetails();
            for (ElementKeyPojo key: elementmap.keySet()) {
                ArrayList<ElementDetailsPojo> values = elementmap.get(key);
            }
            //ElementsDetailsDAO elementDetails = new ElementsDetailsDAO(elementmap);
            //elementDetails.prepareSubjectHeaderDataQuery();
        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return elementmap;
    }

}
