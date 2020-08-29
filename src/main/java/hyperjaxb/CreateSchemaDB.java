package hyperjaxb;
import main.ProcessPresistenceXML;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import javax.persistence.*;
import java.io.InputStream;
import java.util.Properties;
import java.net.URL;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import javax.persistence.Persistence;

/*
import https.www_wcio_org.xml.wcstat.Address;
import https.www_wcio_org.xml.wcstat.ObjectFactory;
*/


public class CreateSchemaDB {
   /* public static void main(String[] args){
       String persistenceUnitName =   getpersistenceUnitName();
        System.out.println(persistenceUnitName);
        try{
            createEntityManager(persistenceUnitName);
        }catch (IOException e){
            e.printStackTrace();
        }

    }*/

    public static void createEntityManager(String persistenceUnitName) throws IOException {
        final Properties persistenceProperties = new Properties();
        InputStream is = null;
        try {
            is = CreateSchemaDB.class.getClassLoader().getResourceAsStream(
                    "persistence.properties");
            System.out.println(is.available());
            try {
                persistenceProperties.load(is);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        Configuration configuration = new Configuration();
        URL hibernateConfXml =  CreateSchemaDB.class.getResource("/hibernate.cfg.xml");
        configuration.configure(hibernateConfXml);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                    persistenceUnitName, configuration.getProperties() );
            final Long id = (long) 1234567890;
            final EntityManager loadManager = emf
                    .createEntityManager();



       /* final Address beta = loadManager.find(
                Address.class, id);
        String contextPath =  "https.www_wcio_org.xml.wcstat";
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(contextPath);
            Marshaller marshaller = context.createMarshaller();
            ObjectFactory objectFactory = new ObjectFactory();
            marshaller.marshal(beta, System.out);
            loadManager.close();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }
    public static String getpersistenceUnitName(){
        String persistenceUnitName = null;
        ProcessPresistenceXML newProcessPresistenceXML = new ProcessPresistenceXML();
        URL persistenceXMlURL = CreateSchemaDB.class.getResource("/META-INF/persistence.xml");
        if(persistenceXMlURL != null){
            Document doc =  newProcessPresistenceXML.getXMLDocument(persistenceXMlURL.getPath());
            java.util.ArrayList<Element> nodes =  newProcessPresistenceXML.getElementByTagName(doc, "persistence-unit");
            if(nodes.size() > 0){
                Element persistenceUnitNode = nodes.get(0);
                Attr atts  =  persistenceUnitNode.getAttributeNode("name");
                if(atts != null){
                    persistenceUnitName = atts.getValue();
                }
            }
        }
        return persistenceUnitName ;
    }
}
