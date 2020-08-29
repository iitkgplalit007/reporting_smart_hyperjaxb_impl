package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ReportUIHelper {
    static ObservableList<String> geReportsArray(){
        ArrayList<String> trans = new ArrayList<String>();
        String projectDirectory =   System.getProperty("user.dir");
        System.out.println(projectDirectory);
        File transformationFolder = new File(projectDirectory+ "/reporting_smart_hyperjaxb_impl/output");
        File[] transformations = transformationFolder.listFiles();
        for(int i=0;i<transformations.length; i++){
            trans.add(transformations[i].getName());
            System.out.println(transformations[i].getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(trans);
        return data ;
    }
    public static File getFileFromString(String fileName){
        String projectDirectory =   System.getProperty("user.dir");
        File transformationFolder = new File(projectDirectory+ "/reporting_smart_hyperjaxb_impl/output");
        File[] transformations = null;
        transformations  = transformationFolder.listFiles();
        for (File trans :transformations) {
            if(trans.getName().equals(fileName)){
                return trans;
            }
        }
        return null;
    }
}
