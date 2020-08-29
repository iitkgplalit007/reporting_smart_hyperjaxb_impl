package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/RSmart.fxml"));
        primaryStage.setTitle("RSmart");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        /*primaryStage.setTitle("RSmart");
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        //Group root = new Group();
        FileChooser fileChooser = new FileChooser();
        Menu file = new Menu("File");
        Menu help = new Menu("help");
        MenuItem close = new MenuItem("Exit");
        file.getItems().add(close);
        close.setOnAction(event -> {
            primaryStage.close();
        });
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(file);
        menuBar.getMenus().add(help);
        VBox root = new VBox(menuBar);
        Scene scene = new Scene(root, 960, 600);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        Tab reports = new Tab();
        reports.setText("Reports");
        Tab transformations = new Tab();
        transformations.setText("Transformation");
        Tab parse = new Tab();
        parse.setText("Parsing");

        HBox reportsbox = new HBox();
        reportsbox.getChildren().add(new Label("Reports"));
        reportsbox.setAlignment(Pos.CENTER);
        reports.setContent(reportsbox);
        reports.setClosable(false);
        tabPane.getTabs().add(reports);


        HBox transformationbox = new HBox();
        transformationbox.getChildren().add(new Label("Transformation"));
        transformationbox.setAlignment(Pos.CENTER);
        transformations.setContent(transformationbox);
        transformations.setClosable(false);
        tabPane.getTabs().add(transformations);

        HBox parsingbox = new HBox();
        parsingbox.getChildren().add(new Label("Parsing"));
        parsingbox.setAlignment(Pos.CENTER);
        parse.setContent(transformationbox);
        parse.setClosable(false);
        tabPane.getTabs().add(parse);


        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        //borderPane.getChildren().add(menuBar);
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
