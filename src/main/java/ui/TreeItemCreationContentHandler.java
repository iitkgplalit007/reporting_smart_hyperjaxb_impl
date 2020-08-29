package ui;


import javafx.scene.control.TreeItem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TreeItemCreationContentHandler extends DefaultHandler {

    public TreeItem<String> item = new TreeItem<>();

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.item = this.item.getParent();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        TreeItem<String> item = new TreeItem<>(qName);
        this.item.getChildren().add(item);
        this.item = item;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String s = String.valueOf(ch, start, length).trim();
        if (!s.isEmpty()) {
            // add text content as new child
            this.item.getChildren().add(new TreeItem<>(s));
        }
    }

}
