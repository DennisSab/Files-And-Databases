package database.tables;

import com.google.gson.Gson;
import mainClasses.Customer;
import database.DB_Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditCustomersTable {

    public void addCustomerFromJSON(String json) throws ClassNotFoundException{
        Customer c = jsonToCustomer(json);
        addNewCustomer(c);
    }

    public Customer jsonToCustomer(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Customer.class);
    }
    
    public String CustomerToJSON(Customer user){
        Gson gson = new Gson();
        return gson.toJson(user, Customer.class);
    }

    public Customer databaseToCustomers(String username , String password) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM customers WHERE username = '" + username + "' AND password = '" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json, Customer.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Customer databaseToCustomers(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM customers WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json, Customer.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createCustomersTable() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE customers "
                + "(customer_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(30) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    email VARCHAR(50) not null unique,"
                + "    card_details VARCHAR(32) not null,"
                + " PRIMARY KEY (customer_id))";

        stmt.execute(query);
        stmt.close();
    }

    public void addNewCustomer(Customer user) throws ClassNotFoundException{
        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " customers (username , password , firstname , lastname , email , card_details) "
                    + " VALUES ("
                    + "'" + user.getUsername()      + "',"
                    + "'" + user.getPassword()      + "',"
                    + "'" + user.getFirstname()     + "',"
                    + "'" + user.getLastname()      + "',"
                    + "'" + user.getEmail()         + "',"
                    + "'" + user.getCardDetails()   + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The customer was successfully added in the database.");

            stmt.close();
        }catch(SQLException ex){
            Logger.getLogger(EditCustomersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}