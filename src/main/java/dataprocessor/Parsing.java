package dataprocessor;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.util.Callback;
import org.bouncycastle.util.Arrays;
import pojo.ElementDetailsPojo;
import pojo.ElementKeyPojo;
import pojo.PreparedStatementParameterPojo;
import pojo.SubjectHeaderPojo;
import ui.TransmissionUIHelper;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Parsing {
    static Properties properties =  new DatabaseAccessor().get_appProperties();
public static void main(String[] args){
    Properties properties =  new DatabaseAccessor().get_appProperties();
    File file = new File(System.getProperty("user.dir") + properties.getProperty("app.outputFolderLocation") +  "WCSTAT20190116_132019");
    parseFile(file, null);

}
  public static void parseFile(File file, TabPane reportParsingLowerPaneID){

      HashMap<ElementKeyPojo, String> record = new HashMap<ElementKeyPojo, String>();
      try{

          String ReportType = "WCSTAT";
          String statecode = "CA";
          List<String> subjectHeaders = ReportRunner.getSubjectHeaderForReportByState(ReportType, statecode);
          String metadataRetrievalQuery  = ReportRunner.getRetrieveMetadataQuery(subjectHeaders);
          java.util.ArrayList<PreparedStatementParameterPojo> inputParameters  =  ReportRunner.createInputsForrefmetadataRetrieval(metadataRetrievalQuery, subjectHeaders);
          HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> elementsMap =  ReportRunner.getElementMap(ReportType,statecode );
          List<ElementKeyPojo> sortedKeys =  elementsMap.keySet().stream().sorted(Comparator.comparing(ElementKeyPojo::get_recordNumber)).collect(Collectors.toList());
          String firstLine = Files.asCharSource(file, Charsets.UTF_8).readFirstLine();
          ElementKeyPojo transmission = sortedKeys.stream().filter(elementKeyPojo -> elementKeyPojo.get_subjectHeader().equals(properties.getProperty("app.TransmissionSubjectHeader"))).findFirst().get();
          elementsMap.get(transmission);
          HashMap<String,String[][]> transmissionMatrix;
          if(TransmissionUIHelper.isTransmissionRecord(transmission, firstLine)){
              transmissionMatrix = getfileMatrixbySubjectHeader(splitTransmisison(firstLine, transmission), ReportType, statecode);
          }
          long lineCount = java.nio.file.Files.lines(Paths.get(file.getAbsolutePath())).count();
          HashMap<String,String[][]> subjectHeaderDataMatrix;
          for (ElementKeyPojo key : sortedKeys) {
              subjectHeaderDataMatrix =  getfileMatrixbySubjectHeader(splitFileBySubjectHeader(file, sortedKeys),  ReportType, statecode);
          }
      }catch (IOException e){
          e.printStackTrace();
      }catch(Exception e){
          e.printStackTrace();
      }




    /*  Scanner sc = null;
      SubjectHeaderPojo header =  getSubjectHeaderDetails("TRANSMISSION");
      try {
          sc = new Scanner(file);
          int counter  = 0;
          while (sc.hasNextLine()){

              if(counter ==0){
                  String firstLine = sc.nextLine();
                  //Create Headers
                  //TableView tab = createTransmissionHeader(firstLine, reportParsingLowerPaneID, header);
                  createRecords(firstLine, header, reportParsingLowerPaneID);
              }
              //getmetadataInformation();
               ++counter;
          }

      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }*/
  }
  public static HashMap<ElementKeyPojo, ArrayList<String>> splitTransmisison(String line, ElementKeyPojo transmission){
      HashMap<ElementKeyPojo, ArrayList<String>> splitFile = new HashMap<ElementKeyPojo, ArrayList<String>>();
      List<ElementKeyPojo> trans = new ArrayList<ElementKeyPojo>();
      trans.add(transmission);

      if (line!=null) {
              String transmissionSubjectHeader = properties.getProperty("app.TransmissionSubjectHeader");
              if(splitFile.containsKey(transmission)){
                  ArrayList<String> records =  splitFile.get(transmission);
                  records.add(line);
                  splitFile.put(transmission, records);
              }else{

                  ArrayList<String> newrecords = new ArrayList<String>();
                  newrecords.add(line);
                  splitFile.put(transmission, newrecords);
              }
          }
      return splitFile;
  }

  public static  HashMap<ElementKeyPojo, ArrayList<String>> splitFileBySubjectHeader(File file , List<ElementKeyPojo> sortedKeys){
      HashMap<ElementKeyPojo, ArrayList<String>> splitFile = new HashMap<ElementKeyPojo, ArrayList<String>>();
      List<ElementKeyPojo> keysWithoutTransmission  =  getSubjectHeadersWithRecordNumberCode(sortedKeys);
      try{
          BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
          String line = reader.readLine();
          int counter =1;
          while (line != null) {
              if(counter > 1){
                  line = reader.readLine();
                  int recordType = getRecordTypeCode(line, keysWithoutTransmission);
                  //ToDo: Change the whole record type code thing
                  ElementKeyPojo subjectHeader =  getSubjectHeaderForRecord( keysWithoutTransmission);
                  if(splitFile.containsKey(subjectHeader)){
                      ArrayList<String> records =  splitFile.get(subjectHeader);
                      records.add(line);
                      splitFile.put(subjectHeader, records);
                  }else{
                      ArrayList<String> newrecords = new ArrayList<String>();
                      newrecords.add(line);
                      splitFile.put(subjectHeader, newrecords);
                  }
              }
              ++counter;
          }
      }catch (IOException e){
          e.printStackTrace();
      }
      return splitFile;
  }


  private static ElementKeyPojo getSubjectHeaderForRecord(List<ElementKeyPojo> sortedKeys){
    try{
        ElementKeyPojo subjectHeader = sortedKeys.stream().filter(elementKeyPojo -> elementKeyPojo.get_recordNumber()==properties.getProperty("app.TransmissionSubjectHeader")).findFirst().get();
        return subjectHeader;
    }catch (Exception e){
        e.printStackTrace();
        throw e;
    }
  }
  private static  int getRecordTypeCode(String record, List<ElementKeyPojo> sortedKeys){
    try{
        Properties appProperties  =  new DatabaseAccessor().get_appProperties();
        int recordNumberOffSet =Integer.parseInt( appProperties.getProperty("app.recordcodeoffset"));
        int recordNumberLength = Integer.parseInt( appProperties.getProperty("app.recordcodelength"));
        String recordType = new String(record.getBytes(), recordNumberOffSet, recordNumberLength);


        int recordtypeCode = 0;
        try{
            recordtypeCode = Integer.parseInt(recordType) ;
        }catch(NumberFormatException e){
            System.out.println(e.getMessage() + " "  +  recordNumberOffSet + " " +  recordNumberLength + " values is  "  +  recordType);
        }
        return recordtypeCode;
    }catch(Exception e){
        e.printStackTrace();
        throw e;
    }

  }
  private static List<ElementKeyPojo> getSubjectHeadersWithRecordNumberCode(List<ElementKeyPojo> sortedKeys){
      ElementKeyPojo subjectHeader = sortedKeys.stream().filter(elementKeyPojo -> elementKeyPojo.get_subjectHeader()==properties.get("app.TransmissionSubjectHeader")).findFirst().get();
      sortedKeys.remove(subjectHeader);
      return sortedKeys;
  }


public static HashMap<String,String[][]> getfileMatrixbySubjectHeader(HashMap<ElementKeyPojo, ArrayList<String>> map, String reportType, String state){
    HashMap<String,String[][]> tableMap =  new HashMap<String,String[][]>();
    for (Map.Entry<ElementKeyPojo, ArrayList<String>> entry :map.entrySet()) {
        HashMap<ElementKeyPojo, ArrayList<ElementDetailsPojo>> elementsMaps =  ReportRunner.getElementMap(reportType, state);
        ArrayList<ElementDetailsPojo> columnDetails =  elementsMaps.get(entry.getKey());
        String[][] newTwoDimentionalArray = new String[entry.getValue().size()+ 1][columnDetails.size()];
        //Set All Columns
        int counter=0;
        for (ElementDetailsPojo column  : columnDetails) {
            newTwoDimentionalArray[0] [counter]= column.get_elementName();
            ++counter;
        }
        // Populate rows
        ArrayList<String> values = entry.getValue();
        for(int i=0;i<values.size();i++){
            String line =  values.get(i);
            for(int k=0;k<columnDetails.size();k++){
                newTwoDimentionalArray[i+1][k] = new String(line.getBytes(), columnDetails.get(k).get_startPosition()-1, columnDetails.get(k).get_lenthOfElement());
            }

        }
        tableMap.put(entry.getKey().get_subjectHeader(),newTwoDimentionalArray );
    }
    return tableMap;
}





  public static SubjectHeaderPojo getSubjectHeaderDetails(String subjectHeader){
      Connection conn = new DatabaseAccessor().getConnection();
      SubjectHeaderPojo headerDetails= null;
      try{
          String queryString = Queries.getSubjectHeaderByRecodeTypeCode;
          //System.out.println(queryString);
          PreparedStatement statement = conn.prepareStatement(queryString);
          statement.setString(1,subjectHeader);
          ResultSet rs = statement.executeQuery();
          while(rs.next()){
              headerDetails =  SubjectHeaderPojo.getNewInstance();
              headerDetails.set_subjectHeader(rs.getString("TYPECODE"));
              headerDetails.set_recordNumberCode(rs.getString("RECORD_TYPE"));
              headerDetails.set_recordNumberLength(rs.getInt("RECORD_NUMBER_LENGTH"));
              headerDetails.set_recordNumberStartPos(rs.getInt("RECORD_NUMBER_START_POSITION"));
          }

      }catch(SQLException e){
          e.printStackTrace();
      }
      return headerDetails;
  }
  /*static TableView createTransmissionHeader(String transmissionRecord , TabPane reportParsingLowerPaneID, SubjectHeaderPojo header){
      java.util.ArrayList<String> values = new java.util.ArrayList<String>();
      String columnHeaderStyle = ".table-view .column-header.foo .label {" +
              " -fx-text-fill: white; " +
              "-fx-font-weight: bold;" +
              "}";
      HashMap<String, ArrayList<ElementDetailsPojo>> metadata =  ElementsDetails.getmetadataInformation(header.get_subjectHeader(), false);
      Tab transmissionTab = new Tab();
      transmissionTab.setText(header.get_subjectHeader());
      transmissionTab.setId(header.get_subjectHeader()+"ID");
      TableView transmissionTable = new TableView();
      transmissionTable.setId(header.get_subjectHeader()+"TV"+"ID");
      //Create Columns
      for (ElementDetailsPojo data: metadata.get("wcstat.TRANSMISSION"header.get_subjectHeader())) {
          TableColumn<String, String> col = new TableColumn<String, String>();
          col.setText(data.get_elementName());

          //Paint.valueOf("#2C8BC6")
          col.getStyleClass().add(columnHeaderStyle);
          col.setId(data.get_elementName() + "ID");
          transmissionTable.getColumns().add(col);
      }

      transmissionTab.setContent(transmissionTable);
      reportParsingLowerPaneID.getTabs().add(transmissionTab);
      return transmissionTable;
  }
    static void createRecords(String transmissionRecord ,  SubjectHeaderPojo header, TabPane reportParsingLowerPaneID){
        java.util.ArrayList<String> values = new java.util.ArrayList<String>();
        ObservableList<String> observableList = FXCollections.observableList(values);
        String subjectHeader =  header.get_subjectHeader();
        HashMap<String, ArrayList<ElementDetailsPojo>> metadata =  getmetadataInformation(header.get_subjectHeader());
        for (ElementDetailsPojo data: metadata.get("wcstat.TRANSMISSION"header.get_subjectHeader())) {

            values.add(transmissionRecord.substring(data.get_startPosition() , data.get_startPosition() + data.get_lenthOfElement()-1));
        }
        tab.setItems(values);

        HashMap<String, ArrayList<ElementDetailsPojo>> metadata = ElementsDetails. getmetadataInformation(header.get_subjectHeader(), true);
        HashMap<String,String> dataElements = new HashMap<String,String>();


        Tab transmissionTab = new Tab();
        transmissionTab.setText(header.get_subjectHeader());
        transmissionTab.setId(header.get_subjectHeader()+"ID");


        //Creates Map
        for (ElementDetailsPojo data: metadata.get("wcstat.TRANSMISSION"header.get_subjectHeader())) {
            String key = data.get_elementName();
            //substring(start, Math.min(start + length, myString.length()));
            //substring(start, Math.min(data.get_startPosition() + data.get_lenthOfElement(), myString.length()));
            String value  =  new String(transmissionRecord.getBytes(),data.get_startPosition()-1,data.get_lenthOfElement());
            //String value  =  transmissionRecord.substring(data.get_startPosition() , data.get_startPosition() + data.get_lenthOfElement());
            System.out.println(key + " : " +  value +  " Start pos : " +  (data.get_startPosition()-1 ) +  " Length : " +   data.get_lenthOfElement());
            dataElements.put(key, value);

        }
        java.util.ArrayList<TableColumn<Map, String>> cols = new java.util.ArrayList<TableColumn<Map,  String>>();
        for (Map.Entry<String, String> entry: dataElements.entrySet()) {
            TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>(entry.getKey());
            column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    // this callback returns property for just one cell, you can't use a loop here
                    // for first column we use key
                    return new SimpleStringProperty(p.getValue().getKey());
                }
            });
            cols.add(column1);
            //transmissionTable.getColumns().add(column1);
        }
        for (Map.Entry<String, String> entry: dataElements.entrySet()) {
            TableColumn<Map, String> column1 = new TableColumn<>(entry.getKey());
            column1.setCellValueFactory(new MapValueFactory(entry.getKey()));
            cols.add(column1);
            //transmissionTable.getColumns().add(column1);
        }
        ObservableList<Map> items = FXCollections.observableArrayList(dataElements);
        TableView transmissionTable = new TableView<>(items);
        boolean set =  transmissionTable.getColumns().setAll(cols);
        transmissionTable.setId(header.get_subjectHeader()+"TV"+"ID");
        transmissionTab.setContent(transmissionTable);
        //transmissionTable.getColumns().addAll(cols);
        reportParsingLowerPaneID.getTabs().add(transmissionTab);


    }*/
}
