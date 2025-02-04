package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditCustomersTable;
import database.tables.EditEventsTable;
import database.tables.EditTicketsTypeTable;
import mainClasses.Customer;
import mainClasses.Event;
import mainClasses.JSON_Converter;
import mainClasses.Ticket;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Booking extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        HttpSession session = request.getSession();
        EditBookingsTable ebt = new EditBookingsTable();
        EditCustomersTable ect = new EditCustomersTable();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        EditEventsTable eet = new EditEventsTable();
        JSONObject bookingJson = new JSONObject();
        JSON_Converter j = new JSON_Converter();

        String data = j.getJSONFromAjax(reader);
        String[] part = data.split("/");

        try{
            Customer c = ect.databaseToCustomers(session.getAttribute("loggedIn").toString());
            int customer_id = c.getId();

            Ticket t = ett.databaseToTicket(session.getAttribute("ticket_selected").toString());
            int ticket_id = t.getId();

            Event e = eet.databaseToEvents(session.getAttribute("event_selected").toString());
            int event_id = e.getId();
            String event_date = e.getDate();

            bookingJson.put("customer_id" , customer_id);
            bookingJson.put("event_id" , event_id);
            bookingJson.put("ticket_id" , ticket_id);
            bookingJson.put("date" , event_date);
            bookingJson.put("cost" , Integer.parseInt(part[0]));
            bookingJson.put("quantity" , Integer.parseInt(part[1]));

            ebt.addBookingFromJSON(bookingJson.toString());
            response.setStatus(200);
        }catch(ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditBookingsTable ebt = new EditBookingsTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String event_id = j.getJSONFromAjax(reader);

        try{
            if(event_id.equals("cancel")){
                mainClasses.Booking b = ebt.databaseToBooking(session.getAttribute("booking_selected").toString());
                ebt.deleteBooking(String.valueOf(b.getId()));
            }else{
                ebt.deleteBookings(event_id);
            }
            response.setStatus(200);
        }catch(ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }
}