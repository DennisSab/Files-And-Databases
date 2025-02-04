package servlets;

import database.tables.EditBookingsTable;
import mainClasses.Booking;
import mainClasses.JSON_Converter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class SelectBooking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditBookingsTable ebt = new EditBookingsTable();
        try{
            Booking b = ebt.databaseToBooking(session.getAttribute("booking_selected").toString());
            int booking_id = b.getId();

            response.getWriter().write(booking_id);
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditBookingsTable ebt = new EditBookingsTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String booking_id = j.getJSONFromAjax(reader);

        try{
            Booking b = ebt.databaseToBooking(booking_id);
            session.setAttribute("booking_selected" , b.getId());
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request , HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("booking_selected" , "");
        response.setStatus(200);
    }
}
