package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditEventsTable;
import database.tables.EditTicketsTypeTable;
import mainClasses.Booking;
import mainClasses.Event;
import mainClasses.Ticket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Refund extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        EditBookingsTable ebt = new EditBookingsTable();
        EditEventsTable eet = new EditEventsTable();

        try{
            Booking b = ebt.databaseToBooking(session.getAttribute("booking_selected").toString());
            Event e = eet.databaseToEvents(String.valueOf(b.getEventId()));
            String refund = e.getRefund();
            if(Objects.equals(refund , "Yes")){
                int ticket_amount = b.getTicketQuantity();
                Ticket t = ett.databaseToTicket(String.valueOf(b.getTicketId()));
                int price = t.getPrice();
                response.getWriter().write(String.valueOf(ticket_amount * price));
                response.setStatus(200);
            }else{
                response.setStatus(403);
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}