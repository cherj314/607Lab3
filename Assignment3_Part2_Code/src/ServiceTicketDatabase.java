import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceTicketDatabase {

	public static void main(String[] args) {
        try {
            // Database connection properties
            String jdbcUrl = "jdbc:mysql://localhost:3306/Assignment3";
            String username = "root";
            String password = "password";
            
            // Establish a database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create a Statement object
            Statement statement = connection.createStatement();

            // Create the EventActivity table
            String createEventActivityTable = "CREATE TABLE EventActivity ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Activityname VARCHAR(20)"
                    + ")";
            statement.execute(createEventActivityTable);

            // Create the EventOrigin table
            String createEventOriginTable = "CREATE TABLE EventOrigin ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Activityname VARCHAR(20)"
                    + ")";
            statement.execute(createEventOriginTable);

         // Create the EventClass table
            String createEventClassTable = "CREATE TABLE EventClass ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "Class VARCHAR(20)"
                + ")";
            statement.execute(createEventClassTable);

            // Create the EventLog table
            String createEventLogTable = "CREATE TABLE EventLog ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "Caseid VARCHAR(20) UNIQUE, "
                + "Activity VARCHAR(20), "
                + "Urgency VARCHAR(1), "
                + "Impact VARCHAR(1), "
                + "Priority VARCHAR(1), "
                + "StartDate DATE, "
                + "EndDate DATE, "
                + "TicketStatus VARCHAR(20), "
                + "UpdateDateTime DATETIME, "
                + "Duration INT, "
                + "Origin VARCHAR(20), "
                + "Class VARCHAR(20)"
                + ")";
            statement.execute(createEventLogTable);
            
            // Close the statement and connection
            statement.close();
            connection.close();

            System.out.println("Service ticket database setup completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
