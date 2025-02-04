package servlets;

import database.tables.EditEventsTable;
import mainClasses.JSON_Converter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Event extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        String name = request.getParameter("name");
        EditEventsTable eet = new EditEventsTable();

        try{
            mainClasses.Event ev = eet.databaseToEventsByName(name);
            response.setStatus(200);
            response.getWriter().write(String.valueOf(ev.getId()));
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            JSON_Converter conv = new JSON_Converter();

            String jsonString = conv.getJSONFromAjax(reader);
            EditEventsTable eet = new EditEventsTable();

            eet.addEventFromJSON(jsonString);
            response.setStatus(200);
        }catch(ClassNotFoundException e){
            Logger.getLogger(LoginCustomer.class.getName()).log(Level.SEVERE , null , e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String event = j.getJSONFromAjax(reader);

        try{
            EditEventsTable eet = new EditEventsTable();
            eet.deleteEvent(event);
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}