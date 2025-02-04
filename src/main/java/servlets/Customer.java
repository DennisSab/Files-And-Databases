package servlets;

import database.tables.EditCustomersTable;
import mainClasses.JSON_Converter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Customer extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        EditCustomersTable ect = new EditCustomersTable();
        try{
            mainClasses.Customer c = ect.databaseToCustomers(session.getAttribute("loggedIn").toString());
            String json = ect.CustomerToJSON(c);
            response.setStatus(200);
            response.getWriter().write(json);
        }catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();
        String customer = j.getJSONFromAjax(reader);

        EditCustomersTable ect = new EditCustomersTable();

        try{
            ect.addCustomerFromJSON(customer);
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        response.setStatus(200);
    }
}

