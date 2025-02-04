package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditTicketsTypeTable;
import mainClasses.Booking;
import mainClasses.JSON_Converter;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Objects;

public class Ticket extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();
        String ticket = j.getJSONFromAjax(reader);
        EditTicketsTypeTable ett = new EditTicketsTypeTable();

        try{
            ett.addTicketFromJSON(ticket);
            response.setStatus(200);
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        EditBookingsTable ebt = new EditBookingsTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String data = j.getJSONFromAjax(reader);
        String[] part = data.split("/");

        try{
            if(Objects.equals(part[0] , "book")){
                mainClasses.Ticket t = ett.databaseToTicket(session.getAttribute("ticket_selected").toString());
                int ticket_id = t.getId();
                ett.bookTicket(String.valueOf(ticket_id) , Integer.parseInt(part[1]));
            }else{
                Booking b = ebt.databaseToBooking(session.getAttribute("booking_selected").toString());
                ett.returnTicket(String.valueOf(b.getTicketId()) , b.getTicketQuantity());
            }
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request , HttpServletResponse response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();
        String event_id = j.getJSONFromAjax(reader);
        EditTicketsTypeTable ett = new EditTicketsTypeTable();

        try{
            ett.deleteTickets(event_id);
            response.setStatus(200);
        }catch(ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }
}