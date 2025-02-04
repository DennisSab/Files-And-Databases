🎟 Ticketάκη – Concert Ticket Booking System
📌 Project Overview
Ticketάκη is a database-driven application designed to manage ticket reservations for concert venues. Built using Java, JDBC/ODBC, and MySQL/MariaDB, the system allows users to browse events, purchase tickets, and manage reservations through a graphical interface (Java Swing / Java Servlets).

This project was developed as part of the HY-360: Files and Databases course during the Winter Semester 2023.

🚀 Features
Event Management: Create, update, and delete events with details such as name, date, time, type, and venue capacity.
User Management: Register and manage customer accounts, including personal details and payment information.
Ticket Booking: Purchase tickets based on event availability and seat categories (VIP, General Admission, etc.).
Payment Processing: Secure payments via credit card.
Reservation Management: View, modify, and cancel ticket reservations with refund policies.
Event Analytics: Track revenue, most popular events, and seat availability reports.
🛠️ Tech Stack
Programming Language: Java
Database: MariaDB / MySQL
Technologies Used: JDBC / ODBC, Java Swing, Java Servlets
📥 Installation & Setup
Clone the repository

bash
Αντιγραφή
git clone https://github.com/your-username/ticketaki.git
cd ticketaki
Database Setup

Install MySQL/MariaDB
Create a database and import the provided SQL schema
Configure the JDBC connection in the application
Run the Application

If using Java Swing:
bash
Αντιγραφή
javac Ticketaki.java
java Ticketaki
If using Java Servlets:
Deploy the project on a Tomcat server and access it via a web browser.
📊 Database Structure
Events: Stores event details (name, date, type, capacity).
Customers: Stores user information and payment details.
Tickets: Tracks ticket type, price, and availability.
Reservations: Manages bookings and payments.
📈 Queries & Reports
The system supports:
✅ Available and booked seats per event
✅ Revenue tracking per event
✅ Most popular events based on reservations
✅ Sales breakdown by ticket type

👥 Team
This project was developed by a team of three students:

[Your Name]
[Colleague 1]
[Colleague 2]
📜 License
This project is for educational purposes and not intended for commercial use.
