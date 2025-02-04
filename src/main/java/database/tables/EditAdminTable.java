package database.tables;

import com.google.gson.Gson;
import mainClasses.Admin;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditAdminTable{

    public void addAdminFromJSON(String json) throws ClassNotFoundException{
        Admin ad = jsonToAdmin(json);
        addNewAdmin(ad);
    }

    public Admin jsonToAdmin(String json){
        Gson gson = new Gson();
        return gson.fromJson(json , Admin.class);
    }

    public String AdminToJSON(Admin user){
        Gson gson = new Gson();
        return gson.toJson(user , Admin.class);
    }

    public Admin databaseToAdmins(String username , String password) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try{
            rs = stmt.executeQuery("SELECT * FROM admin WHERE username = '" + username + "' AND password = '" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Admin.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Admin databaseToAdmins(String username) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try{
            rs = stmt.executeQuery("SELECT * FROM admin WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Admin.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createAdminTable() throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE admin "
                + "(admin_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    password VARCHAR(30) not null,"
                + " PRIMARY KEY (admin_id))";

        stmt.execute(query);
        stmt.close();
    }

    public void addNewAdmin(Admin user) throws ClassNotFoundException{
        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " admin (username , password) "
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getPassword() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The admin was successfully added in the database.");
            stmt.close();
        }catch(SQLException ex){
            Logger.getLogger(EditCustomersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}