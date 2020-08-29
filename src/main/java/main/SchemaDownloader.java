package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;


public class SchemaDownloader {
    private String xsdName;
    public static final String APP_SCHEMALOCATION = "app.schemalocation";

    public static SchemaDownloader getInstance(){
        return new SchemaDownloader();
    }

    public void downLoadXsdFile(String xsdUrl, Properties appProperty) throws IOException, URISyntaxException {

        String schemaLocation = appProperty.getProperty("app.schemalocation");
        String schemaDir  =  System.getProperty("user.dir")+ "" +  schemaLocation;
        System.out.println("123 " +  schemaDir);
        File dir = new File(schemaDir);
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        xsdName = xsdUrl.substring(xsdUrl.lastIndexOf("/"));
        File xsdfile = new File( schemaDir + xsdName);
        System.out.println(new File(schemaDir).isDirectory());
        URL url = null;
        System.out.println("\nDownloading " + xsdfile.getName() + "...");
        try {
            url = new URL(xsdUrl);
        } catch(MalformedURLException e) {
            System.out.println("Error: Xsd url is not valid");
            e.printStackTrace();
        }
        System.out.println(xsdfile);
        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(xsdfile));

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead == -1)
                break;
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        System.out.println("Download successful...");
    }
}
