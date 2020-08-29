package ui.ngui;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class TransformationParser {
    public static void main (String args[]) {
        String projectDirectory =   System.getProperty("user.dir");
        System.out.println(projectDirectory);
        File transformationFolder = new File(projectDirectory+ "/reporting_smart_hyperjaxb_impl/transformations");
        if(transformationFolder.isDirectory()){
            for (File file :transformationFolder.listFiles()) {
                if(file.getName().endsWith("ktr")){
                    parseTransformation(file);
                    break;
                }
            }
        }

    }

    public static void parseTransformation(File file){
        try{
            System.out.println(file.getName());
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    // do something with the current element
                    //System.out.println(node.getNodeName());

                    if(node.getNodeName().equals("step")){
                        //System.out.println(node);
                        List<Node> nodes =  traverse(node);
                        for (Node n :nodes) {
                            nodeToString(n);
                            //System.out.println(n.getNodeValue());
                            //System.out.println("NAME : " +  n.getNodeName());
                        }

                       /* if(node.hasChildNodes())
                        {
                            NodeList list = node.getChildNodes();
                            for(int k=0; k<list.getLength(); k++){
                                Node childnode = list.item(k);
                                if (node.getNodeType() == Node.ELEMENT_NODE) {
                                    System.out.println("NAME : " +  childnode.getNodeName()
                                    + " VALUE : " +  node.getTextContent());
                                }
                            }
                            //System.out.println(node.getAttributes());
                        }*/
                        //System.out.println(node.getNodeName() + node.getTextContent());
                    }

                }
            }
        }catch(Exception e){

        }

    }
    private static List<Node> traverse(Node n)
    {
        return traverse(Arrays.asList(n));
    }

    /* traverses tree starting with given nodes */
    private static List<Node> traverse(List<Node> nodes)
    {
        List<Node> open = new LinkedList<Node>(nodes);
        List<Node> visited = new LinkedList<Node>();

        ListIterator<Node> it = open.listIterator();
        while (it.hasNext() || it.hasPrevious())
        {
            Node unvisited;
            if (it.hasNext())
                unvisited = it.next();
            else
                unvisited = it.previous();

            it.remove();

            List<Node> children = getChildren(unvisited);
            for (Node child : children)
                it.add(child);

            visited.add(unvisited);
        }

        return visited;
    }

    private static List<Node> getChildren(Node n)
    {
        List<Node> children = asList(n.getChildNodes());
        Iterator<Node> it = children.iterator();
        while (it.hasNext())
            if (it.next().getNodeType() != Node.ELEMENT_NODE)
                it.remove();
        return children;
    }

    private static List<Node> asList(NodeList nodes)
    {
        List<Node> list = new ArrayList<Node>(nodes.getLength());
        for (int i = 0, l = nodes.getLength(); i < l; i++)
            if(!nodes.item(i).getTextContent().isEmpty()){
                list.add(nodes.item(i));
            }
        return list;
    }
    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        System.out.println(sw.toString());
        return sw.toString();
    }
}
