package servlets;

import database.tables.EditEventsTable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EventsTable extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String user_type = request.getParameter("user_type");
        EditEventsTable eet = new EditEventsTable();
        String htmlTable = eet.generateEventsTable(user_type);

        response.setStatus(200);
        response.getWriter().write(htmlTable);
    }
}
