package servlets;

import database.tables.EditEventsTable;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

public class Admin extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditEventsTable eet = new EditEventsTable();
        try{
            mainClasses.Event ev = eet.databaseToEvents(session.getAttribute("loggedIn").toString());
            String json = eet.EventToJSON(ev);
            response.setStatus(200);
            response.getWriter().write(json);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}