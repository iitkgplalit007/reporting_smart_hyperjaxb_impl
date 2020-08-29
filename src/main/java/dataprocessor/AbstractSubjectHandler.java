package dataprocessor;

import pojo.ElementDetailsPojo;

import java.util.ArrayList;

public class AbstractSubjectHandler implements HandleSubjectHeader {
    private String _subjectHeader;
    private ArrayList<ElementDetailsPojo> _elementDetails;

    public String get_subjectHeader() {
        return _subjectHeader;
    }

    public void set_subjectHeader(String _subjectHeader) {
        this._subjectHeader = _subjectHeader;
    }

    public ArrayList<ElementDetailsPojo> get_elementDetails() {
        return _elementDetails;
    }

    public void set_elementDetails(ArrayList<ElementDetailsPojo> _elementDetails) {
        this._elementDetails = _elementDetails;
    }

    @Override
    public String prepareSubjectHeaderDataQuery() {
       return null;
    }

    @Override
    public void processResultSet() {

    }
}
