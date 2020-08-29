package pojo;

public class ElementDetailsPojo {

    String _subjectHeader;
     String _elementName;
     String _targetSchema;
     String _targetTable;
    String _elementType;
    int _startPosition;
    int _endPosition;
    int _lenthOfElement;
    String _class;
    String _recordNumber;

    public String get_recordNumber() {
        if(_recordNumber == null){
            return "-1";
        }else{
            if(_recordNumber.contains(".")){
               return _recordNumber.substring(0,_recordNumber.indexOf('.'));
            }
            return _recordNumber;
        }

    }

    public void set_recordNumber(String _recordNumber) {
        this._recordNumber = _recordNumber;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String get_subjectHeader() {
        return _subjectHeader;
    }

    public void set_subjectHeader(String _subjectHeader) {
        this._subjectHeader = _subjectHeader;
    }


    public String get_elementType() {
        return _elementType;
    }

    public void set_elementType(String _elementType) {
        this._elementType = _elementType;
    }


    public  String get_elementName() {
        return _elementName!=null?_elementName.trim(): _elementName;
    }

    public void set_elementName(String _elementName) {
        this._elementName = _elementName;
    }

    public String get_targetSchema() {
        return _targetSchema;
    }

    public void set_targetSchema(String _targetSchema) {
        this._targetSchema = _targetSchema;
    }

    public  String get_targetTable() {
        return _targetTable;
    }

    public void set_targetTable(String _targetTable) {
        this._targetTable = _targetTable;
    }

    public  int get_startPosition() {
        return _startPosition;
    }

    public void set_startPosition(int _startPosition) {
        this._startPosition = _startPosition;
    }

    public  int get_endPosition() {
        return _endPosition;
    }

    public  void set_endPosition(int _endPosition) {
        this._endPosition = _endPosition;
    }

    public int get_lenthOfElement() {
        return _lenthOfElement;
    }

    public void set_lenthOfElement(int _lenthOfElement) {
        this._lenthOfElement = _lenthOfElement;
    }

    public static  ElementDetailsPojo getInstance(){
     return new ElementDetailsPojo();
    }
}
