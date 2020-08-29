package ui;

import dataprocessor.DatabaseAccessor;
import dataprocessor.Parsing;
import dataprocessor.Queries;
import dataprocessor.ReportRunner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Callback;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import pojo.RefernceDataPoJo;
import pojo.TransmissionPoJO;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class ControllerInitialization {
    public ControllerInitialization() {
        new ControllerInitialization();
    }

    @FXML
    private static java.sql.Date TodaysSQLDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    static void runReportInitialization(Button RunReportID){
        RunReportID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ReportRunner.runReport(TodaysSQLDate, "WCSTAT" , "CA");
            }
        });
    }
     static void parseFile(TabPane reportParsingLowerPaneID, Button parseID, File file){
        parseID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parsing.parseFile(file, reportParsingLowerPaneID);
            }
        });
    }
    static void splitPaneDividerSetter(SplitPane SplitPaneReportsID){
        SplitPaneReportsID.getDividers().get(0).positionProperty().addListener(
                o -> System.out.println(SplitPaneReportsID.getDividerPositions()[0])
        );
        SplitPaneReportsID.setDividerPosition(0, 0.21237458193979933);
    }
    public static void createNewTransmission( Button createNewTransmissionButtonID, GridPane transmissionDataGridID){
        createNewTransmissionButtonID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TransmissionPoJO newTransmissionPoJO = new TransmissionPoJO();
                TransmissionPoJO.clone(newTransmissionPoJO);
                for (Node child :transmissionDataGridID.getChildren()) {
                    if(child instanceof TextField){
                        switch(child.getId()){
                            case "Name_of_data_provider_contact":
                                newTransmissionPoJO.setName_of_data_provider_contact(((TextField) child).getText());
                                break;
                            case "Address_of_contact_street":
                                newTransmissionPoJO.setAddress_of_contact_street(((TextField) child).getText());
                                break;
                            case "Address_of_contact_city":
                                newTransmissionPoJO.setAddress_of_contact_city(((TextField) child).getText());
                                break;
                            case "Address_of_contact_state":
                                newTransmissionPoJO.setAddress_of_contact_state(((TextField) child).getText());
                                break;
                            case "Address_of_contact_zip_code":
                                newTransmissionPoJO.setAddress_of_contact_zip_code(((TextField) child).getText());
                                break;
                            case "Phone_number":
                                newTransmissionPoJO.setPhone_number(((TextField) child).getText());
                                break;
                            case "Phone_number_extension":
                                newTransmissionPoJO.setPhone_number_extension(((TextField) child).getText());
                                break;
                            case "Fax_number":
                                newTransmissionPoJO.setFax_number(((TextField) child).getText());
                                break;
                            case "Data_provider_contact_email__0":
                                newTransmissionPoJO.setData_provider_contact_email__0(((TextField) child).getText());
                                break;
                            default:
                                break;
                        }
                        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                        newTransmissionPoJO.setProcessed_date_item(date);
                        //newTransmissionPoJO.setVersion_number_of_day(0);
                        newTransmissionPoJO.setTransmission_version_identif_0(null);

                    }
                }
                java.util.HashMap<String, String>  map =  TransmissionUIHelper.getTramissionData(newTransmissionPoJO);
                TransmissionUIHelper.persistTransmission(map);
            }
        });
    }
    public static void loadTransmission(GridPane transmissionDataGridID) {
        try {
            TransmissionPoJO newInstace = new TransmissionPoJO();

            newInstace.retrieveTransmissionRecord();

            Label contactName = new Label("Provider Contact Name :");
            contactName.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName.setAlignment(Pos.CENTER_RIGHT);
            contactName.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF = new TextField();
            contactNameTF.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF.setText(newInstace.getName_of_data_provider_contact());
            contactNameTF.setId("Name_of_data_provider_contact");
            transmissionDataGridID.add(contactName, 0, 0);
            transmissionDataGridID.add(contactNameTF, 1, 0);


            Label contactName1 = new Label("Street :");
            contactName1.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName1.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName1.setAlignment(Pos.CENTER_RIGHT);
            contactName1.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF1 = new TextField();
            contactNameTF1.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF1.setText(newInstace.getAddress_of_contact_street());
            contactNameTF1.setId("Address_of_contact_street");
            transmissionDataGridID.add(contactName1, 0, 1);
            transmissionDataGridID.add(contactNameTF1, 1, 1);


            Label contactName2 = new Label("City :");
            contactName2.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName2.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName2.setAlignment(Pos.CENTER_RIGHT);
            contactName2.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF2 = new TextField();
            contactNameTF2.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF2.setText(newInstace.getAddress_of_contact_city());
            contactNameTF2.setId("Address_of_contact_city");
            transmissionDataGridID.add(contactName2, 0, 2);
            transmissionDataGridID.add(contactNameTF2, 1, 2);


            Label contactName3 = new Label("State :");
            contactName3.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName3.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName3.setAlignment(Pos.CENTER_RIGHT);
            contactName3.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF3 = new TextField();
            contactNameTF3.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF3.setText(newInstace.getAddress_of_contact_state());
            contactNameTF3.setId("Address_of_contact_state");
            transmissionDataGridID.add(contactName3, 0, 3);
            transmissionDataGridID.add(contactNameTF3, 1, 3);

            Label contactName4 = new Label("Postal Code :");
            contactName4.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName4.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName4.setAlignment(Pos.CENTER_RIGHT);
            contactName4.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF4 = new TextField();
            contactNameTF4.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF4.setText(newInstace.getAddress_of_contact_zip_code());
            contactNameTF4.setId("Address_of_contact_zip_code");
            transmissionDataGridID.add(contactName4, 2, 4);
            transmissionDataGridID.add(contactNameTF4, 3, 4);


            Label contactName5 = new Label("Phone Number :");
            contactName5.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName5.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName5.setAlignment(Pos.CENTER_RIGHT);
            contactName5.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF5 = new TextField();
            contactNameTF5.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF5.setText(newInstace.getPhone_number());
            contactNameTF5.setId("Phone_number");
            transmissionDataGridID.add(contactName5, 2, 0);
            transmissionDataGridID.add(contactNameTF5, 3, 0);

            Label contactName6 = new Label("Extension :");
            contactName6.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName6.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName6.setAlignment(Pos.CENTER_RIGHT);
            contactName6.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF6 = new TextField();
            contactNameTF6.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF6.setText(newInstace.getPhone_number_extension());
            contactNameTF6.setId("Phone_number_extension");
            transmissionDataGridID.add(contactName6, 2, 1);
            transmissionDataGridID.add(contactNameTF6, 3, 1);


            Label contactName7 = new Label("Extension :");
            contactName7.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName7.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName7.setAlignment(Pos.CENTER_RIGHT);
            contactName7.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF7 = new TextField();
            contactNameTF7.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF7.setText(newInstace.getPhone_number_extension());
            contactNameTF7.setId("Phone_number_extension");
            transmissionDataGridID.add(contactName7, 2, 2);
            transmissionDataGridID.add(contactNameTF7, 3, 2);

            Label contactName8 = new Label("Fax Number :");
            contactName8.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName8.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName8.setAlignment(Pos.CENTER_RIGHT);
            contactName8.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF8 = new TextField();
            contactNameTF8.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF8.setText(newInstace.getFax_number());
            contactNameTF8.setId("Fax_number");
            transmissionDataGridID.add(contactName8, 2, 3);
            transmissionDataGridID.add(contactNameTF8, 3, 3);

            Label contactName9 = new Label("Email :");
            contactName9.setFont(Font.font("Verdana", FontPosture.ITALIC, 11));
            contactName9.setTextFill(Paint.valueOf("#2C8BC6"));
            contactName9.setAlignment(Pos.CENTER_RIGHT);
            contactName9.setPadding(new Insets(10, 20, 10, 20));
            TextField contactNameTF9 = new TextField();
            contactNameTF9.setPadding(new Insets(0, 5, 0, 5));
            contactNameTF9.setText(newInstace.getData_provider_contact_email__0());
            contactNameTF9.setId("Data_provider_contact_email__0");
            transmissionDataGridID.add(contactName9, 0, 4);
            transmissionDataGridID.add(contactNameTF9, 1, 4);
            //transmissionDataGridID.setHgap(10);
            transmissionDataGridID.setAlignment(Pos.CENTER);
            //transmissionDataGridID.setGridLinesVisible(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    static void setReportLV(ListView<String> reportsLVID, TextArea reportDetailsTAID){
        reportsLVID.setItems(ReportUIHelper.geReportsArray());
        reportsLVID.setEditable(false);
        reportsLVID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                File report = ReportUIHelper.getFileFromString(newValue);
                readreport(report,  reportDetailsTAID);
            }
        });
    }
    static void readreport(File file, TextArea reportDetailsTAID){
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            reportDetailsTAID.appendText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    static void setTransformationListView(ListView<String> transformationListView){
        transformationListView.setItems(TransformationsUIHelper.getTransformationArray(null));
    }
    static void setJobLogsTextArea(TextArea jobLogsTextArea){
        jobLogsTextArea.setText("This is RSmart");
    }
    static void setJobMenuButtonID(MenuItem jobMenuButtonID, ListView<String> transformationListView){
        jobMenuButtonID.setOnAction(event -> {
            transformationListView.setItems(TransformationsUIHelper.getTransformationArray("kjb"));
        });
    }
    static void setTransformationMenuButtonID(MenuItem transformationMenuButtonID, ListView<String> transformationListView){
        transformationMenuButtonID.setOnAction(event -> {
            transformationListView.setItems(TransformationsUIHelper.getTransformationArray("kjb"));
        });
    }
    static void setRunTransformations(Button runTransformations, ListView<String> transformationListView, TextArea jobLogsTextArea){
        runTransformations.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ObservableList<String> selectedValues  = transformationListView.getSelectionModel().getSelectedItems();
                java.util.Iterator<String> files = selectedValues.iterator();
                while(files.hasNext()){
                    File transformation = TransformationsUIHelper.getFileFromString(files.next());
                    TextAreaAppender.setTextArea(jobLogsTextArea);
                    String returnString = kettle.RunTransformations.runTransformation(transformation.getPath());
                    jobLogsTextArea.setStyle("-fx-text-fill: green ;") ;
                };
            }
        });
    }
    static void setSelectBySubjectHeaderID(Menu SelectBySubjectHeaderID){
        ArrayList<String> SubjectHeaders = TransformationsUIHelper.getAllSubjectHeader();
        for (String header :SubjectHeaders) {
            MenuItem newSubjectHeader = new MenuItem();
            newSubjectHeader.setId(header+ "ID");
            newSubjectHeader.addEventHandler(MouseEvent.MOUSE_CLICKED,new SubjectHeaderButtonEventHandler());
            newSubjectHeader.setText(header);
            SelectBySubjectHeaderID.getItems().add(newSubjectHeader);
        }
    }
    static void setStateCAID(MenuItem stateCAID, TableView<RefernceDataPoJo> resultSetDataViewID){
        stateCAID.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Connection conn = new DatabaseAccessor().getConnection();
                try {
                    String queryString = Queries.byState;
                    //System.out.println(queryString);
                    PreparedStatement statement = conn.prepareStatement(queryString);
                    statement.setString(1, "CA");
                    ResultSet rs =  statement.executeQuery();
                    ObservableList<RefernceDataPoJo> list = FXCollections.observableArrayList();

                    while(rs.next()){
                        RefernceDataPoJo refernceData = new RefernceDataPoJo();
                        String elementName = rs.getString("NCCIELEMENTNAME");
                        String subjectheader = rs.getString("SUBJECTHEADER");
                        String offset = rs.getString("NCCIELEMENTOFFSET");
                        String length = rs.getString("NCCIELEMMENTLENGTH");
                        refernceData.set_elementName(elementName);
                        refernceData.set_subjectHeader(subjectheader);
                        refernceData.set_startPosition(offset);
                        refernceData.set_lenthOfElement(length);
                        list.add(refernceData);
                    }
                    resultSetDataViewID.setItems(list);
                    TableColumn<RefernceDataPoJo,String> elementName = new TableColumn<RefernceDataPoJo,String>("Element Name");
                    elementName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RefernceDataPoJo, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<RefernceDataPoJo, String> p) {
                            // p.getValue() returns the Person instance for a particular TableView row
                            return p.getValue()._elementNameProperty();
                        }
                    });
                    TableColumn<RefernceDataPoJo,String> subjectHeader = new TableColumn<RefernceDataPoJo,String>("Subject Header");
                    subjectHeader.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RefernceDataPoJo, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<RefernceDataPoJo, String> p) {
                            // p.getValue() returns the Person instance for a particular TableView row
                            return p.getValue()._subjectHeaderProperty();
                        }
                    });
                    TableColumn<RefernceDataPoJo,String> startingPosition = new TableColumn<RefernceDataPoJo,String>("Offset");
                    startingPosition.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RefernceDataPoJo, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<RefernceDataPoJo, String> p) {
                            // p.getValue() returns the Person instance for a particular TableView row
                            return p.getValue()._startPositionProperty();
                        }
                    });
                    TableColumn<RefernceDataPoJo,String> length = new TableColumn<RefernceDataPoJo,String>("Length");
                    length.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RefernceDataPoJo, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<RefernceDataPoJo, String> p) {
                            // p.getValue() returns the Person instance for a particular TableView row
                            return p.getValue()._lenthOfElementProperty();
                        }
                    });

                    resultSetDataViewID.getColumns().addAll(elementName, subjectHeader, startingPosition, length);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }finally {
                    try {
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

    }
    static void setTransformationListView(ListView<String> transformationListView, TreeView<String> treeViewTransformationID){
        transformationListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                File xmlFile = TransformationsUIHelper.getFileFromString(newValue);
                try {
                    TreeItem<String> root = null;
                    try {
                        root = readData(xmlFile);
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    treeViewTransformationID.setRoot(root);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static TreeItem<String> readData(File file) throws SAXException, ParserConfigurationException, IOException, org.xml.sax.SAXException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader =  parser.getXMLReader();
        TreeItemCreationContentHandler contentHandler = new TreeItemCreationContentHandler();

        // parse file using the content handler to create a TreeItem representation
        reader.setContentHandler(contentHandler);
        reader.parse(file.toURI().toString());

        // use first child as root (the TreeItem initially created does not contain data from the file)
        TreeItem<String> item = contentHandler.item.getChildren().get(0);

        contentHandler.item.getChildren().clear();
        return item;
    };
}

