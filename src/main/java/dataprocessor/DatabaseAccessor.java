package dataprocessor;

import org.apache.commons.lang3.tuple.Pair;
import pojo.PreparedStatementParameterPojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class DatabaseAccessor {
   static String _userID = null;
   static String _password = null;
   static String _jdbcurl = null;
   static String _applicationPath = null;
   static final String APP_PROPERTIES = "/application.properties";
   static Properties _appProperties;

    public static Properties get_appProperties() {
        return _appProperties;
    }

    public void setAppProperties(){
        try {
            System.out.println(DatabaseAccessor.class.getResource(APP_PROPERTIES));
            URL resource =  DatabaseAccessor.class.getResource(APP_PROPERTIES);

            System.out.println(resource);
            File appPropertiesFile = new File(resource.getPath());
            _appProperties = new Properties();
            _appProperties.load(new FileInputStream(appPropertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatabaseAccessor() {
        setAppProperties();
        set_applicationPath(null);
        set_userID();
        set_password();
        set_jdbcurl();
    }

    public static String get_applicationPath() {
        return _applicationPath;
    }

    public static void set_applicationPath(String _applicationPath) {
        if(_appProperties.getProperty("app.outputFolderLocation") != null){
            _applicationPath = _appProperties.getProperty("app.outputFolderLocation");
        }
    }

    public void set_userID(){
        if(_appProperties.getProperty("app.staginguserid") != null){
            _userID = _appProperties.getProperty("app.staginguserid");
        }
    }
    public void set_password(){
        if(_appProperties.getProperty("app.stagingdbpassword") != null){
            _password = _appProperties.getProperty("app.stagingdbpassword");
        }
    }
    public void set_jdbcurl(){
        if(_appProperties.getProperty("app.stagingjdbcurl") != null){
            _jdbcurl = _appProperties.getProperty("app.stagingjdbcurl");
        }
    }
     public Connection getConnection(){
/*         this.setAppProperties();
         this.set_jdbcurl();
         this.set_userID();
         this.set_password();
         System.out.println(_jdbcurl);
         System.out.println(_userID);
         System.out.println(_password);*/
         Connection conn = null;
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             conn = DriverManager.getConnection(_jdbcurl,
                     _userID, _password);
             System.out.println("connected");

         } catch (Exception e) {
             e.printStackTrace();
         }
         return conn;
     }
     /*
     @param query = the string of the prepared statement with ?. The parameters are not set on the query yet.
     @param java.util.ArrayList<PreparedStatementParameterPojo> parameterSet = The list of the PreparedStatementParameterPojo containing the index of the
                                                                          parameter to be set and the values corresponding to that index.
     @return java.util.HashMap<Pair<String, String>, java.util.ArrayList<Object>> = HashMap of the column details, which includes the name and the class
                                                                           type of the column. values of the map are the values of the data elements retrieved from table.
      */
     public static java.util.HashMap<Pair<String, String>, java.util.ArrayList<Object>> runPreparedStatement(String query, java.util.ArrayList<PreparedStatementParameterPojo> parameterSet){
         Connection conn = new DatabaseAccessor().getConnection();
         java.util.ArrayList<Pair<String, String>>  columnDef = new java.util.ArrayList<Pair<String, String>>();
         java.util.HashMap<Pair<String, String>, java.util.ArrayList<Object>> dataDetails = new java.util.HashMap<Pair<String, String>, java.util.ArrayList<Object>>();
         try{
            PreparedStatement statement = createPreparedStatement(conn , query, parameterSet);
            ResultSet rs = statement.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount  = metadata.getColumnCount();
            for (int i=1; i<=columnCount;i++) {
                String  columnName = metadata.getColumnName(i) ;
                String  ColumnType  = metadata.getColumnTypeName(i);
                Pair<String, String>  colNameAndType = Pair.of(columnName, ColumnType);
                columnDef.add(colNameAndType);
            }

            while(rs.next()) {
                for (Pair<String, String> coldef : columnDef) {
                    Object value = null;
                    if (coldef.getRight().equals("datetime")) {
                        value = rs.getDate(coldef.getLeft());
                    } else if (coldef.getRight().equals("int")) {
                        value = rs.getInt(coldef.getLeft());
                    } else if (coldef.getRight().equals("numeric")) {
                        value = rs.getDouble(coldef.getLeft());
                    } else if (coldef.getRight().equals("varchar")) {
                        value = rs.getString(coldef.getLeft());
                    } else {
                        value = rs.getString(coldef.getLeft());
                    }
                    if(!dataDetails.containsKey(coldef)){
                        java.util.ArrayList<Object> newResultArray = new java.util.ArrayList<Object>();
                        newResultArray.add(value);
                        dataDetails.put(coldef, newResultArray);
                    }else{
                        java.util.ArrayList<Object> existingResultArray =  dataDetails.get(coldef);
                        existingResultArray.add(value);
                        dataDetails.put(coldef, existingResultArray);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataDetails;
     }
     public static PreparedStatement createPreparedStatement(Connection conn , String query, java.util.ArrayList<PreparedStatementParameterPojo> parameterList){
         PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            ParameterMetaData paramMetadata =  statement.getParameterMetaData();
            if(paramMetadata.getParameterCount() >0 ){
                for (PreparedStatementParameterPojo param : parameterList) {
                    String parameterType  =  paramMetadata.getParameterTypeName(param.getIndex());
                    switch(parameterType){
                        case "datetime":
                            statement.setDate(param.getIndex(), (java.sql.Date)param.getColumnValue());
                            break;
                        case "int":
                            statement.setInt(param.getIndex(), (int)param.getColumnValue());
                            break;
                        case "numeric":
                            statement.setBigDecimal(param.getIndex(), (java.math.BigDecimal)param.getColumnValue());
                            break;
                        case "varchar":
                            statement.setString(param.getIndex(), (String)param.getColumnValue());
                            break;
                        default:
                            //TODO: find other Data elements.
                    }
                }
            }

         } catch (SQLException e) {
             e.printStackTrace();
         }
         return statement;
     }

}
