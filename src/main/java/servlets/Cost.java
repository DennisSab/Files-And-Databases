package servlets;

import database.tables.EditTicketsTypeTable;
import mainClasses.Ticket;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class Cost extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditTicketsTypeTable ett = new EditTicketsTypeTable();
        int amount = Integer.parseInt(request.getParameter("amount"));

        try{
            Ticket t = ett.databaseToTicket(session.getAttribute("ticket_selected").toString());
            int ticket_price = t.getPrice();
            int cost = amount * ticket_price;
            response.getWriter().write(String.valueOf(cost));
            response.setStatus(200);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}