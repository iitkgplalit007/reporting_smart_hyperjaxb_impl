package handlers;

import com.nimbusds.oauth2.sdk.util.DateUtils;
import dataprocessor.AbstractSubjectHandler;
import dataprocessor.DatabaseAccessor;
import dataprocessor.ReportRunner;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import pojo.ElementDetailsPojo;
import util.SQLColumnType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class WCSTATHandler extends AbstractSubjectHandler {
    private String _subjectHeader;
    private String _schemaTable;
    private ArrayList<ElementDetailsPojo> _elementDetails;
    private String _query;
    private StringBuffer _sb;
    private String _targetTable;
    final static Logger logger = Logger.getLogger(WCSTATHandler.class);
    public WCSTATHandler() {

    }

    public String get_targetTable() {
        return _targetTable;
    }

    public void set_targetTable(String _targetTable) {
        this._targetTable = _targetTable;
    }

    public StringBuffer get_sb() {
        return _sb;
    }

    public void set_sb(StringBuffer _sb) {
        this._sb = _sb;
    }

    public String get_query() {
        return _query;
    }

    public void set_query(String _query) {
        this._query = _query;
    }

    public String get_schemaTable() {
        return _schemaTable;
    }

    public void set_schemaTable(String _schemaTable) {
        this._schemaTable = _schemaTable;
    }

    @Override
    public void set_elementDetails(ArrayList<ElementDetailsPojo> _elementDetails) {
     this._elementDetails = _elementDetails;
        this._elementDetails.sort(Comparator.comparing(elementDetailsPojo -> elementDetailsPojo.get_startPosition()));
    }
    @Override
    public void set_subjectHeader(String _subjectHeader) {
      this._subjectHeader = _subjectHeader;
    }

    @Override
    public String get_subjectHeader() {
        return _subjectHeader;
    }
    @Override
    public ArrayList<ElementDetailsPojo> get_elementDetails() {
        return _elementDetails;
    }

    @Override
    public String prepareSubjectHeaderDataQuery() {
        StringBuffer columns = new StringBuffer();
        for(int i=0; i< _elementDetails.size(); i++ ){
            if(i== _elementDetails.size()-1){
                columns.append(_elementDetails.get(i).get_elementName());
            }else{
                columns.append(_elementDetails.get(i).get_elementName() +  ", ");
            }
        }
        String query = "SELECT " + columns.toString() + " FROM " + get_schemaTable() + "." + get_targetTable();
        return query;
    }


    @Override
    public void processResultSet() {
        Connection conn = new DatabaseAccessor().getConnection();
        try{
            PreparedStatement statement = conn.prepareStatement(_query);
            ResultSet rs =  statement.executeQuery();
            int counter = 1;
            ResultSetMetaData columnMetadata  =  rs.getMetaData();
            while(rs.next()){
                StringBuffer record = new StringBuffer();
                for (ElementDetailsPojo element: _elementDetails) {
                   _sb.append(getDataValue(element, rs, columnMetadata));
                }
                _sb.append(System.getProperty("line.separator"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public String getDataValue(ElementDetailsPojo element, ResultSet rs, ResultSetMetaData columnMetadata){
        String elementValue = null;
        try{
            String columnTypeName = null;
            Object value = null;
            String columnName;
            for(int i=1; i<=columnMetadata.getColumnCount();i++){
                if(columnMetadata.getColumnName(i).equals(element.get_elementName().trim())){
                    columnTypeName =  columnMetadata.getColumnTypeName(i);
                    break;
                }
            }
            switch(columnTypeName){
                case SQLColumnType.VARCHAR:
                    value = rs.getString(element.get_elementName());
                    value = value==null? NCCIDataTypeHandler.SPACE: value.toString() ;
                    break;
                case SQLColumnType.DATETIME:
                    value =  rs.getDate(element.get_elementName());
                    if(value != null){
                        value = new DateHandler(((Date) rs.getDate(element.get_elementName())).getTime()).toString();
                    }else{
                        value = NCCIDataTypeHandler.SPACE;
                    }

                    break;
                case SQLColumnType.NUMERIC:
                    value = rs.getBigDecimal(element.get_elementName());
                    if(value != null){
                        value = new AmountHandler(rs.getBigDecimal(element.get_elementName()).doubleValue()).toString() ;
                    }else{
                        value = NCCIDataTypeHandler.ZERO;
                    }

                    break;
                case SQLColumnType.DECIMAL:
                    value = rs.getBigDecimal(element.get_elementName());
                    if(value != null){
                        value = new AmountHandler(rs.getBigDecimal(element.get_elementName()).doubleValue()).toString() ;
                    }else{
                        value = NCCIDataTypeHandler.ZERO;
                    }

                    break;
                case SQLColumnType.INT:
                    value =  rs.getInt(element.get_elementName());
                    if(value != null){
                        value = Integer.toString((int)value);
                    }else{
                        value = NCCIDataTypeHandler.SPACE;
                    }
                    break;
                default:
                    System.out.println(" Column type is  : " +  columnTypeName);
                        value.toString();
                break;
            }
            System.out.println(" Column name : "+ element.get_elementName() + " element value : " + value + " length start : "+ element.get_lenthOfElement() + " start pos : " + element.get_startPosition() + " actual length : " + value.toString().length() + "Subject Header " +  element.get_subjectHeader());
            return new NCCIDataTypeHandler().convertToNCCIDataFormat(element, value.toString());
        }catch(Exception e){
            System.out.println("Column name : "+ element.get_elementName() + " Subject Header " +  element.get_subjectHeader());
            //System.out.println("Column name : "+ element.get_elementName() + " element value : " + elementValue + " length start : "+ element.get_lenthOfElement() + " start pos : " + element.get_startPosition() + " actual length : "+ elementValue.length());
            e.printStackTrace();
        }
        return  null;

    }

}
