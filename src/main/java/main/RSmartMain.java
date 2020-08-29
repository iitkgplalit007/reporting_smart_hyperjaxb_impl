package main;



import hyperjaxb.CreateSchemaDB;
import kettle.RunTransformations;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

public class RSmartMain {
    public static final String APP_PROPERTIES = "/application.properties";
    public static final String APP_TRANSFORMATION_LOCATION = "app.transformationlocation";
    public static Properties _appProperties;
    public  static void main (String[] args) throws URISyntaxException {
        /*String [] schemaUrls = {"https://www.wcio.org/Active%20DSM/WCSTATXMLSC.xsd" *//*,
                "https://www.wcio.org/Active%20DSM/WCCRITXMLSC.xsd"
               "https://www.wcio.org/Active%20DSM/WCPOLSXMLSC.xsd",
                "https://www.wcio.org/Active%20DSM/WCRATINGXMLSC.xsd",
                "https://www.wcio.org/Active%20DSM/WCUNDERWRITINGXMLSC.xsd",
                "https://www.wcio.org/Active%20DSM/WCUNDERWRITINGXMLSC.xsd",
                "https://www.wcio.org/Active%20DSM/WCNOAXMLSC.xsd",
                *//*


        };
       //String schemaurl = "https://www.wcio.org/Active%20DSM/WCSTATXMLSC.xsd";
       SchemaDownloader schemaDownloader = SchemaDownloader.getInstance();
        try {
            setAppProperties();
            for(String schemaurl : schemaUrls){
                schemaDownloader.downLoadXsdFile(schemaurl, _appProperties);
            }
            CreateSchemaDB newDB = new CreateSchemaDB();
            String persistenceUnitName =   newDB.getpersistenceUnitName();
            System.out.println(persistenceUnitName);
            newDB.createEntityManager(persistenceUnitName);
            runTransformations();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        runTransformations();
    }
    public static void setAppProperties(){

        try {
            System.out.println(RSmartMain.class.getResource(APP_PROPERTIES));
            URL resource =  RSmartMain.class.getResource(APP_PROPERTIES);

            System.out.println(resource);
            File appPropertiesFile = new File(resource.getPath());
            _appProperties = new Properties();
            _appProperties.load(new FileInputStream(appPropertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void  runTransformations(){
        Collection<File> transformationFiles =  getListOfAllTransformationsFiles();
        for (File transformationFile : transformationFiles
             ) {
            RunTransformations.runTransformation(transformationFile.getPath());
            
        }
    }
    public static Collection getListOfAllTransformationsFiles(){
        if(_appProperties == null){
            setAppProperties();
        }
        String transformationLocation = _appProperties.getProperty(APP_TRANSFORMATION_LOCATION);
        String transDir  =  System.getProperty("user.dir")+ "" +  transformationLocation;
        File directory = new File(transDir);
        return FileUtils.listFiles(directory, new WildcardFileFilter("*.ktr"), null);
    }

}
