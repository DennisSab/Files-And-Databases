package servlets;

import database.tables.EditAdminTable;
import mainClasses.Admin;
import mainClasses.JSON_Converter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userType") == "admin"){
            response.setStatus(200);
            response.getWriter().write("admin");
        }else{
            response.setStatus(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        JSON_Converter j = new JSON_Converter();
        String admin = j.getJSONFromAjax(reader);

        EditAdminTable eat = new EditAdminTable();
        Admin ad = eat.jsonToAdmin(admin);

        String username = ad.getUsername();
        String password = ad.getPassword();

        try{
            ad = eat.databaseToAdmins(username , password);
            if(ad != null){
                HttpSession session = request.getSession();
                if(request.getServletContext().getAttribute("activeUsers") == null){
                    session.setAttribute("loggedIn" , username);
                    session.setAttribute("userType" , "admin");
                    request.getServletContext().setAttribute("activeUsers" , 1);
                    response.setStatus(200);
                }
            }else if(eat.databaseToAdmins(username) != null){
                response.setStatus(403);
            }else{
                response.setStatus(404);
            }
        }catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(LoginCustomer.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}