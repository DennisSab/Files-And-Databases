package servlets;

import database.init.InitDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDB extends HttpServlet{

    protected void processRequest(HttpServletRequest request , HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteDB</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteDB at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException{
        try{
            response.setContentType("text/html;charset=UTF-8");
            InitDatabase init=new InitDatabase();
            init.dropDatabase();
        }catch(SQLException ex){
            response.sendError(500);
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE , null , ex);
        }catch(ClassNotFoundException ex){
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE , null , ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        processRequest(request , response);
    }
}
