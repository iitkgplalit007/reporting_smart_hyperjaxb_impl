package dataprocessor;

import pojo.ElementDetailsPojo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HandleSubjectHeader {

    String  prepareSubjectHeaderDataQuery();

    void processResultSet();


}
