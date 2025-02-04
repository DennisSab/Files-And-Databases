package database;

import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DB_Connection{
    
    private static final String url = "jdbc:mysql://localhost";
    private static final String databaseName = "ticketάκη";
    private static final int port = 3306;
    private static final String username = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url + ":" + port + "/" + databaseName , username , password);
    }
    
    public static Connection getInitialConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url + ":" + port , username , password);
    }
      
    public static String getResultsToJSON(ResultSet rs) throws SQLException{
        JsonObject object = getResultsToJSONObject(rs);
        return object.toString();
    }
        
    public static JsonObject getResultsToJSONObject(ResultSet rs) throws SQLException{
       ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        JsonObject object = new JsonObject();

        for(int i = 1; i <= columnCount; i++){
            String name = metadata.getColumnName(i);
            String value = rs.getString(i);
            object.addProperty(name , value);
        }
        return object;
    }
}
