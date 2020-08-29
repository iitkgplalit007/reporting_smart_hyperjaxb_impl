package ui;

import dataprocessor.DatabaseAccessor;
import dataprocessor.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class TransformationsUIHelper {
     static ListView<File> getTransformation(){
         ListView<File> transformations  =  new ListView<File>();

         return transformations;
     }
     static ObservableList<String> getTransformationArray(String fileExten){
         ArrayList<String> trans = new ArrayList<String>();
            String projectDirectory =   System.getProperty("user.dir");
         System.out.println(projectDirectory);
            File transformationFolder = new File(projectDirectory+ "/reporting_smart_hyperjaxb_impl/transformations");
         File[] transformations = null;
            if(fileExten != null){
                transformations  = transformationFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith("." + fileExten);
                    }
                });
            }else{
                transformations  = transformationFolder.listFiles();
            }

            for(int i=0;i<transformations.length; i++){
                trans.add(transformations[i].getName());
                System.out.println(transformations[i].getName());
            }
         ObservableList<String> data = FXCollections.observableArrayList(trans);
            return data ;
     }
     public static void main(String[] args){
         getTransformationArray(null);
     }
     public static File getFileFromString(String fileName){
         String projectDirectory =   System.getProperty("user.dir");
         File transformationFolder = new File(projectDirectory+ "/reporting_smart_hyperjaxb_impl/transformations");
         File[] transformations = null;
         transformations  = transformationFolder.listFiles();
         for (File trans :transformations) {
             if(trans.getName().equals(fileName)){
                 return trans;
             }
         }
         return null;
     }
     static ArrayList<String> getAllSubjectHeader()  {
         ArrayList<String> subjectHeaders = new ArrayList<String>();
         Connection conn = new DatabaseAccessor().getConnection();
         try{
             String queryString = Queries.subjectHeader;
             //System.out.println(queryString);
             PreparedStatement statement = conn.prepareStatement(queryString);
             ResultSet rs =  statement.executeQuery();
             while(rs.next()){
                 subjectHeaders.add(rs.getString("NAME"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }finally {
             try {
                 conn.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         return subjectHeaders;
     }
    static ResultSet getbyStateReferenceData(String State) throws SQLException {
        ResultSet rs = null;
        Connection conn = new DatabaseAccessor().getConnection();
        try{
            String queryString = Queries.byState;
            //System.out.println(queryString);
            PreparedStatement statement = conn.prepareStatement(queryString);
            statement.setString(1, State);
            rs =  statement.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return rs;
    }
}
