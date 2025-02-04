package servlets;

import database.tables.EditEventsTable;
import mainClasses.Event;
import mainClasses.JSON_Converter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class SelectEvent extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditEventsTable eet = new EditEventsTable();
        try{
            Event ev = eet.databaseToEvents(session.getAttribute("event_selected").toString());
            int event_id = ev.getId();

            response.getWriter().write(event_id);
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String event_id = j.getJSONFromAjax(reader);
        session.setAttribute("event_selected" , event_id);
        response.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest request , HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("event_selected" , "");
        response.setStatus(200);
    }
}
