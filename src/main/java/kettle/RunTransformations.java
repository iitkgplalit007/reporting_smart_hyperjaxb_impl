package kettle;
import main.RSmartMain;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.core.logging.LogChannelFileWriterBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;


public class RunTransformations {


    public static RunTransformations getInstance(){return new RunTransformations();}
    public static void getAndRunAllTransaction(){
       Collection<File> transformations = RSmartMain.getListOfAllTransformationsFiles();
        for (File transformationFile : transformations
        ) {
            RunTransformations.runTransformation(transformationFile.getPath());

        }
    }
    public static void main(String[] args){
        getAndRunAllTransaction();
    }

    public static String runTransformation(String filename) {
        String returnString = null;

        try {
            KettleEnvironment.init();
            Document doc = XMLHandler.loadXMLFile(filename);
            NodeList fileRef = doc.getElementsByTagName("file");
            if(fileRef.getLength()>0){
                Node filenode =  fileRef.item(0) ;
                Node fileNameNode = null;
                for(int i=0; i<= filenode.getChildNodes().getLength(); i++){
                    if(filenode.getChildNodes().item(i).getNodeName().equals("name")){
                        fileNameNode = filenode.getChildNodes().item(i);
                        fileNameNode.setTextContent("C:\\reporting_smart_hyperjaxb_impl\\reference_data\\Reference Data.xlsx");
                        break;
                    }
                }
            }
            //TransMeta metaData = new TransMeta(filename);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(doc);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            TransMeta metaData = new TransMeta(is, null, true, null , null);
            Trans trans = new Trans( metaData );
            trans.execute( null );
            trans.waitUntilFinished();
            if ( trans.getErrors() > 0 ) {
                System.out.print( "Error Executing transformation" );
            }
        }
        catch ( KettleException e ) {
            // TODO Put your exception-handling code here.
            System.out.println(e);

        }catch (TransformerException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
