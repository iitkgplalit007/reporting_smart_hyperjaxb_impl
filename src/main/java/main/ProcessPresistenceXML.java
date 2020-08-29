package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import hyperjaxb.CreateSchemaDB;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ProcessPresistenceXML {
    public URL getPersistenceXMLURL(){
        URL persistenceXML = CreateSchemaDB.class.getResource("/META-INF/persistence.xml");
        return persistenceXML;
    }
    public Document getXMLDocument(String xmlPath){
        Document doc = null;
        try {
        File fXmlFile = new File(xmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return doc;
    }
    public java.util.ArrayList<Element> getElementByTagName(Document doc, String tagName){
        java.util.ArrayList<Element> elements = new java.util.ArrayList<Element>();
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(tagName);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                elements.add(eElement);
        }
    }
        return elements;
    }
}
