package util;

import dataprocessor.DatabaseAccessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class FileUtil {
    public static final String APP_PROPERTIES = "/application.properties";
    File createFile(String reportType){
        File file = null;
        try{
            Properties properties =  new DatabaseAccessor().get_appProperties();

            file = new File(System.getProperty("user.dir") + properties.getProperty("app.outputFolderLocation") + getFileName(reportType) );
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
        return file;
    }
    private String getFileName(String FilePrefix){
        //FilePrefix +  TodaysSQLDate.toString().replaceAll("[^a-zA-Z0-9]", "");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return FilePrefix+timeStamp;
    }
    public void writeToTheFile(StringBuffer sb, String reportType){
        try {
            BufferedWriter bwf = new  BufferedWriter(new FileWriter(createFile(reportType)));
            bwf.write(sb.toString());
            bwf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
