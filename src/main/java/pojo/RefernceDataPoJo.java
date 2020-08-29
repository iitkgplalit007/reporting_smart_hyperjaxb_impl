package pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RefernceDataPoJo {
    SimpleStringProperty _elementName = new SimpleStringProperty();
    SimpleStringProperty  _subjectHeader = new SimpleStringProperty();
    SimpleStringProperty  _startPosition = new SimpleStringProperty();
    SimpleStringProperty  _lenthOfElement = new SimpleStringProperty();

    public  String get_elementName() {
        return _elementName.get();
    }

    public SimpleStringProperty _elementNameProperty() {
        return _elementName;
    }

     public void set_elementName(String _elementName) {
        this._elementName.set(_elementName);
    }

    public String get_subjectHeader() {
        return _subjectHeader.get();
    }

    public SimpleStringProperty _subjectHeaderProperty() {
        return _subjectHeader;
    }

    public void set_subjectHeader(String _subjectHeader) {
        this._subjectHeader.set(_subjectHeader);
    }

    public String get_startPosition() {
        return _startPosition.get();
    }

    public SimpleStringProperty _startPositionProperty() {
        return _startPosition;
    }

    public void set_startPosition(String _startPosition) {
        this._startPosition.set(_startPosition);
    }

    public String get_lenthOfElement() {
        return _lenthOfElement.get();
    }

    public SimpleStringProperty _lenthOfElementProperty() {
        return _lenthOfElement;
    }

    public void set_lenthOfElement(String _lenthOfElement) {
        this._lenthOfElement.set(_lenthOfElement);
    }
}
