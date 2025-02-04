package servlets;

import database.tables.EditCustomersTable;
import mainClasses.Customer;
import mainClasses.JSON_Converter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCustomer extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        try{
            if(session.getAttribute("userType") == "customer"){
                response.setStatus(200);
                EditCustomersTable ect = new EditCustomersTable();
                Customer c = ect.databaseToCustomers(session.getAttribute("loggedIn").toString());
                response.getWriter().write(c.getFirstname());
            }else{
                response.setStatus(403);
            }
        }catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(LoginCustomer.class.getName()).log(Level.SEVERE , null , ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();
        String customer = j.getJSONFromAjax(reader);

        EditCustomersTable ect = new EditCustomersTable();
        Customer c = ect.jsonToCustomer(customer);

        String username = c.getUsername();
        String password = c.getPassword();

        try{
            c = ect.databaseToCustomers(username , password);
            if(c != null){
                HttpSession session = request.getSession();
                if(request.getServletContext().getAttribute("activeUsers") == null){
                    session.setAttribute("loggedIn" , username);
                    session.setAttribute("userType" , "customer");
                    request.getServletContext().setAttribute("activeUsers" , 1);
                    response.setStatus(200);
                }
            }else if(ect.databaseToCustomers(username) != null){
                response.setStatus(403);
            }else{
                response.setStatus(404);
            }
        }catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(LoginCustomer.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}
