package dataprocessor;

import handlers.WCSTATHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import pojo.ElementDetailsPojo;
import pojo.ElementKeyPojo;
import pojo.PreparedStatementParameterPojo;
import util.FileUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

public class ReportRunner {
    final static Logger logger = Logger.getLogger(ReportRunner.class);
    static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> _elementmap;
    public static void runReport(Date date, String ReportType, String statecode) {
        List<String> subjectHeaders = getSubjectHeaderForReportByState(ReportType, statecode);
        String metadataRetrievalQuery  = getRetrieveMetadataQuery(subjectHeaders);
        java.util.ArrayList<PreparedStatementParameterPojo> inputParameters  =  createInputsForrefmetadataRetrieval(metadataRetrievalQuery, subjectHeaders);
        Connection conn = new DatabaseAccessor().getConnection();
        PreparedStatement statement = DatabaseAccessor.createPreparedStatement(conn, metadataRetrievalQuery, inputParameters);
        try {
            ResultSet rs = statement.executeQuery();
            ElementsDetails elements = ElementsDetails.getInstance();
            elements.set_elementdetails(rs);
            _elementmap = elements.get_elementdetails();
            StringBuffer newSB = new StringBuffer();
            List<ElementKeyPojo> sortedKeys =  _elementmap.keySet().stream().sorted(Comparator.comparing(ElementKeyPojo::get_recordNumber)).collect(Collectors.toList());
            for (ElementKeyPojo key : sortedKeys) {
                ArrayList<ElementDetailsPojo> values = _elementmap.get(key);
                logger.info("Working on report : " + ReportType + " with subject header : " +  key.get_subjectHeader() +  " Schema name : " + key.get_Schema() + " Target table name : " + key.get_table());
                WCSTATHandler   newWCSTATHandler = new WCSTATHandler();
                                newWCSTATHandler.set_sb(newSB);
                                newWCSTATHandler.set_subjectHeader(key.get_subjectHeader());
                                newWCSTATHandler.set_schemaTable(key.get_Schema());
                                newWCSTATHandler.set_targetTable(key.get_table());
                                newWCSTATHandler.set_elementDetails(values);
                                newWCSTATHandler.set_query(newWCSTATHandler.prepareSubjectHeaderDataQuery());
                                newWCSTATHandler.processResultSet();
            }
            new FileUtil().writeToTheFile(newSB, ReportType);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> getElementMap(String ReportType, String statecode){
        List<String> subjectHeaders = getSubjectHeaderForReportByState(ReportType, statecode);
        String metadataRetrievalQuery  = getRetrieveMetadataQuery(subjectHeaders);
        java.util.ArrayList<PreparedStatementParameterPojo> inputParameters  =  createInputsForrefmetadataRetrieval(metadataRetrievalQuery, subjectHeaders);
        Connection conn = new DatabaseAccessor().getConnection();
        PreparedStatement statement = DatabaseAccessor.createPreparedStatement(conn, metadataRetrievalQuery, inputParameters);
        try {
            ResultSet rs = statement.executeQuery();
            ElementsDetails elements = ElementsDetails.getInstance();
            elements.set_elementdetails(rs);
            _elementmap = elements.get_elementdetails();
        }catch(Exception e){
                e.printStackTrace();
        }
        return _elementmap;
    }
    public static void main(String[] args) {
        try{
          /*  String runDate = args[0];
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'");
            java.util.Date rundate  =  format.parse(runDate);
            java.util.Calendar cal = Calendar.getInstance();
            cal.setTime(rundate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
            runReport(sqlDate);*/
            runReport(null, "WCSTAT" , "CA");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static List<String> getSubjectHeaderForReportByState(String reportType, String stateCode){
        List<String> subjectHeaderList = null;
        String query = Queries.subjectHeaderByState;
        java.util.ArrayList<PreparedStatementParameterPojo> paramSet = new java.util.ArrayList<PreparedStatementParameterPojo>();
        PreparedStatementParameterPojo state = new PreparedStatementParameterPojo();
        state.setIndex(1);
        state.setColumnValue(stateCode);

        PreparedStatementParameterPojo report = new PreparedStatementParameterPojo();
        report.setIndex(2);
        report.setColumnValue(reportType);

        paramSet.add(state);
        paramSet.add(report);


        java.util.HashMap<Pair<String, String>, java.util.ArrayList<Object>> data = DatabaseAccessor.runPreparedStatement(query, paramSet);
        for (Map.Entry<Pair<String, String>, java.util.ArrayList<Object>> entry : data.entrySet()) {
            java.util.ArrayList<Object> subjectHeaders =   entry.getValue();
            subjectHeaderList = subjectHeaders.stream().map( Object::toString ).collect( Collectors.toList() );
        }
      return subjectHeaderList;
    }

    static String getRetrieveMetadataQuery(List<String> subjectHeaders){
        String queryString = Queries.refmetadata;
        StringBuffer parameters = new StringBuffer();
        for(int i=0; i < subjectHeaders.size(); i++){
            if(i==subjectHeaders.size() -1){
                parameters.append("?");
            }else{
                parameters.append("?, ");
            }
        }
        parameters.append(");");
        System.out.println(queryString + parameters.toString());
        return queryString + parameters.toString();
    }
    static java.util.ArrayList<PreparedStatementParameterPojo> createInputsForrefmetadataRetrieval(String query, List<String> subjectHeaders){
        java.util.ArrayList<PreparedStatementParameterPojo> inputs = new java.util.ArrayList<PreparedStatementParameterPojo>();
        for (int i=0; i < subjectHeaders.size(); i++) {
            PreparedStatementParameterPojo newPreparedStatementParameterPojo = new PreparedStatementParameterPojo();
            newPreparedStatementParameterPojo.setIndex(i+1);
            newPreparedStatementParameterPojo.setColumnValue(subjectHeaders.get(i));
            inputs.add(newPreparedStatementParameterPojo);
        }
        return inputs;
    }

}
