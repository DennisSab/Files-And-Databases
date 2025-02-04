package database.tables;

import mainClasses.Event;
import com.google.gson.Gson;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventsTable{

    public void addEventFromJSON(String json) throws ClassNotFoundException{
        Event e = jsonToEvent(json);
        addNewEvent(e);
    }

    public Event jsonToEvent(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Event.class);
    }
    
    public String EventToJSON(Event user){
         Gson gson = new Gson();
        return gson.toJson(user, Event.class);
    }

    public Event databaseToEvents(String id) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM events WHERE event_id = '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Event.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Event databaseToEventsByName(String name) throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM events WHERE name = '" + name + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            return gson.fromJson(json , Event.class);
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Event> getEvents() throws SQLException , ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Event> events = new ArrayList<Event>();
        ResultSet rs;

        try{
            rs = stmt.executeQuery("SELECT * FROM events");

            while(rs.next()){
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Event owner = gson.fromJson(json, Event.class);
                events.add(owner);
            }
            return events;
        }catch(Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String generateEventsTable(String user_type){
        StringBuilder htmlTable = new StringBuilder();

        try{
            ArrayList<Event> events = getEvents();
            if(events != null && !events.isEmpty()){
                htmlTable.append("<table border=\"1\">");
                htmlTable.append("<thead>");
                htmlTable.append("<tr>");
                htmlTable.append("<th>ID</th>");
                htmlTable.append("<th>Name</th>");
                htmlTable.append("<th>Date</th>");
                htmlTable.append("<th>Time</th>");
                htmlTable.append("<th>Type</th>");
                htmlTable.append("<th>Capacity</th>");
                htmlTable.append("<th>Refund</th>");
                htmlTable.append("<th></th>");
                htmlTable.append("</tr>");
                htmlTable.append("</thead>");
                htmlTable.append("<tbody>");

                for(Event ev : events){
                    htmlTable.append("<tr>");
                    htmlTable.append("<td>").append(ev.getId()).append("</td>");
                    htmlTable.append("<td>").append(ev.getName()).append("</td>");
                    htmlTable.append("<td>").append(ev.getDate()).append("</td>");
                    htmlTable.append("<td>").append(ev.getTime()).append("</td>");
                    htmlTable.append("<td>").append(ev.getType()).append("</td>");
                    htmlTable.append("<td>").append(ev.getCapacity()).append("</td>");
                    htmlTable.append("<td>").append(ev.getRefund()).append("</td>");
                    if(Objects.equals(user_type , "admin")){
                        htmlTable.append("<td>").append("<button class=\"btn\" onclick=\"deleteEverything('").append(ev.getId()).append("')\">Delete</button>").append("</td>");
                    }else{
                        htmlTable.append("<td>").append("<button class=\"btn\" onclick=\"selectEvent('").append(ev.getId()).append("')\">Select</button>").append("</td>");
                    }
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

    public void deleteEvent(String id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("DELETE FROM events WHERE event_id ='" + id + "'");
        stmt.close();
        con.close();
    }

    public void createEventsTable() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE events"
                + "(event_id INTEGER not NULL AUTO_INCREMENT, "
                + "admin_id INTEGER not null,"
                + "name VARCHAR(30) not null unique,"
                + "date DATE not null,"
                + "time TIME not null,"
                + "type VARCHAR(30) not null,"
                + "capacity INTEGER not null,"
                + "refund VARCHAR(10) not null,"
                + "FOREIGN KEY (admin_id) REFERENCES admin(admin_id),"
                + "PRIMARY KEY (event_id))";

        stmt.execute(query);
        stmt.close();
    }

    public void addNewEvent(Event user) throws ClassNotFoundException{
        try{
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + "events(admin_id , name , date , time , type , capacity , refund) "
                    + "VALUES ("
                    + "'" + user.getAdminId()   + "',"
                    + "'" + user.getName()      + "',"
                    + "'" + user.getDate()      + "',"
                    + "'" + user.getTime()      + "',"
                    + "'" + user.getType()      + "',"
                    + "'" + user.getCapacity()  + "',"
                    + "'" + user.getRefund()    + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The event was successfully added in the database.");
            stmt.close();
        }catch(SQLException ex){
            Logger.getLogger(EditEventsTable.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}