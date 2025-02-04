/*
HY-360 Project Team 11
Karolos Vipperman  csd4601
Denis Sabani  csd4739
Manousos Mavroudis csd4682
 */
package database.init;

import database.tables.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static database.DB_Connection.getInitialConnection;

public class InitDatabase{

    public static void main(String[] args) throws SQLException , ClassNotFoundException{
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
        EditAdminTable eut = new EditAdminTable();
        eut.addAdminFromJSON("{\"username\":\"admin\",\"password\":\"admin*\"}");
    }

    public void dropDatabase() throws SQLException , ClassNotFoundException{
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE ticketάκη";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
    }

    public void initDatabase() throws SQLException , ClassNotFoundException{
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE ticketάκη");
        stmt.close();
        conn.close();
    }

    public void initTables() throws SQLException , ClassNotFoundException{
        EditAdminTable editAdmins = new EditAdminTable();
        editAdmins.createAdminTable();

        EditCustomersTable editCustomers = new EditCustomersTable();
        editCustomers.createCustomersTable();

        EditEventsTable editEvents = new EditEventsTable();
        editEvents.createEventsTable();

        EditTicketsTypeTable editTickets = new EditTicketsTypeTable();
        editTickets.createTicketsTable();

        EditBookingsTable editBookings = new EditBookingsTable();
        editBookings.createBookingTable();
    }
}