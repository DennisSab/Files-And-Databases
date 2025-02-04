package servlets;

import database.init.InitDatabase;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InitDB extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{
            response.setContentType("text/html;charset=UTF-8");
            InitDatabase.main(new String[0]);
        }catch(SQLException ex){
            response.sendError(500);
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE , null , ex);
        }catch(ClassNotFoundException ex){
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE , null , ex);
        }
    }
}
