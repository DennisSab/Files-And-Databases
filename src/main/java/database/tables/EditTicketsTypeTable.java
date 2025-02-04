package database.tables;

import mainClasses.Ticket;
import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditTicketsTypeTable {

    public void addTicketFromJSON(String json) throws ClassNotFoundException{
        Ticket t = jsonToTicket(json);
        createNewTicket(t);
    }

    public Ticket jsonToTicket(String json){
        Gson gson = new Gson();
        return gson.fromJson(json , Ticket.class);
    }

    public String ticketToJSON(Ticket t){
        Gson gson = new Gson();
        return gson.toJson(t , Ticket.class);
    }

    public void deleteTickets(String event_id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM tickets_type WHERE event_id = '" + event_id + "'");
        stmt.close();
        con.close();
    }

    public Ticket databaseToTicket(String event_id , String type) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM tickets_type WHERE event_id = '" + event_id + "' AND type = '" + type + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Ticket.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Ticket databaseToTicket(String id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM tickets_type WHERE ticket_id = '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Ticket.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void returnTicket(String id , int quantity) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        Ticket t = ett.databaseToTicket(id);

        stmt.executeUpdate("UPDATE tickets_type SET availability = '" + (t.getAvailability() + quantity) + "' WHERE ticket_id = '" + id + "'");
    }

    public void bookTicket(String id , int quantity) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        Ticket t = ett.databaseToTicket(id);

        stmt.executeUpdate("UPDATE tickets_type SET availability = '" + (t.getAvailability() - quantity) + "' WHERE ticket_id = '" + id + "'");
    }

    public void createTicketsTable() throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String sql = "CREATE TABLE tickets_type"
                + "(ticket_id INTEGER not NULL AUTO_INCREMENT, "
                + "event_id INTEGER not null,"
                + "type VARCHAR(20) not null,"
                + "price INTEGER not null,"
                + "availability INTEGER not null, "
                + "FOREIGN KEY (event_id) REFERENCES events(event_id), "
                + "PRIMARY KEY (ticket_id))";

        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    public void createNewTicket(Ticket t) throws ClassNotFoundException{
        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " tickets_type(event_id , type , price , availability)"
                    + " VALUES("
                    + "'" + t.getEventId()      + "',"
                    + "'" + t.getType()         + "',"
                    + "'" + t.getPrice()        + "',"
                    + "'" + t.getAvailability() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The ticket type was successfully added in the database.");
            stmt.close();
        }catch(SQLException ex){
            Logger.getLogger(EditTicketsTypeTable.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}
