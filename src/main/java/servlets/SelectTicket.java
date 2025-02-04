package servlets;

import database.tables.EditTicketsTypeTable;
import mainClasses.JSON_Converter;
import mainClasses.Ticket;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class SelectTicket extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        try{
            Ticket t = ett.databaseToTicket(session.getAttribute("ticket_selected").toString());
            response.getWriter().write(String.valueOf(t.getAvailability()));
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();

        String ticket_type = j.getJSONFromAjax(reader);

        try{
            Ticket t = ett.databaseToTicket(session.getAttribute("event_selected").toString() , ticket_type);
            int ticket_id = t.getId();
            session.setAttribute("ticket_selected" , ticket_id);
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request , HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("ticket_selected" , "");
        response.setStatus(200);
    }
}
