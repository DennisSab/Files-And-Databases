package database.tables;

import com.google.gson.Gson;
import mainClasses.Booking;
import database.DB_Connection;
import mainClasses.Event;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditBookingsTable{

    public void addBookingFromJSON(String json) throws ClassNotFoundException{
        Booking b = jsonToBooking(json);
        createNewBooking(b);
    }

    public Booking jsonToBooking(String json){
        Gson gson = new Gson();
        Booking b = gson.fromJson(json, Booking.class);
        JSONObject jsonObject = new JSONObject(json);

        String dateValue = jsonObject.getString("date");
        b.setDate(dateValue);
        return b;
    }

    public String bookingToJSON(Booking b){
        Gson gson = new Gson();
        return gson.toJson(b , Booking.class);
    }

    public Booking databaseToBooking(String id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE booking_id = '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Booking.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Booking> getBookings(int customer_id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE customer_id = '" + customer_id + "'");

            while(rs.next()){
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking b = gson.fromJson(json, Booking.class);
                bookings.add(b);
            }
            return bookings;
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String generateBookingsTable(int customer_id){
        StringBuilder htmlTable = new StringBuilder();
        EditEventsTable eet = new EditEventsTable();

        try{
            ArrayList<Booking> bookings = getBookings(customer_id);
            if(bookings != null && !bookings.isEmpty()){
                htmlTable.append("<table border=\"1\">");
                htmlTable.append("<thead>");
                htmlTable.append("<tr>");
                htmlTable.append("<th>ID</th>");
                htmlTable.append("<th>Event Name</th>");
                htmlTable.append("<th>Date</th>");
                htmlTable.append("<th>Cost</th>");
                htmlTable.append("<th>Ticket Amount</th>");
                htmlTable.append("<th></th>");
                htmlTable.append("</tr>");
                htmlTable.append("</thead>");
                htmlTable.append("<tbody>");

                for(Booking b : bookings){
                    htmlTable.append("<tr>");
                    htmlTable.append("<td>").append(b.getId()).append("</td>");
                    Event e = eet.databaseToEvents(String.valueOf(b.getEventId()));
                    htmlTable.append("<td>").append(e.getName()).append("</td>");
                    htmlTable.append("<td>").append(b.getDate()).append("</td>");
                    htmlTable.append("<td>").append(b.getCost()).append("</td>");
                    htmlTable.append("<td>").append(b.getTicketQuantity()).append("</td>");
                    htmlTable.append("<td>").append("<button class=\"btn\" onclick=\"selectBooking('").append(b.getId()).append("')\">Select</button>").append("</td>");
                    htmlTable.append("</tr>");
                }
                htmlTable.append("</tbody>");
                htmlTable.append("</table>");
            }else{
                htmlTable.append("No data available.");
            }
        }catch(SQLException | ClassNotFoundException e){
            htmlTable.append("Error: ").append(e.getMessage());
        }
        return htmlTable.toString();
    }

    public void deleteBooking(String id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM bookings WHERE booking_id = '" + id + "'");
        stmt.close();
        con.close();
    }

    public void deleteBookings(String event_id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM bookings WHERE event_id = '" + event_id + "'");
        stmt.close();
        con.close();
    }

    public void createBookingTable() throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String sql = "CREATE TABLE bookings"
                + "(booking_id INTEGER not NULL AUTO_INCREMENT,"
                + "customer_id INTEGER not NULL,"
                + "event_id INTEGER not NULL,"
                + "ticket_id INTEGER not NULL,"
                + "date DATE not NULL,"
                + "cost INTEGER not NULL,"
                + "quantity INTEGER not NULL,"
                + "FOREIGN KEY (customer_id) REFERENCES customers(customer_id),"
                + "FOREIGN KEY (event_id) REFERENCES events(event_id), "
                + "FOREIGN KEY (ticket_id) REFERENCES tickets_type(ticket_id) ON DELETE CASCADE ON UPDATE CASCADE,  "
                + "PRIMARY KEY (booking_id))";

        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    public void createNewBooking(Booking b) throws ClassNotFoundException{
        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + "bookings (customer_id , event_id , ticket_id , date , cost , quantity) "
                    + "VALUES ("
                    + "'" + b.getCustomerId()       + "',"
                    + "'" + b.getEventId()          + "',"
                    + "'" + b.getTicketId()         + "',"
                    + "'" + b.getDate()             + "',"
                    + "'" + b.getCost()             + "',"
                    + "'" + b.getTicketQuantity()     + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            System.out.println("# The booking was successfully added in the database.");
            stmt.close();
        }catch(SQLException ex){
            Logger.getLogger(EditBookingsTable.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}