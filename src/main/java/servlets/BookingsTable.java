package servlets;

import database.tables.EditBookingsTable;
import database.tables.EditCustomersTable;
import mainClasses.Customer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class BookingsTable extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditCustomersTable ect = new EditCustomersTable();
        try{
            Customer c = ect.databaseToCustomers(session.getAttribute("loggedIn").toString());
            EditBookingsTable eet = new EditBookingsTable();
            String htmlTable = eet.generateBookingsTable(c.getId());

            response.setStatus(200);
            response.getWriter().write(htmlTable);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
