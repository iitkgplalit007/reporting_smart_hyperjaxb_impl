package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import pojo.RefernceDataPoJo;

import java.util.Calendar;

public class RSmartController {
    @FXML
    private Button RunTransformations;

    @FXML
    private ListView<String> TransformationListView;

    @FXML
    private TextFlow TransformationLog;

    @FXML
    private TextArea JobLogsTextArea;

    @FXML
    private MenuButton GetResourcesMenuButtonID;

    @FXML
    private MenuItem TransformationMenuButtonID;

    @FXML
    private MenuItem JobMenuButtonID;

    @FXML
    private Menu SelectByReportTypeID;

    @FXML
    private Menu SelectBYStateID;

    @FXML
    private MenuItem StateCAID;

    @FXML
    private Menu SelectBySubjectHeaderID;

    @FXML
    private MenuItem StateCAID1;

    @FXML
    private TableView<RefernceDataPoJo> ResultSetDataViewID;

    @FXML
    private TreeView<String> XMLTreeViewID;


    @FXML
    private SplitPane SplitPanelTransformationID;

    @FXML
    private TreeView<String> TreeViewTransformationID;

    @FXML
    private Button RunReportID;

    @FXML
    private DatePicker RunReportDateID;

    @FXML
    private ListView<String> ReportsLVID;

    @FXML
    private TextArea ReportDetailsTAID;

    @FXML
    private Button CreateNewTransmissionButtonID;

    @FXML
    private GridPane TransmissionDataGridID;

    @FXML
    private AnchorPane BottomPaneReportsID;

    @FXML
    private SplitPane SplitPaneReportsID;


    @FXML
    private TabPane ReportParsingLowerPaneID;

    @FXML
    private Button ParseID;

    @FXML
    private java.sql.Date TodaysSQLDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    @FXML
    public void initialize() {
        ControllerInitialization.runReportInitialization(RunReportID);
        ControllerInitialization.parseFile(ReportParsingLowerPaneID, ParseID, null);
        ControllerInitialization.createNewTransmission(CreateNewTransmissionButtonID, TransmissionDataGridID);
        ControllerInitialization.loadTransmission(TransmissionDataGridID);
        ControllerInitialization.setReportLV(ReportsLVID, ReportDetailsTAID);
        ControllerInitialization.setTransformationListView(TransformationListView);
        ControllerInitialization.setJobLogsTextArea(JobLogsTextArea);
        ControllerInitialization.setJobMenuButtonID(JobMenuButtonID, TransformationListView);
        ControllerInitialization.setTransformationMenuButtonID(TransformationMenuButtonID, TransformationListView);
        ControllerInitialization.setRunTransformations(RunTransformations, TransformationListView, JobLogsTextArea);
        ControllerInitialization.setSelectBySubjectHeaderID(SelectBySubjectHeaderID);
        ControllerInitialization.setStateCAID(StateCAID, ResultSetDataViewID);
        ControllerInitialization.setTransformationListView(TransformationListView, TreeViewTransformationID);
    }
}

