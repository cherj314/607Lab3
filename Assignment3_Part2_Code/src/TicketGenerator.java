import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Date;

public class TicketGenerator {

	
	public static void main(String[] args) {
        // Define the database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/Assignment3";
        String username = "root";
        String password = "password";

        // Number of tickets to generate
        int numTickets = 10000;

        // Time window for tickets
        Date startDate = java.sql.Date.valueOf("2023-01-01");
        Date endDate = java.sql.Date.valueOf("2023-06-30");

        // Initialize a MySQL database connection
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Generate and insert tickets
            for (int i = 0; i < numTickets; i++) {
                // Generate random data for each ticket
                String caseId = "CS_" + (i + 1);
                String activity = generateRandomActivity();
                String urgency = generateRandomUrgency();
                String impact = generateRandomImpact();
                String priority = generateRandomPriority(urgency, impact);
                Date ticketStartDate = generateRandomDate(startDate, endDate);
                Date ticketEndDate = generateRandomDate(ticketStartDate, endDate);
                String ticketStatus = generateRandomStatus();
                Date updateDateTime = new Date();
                int duration = calculateDuration(ticketStartDate, ticketEndDate);
                String origin = generateRandomOrigin();
                String ticketClass = generateRandomClass();

                // Insert the generated ticket into the EventLog table
                String insertQuery = "INSERT INTO EventLog (Caseid, Activity, Urgency, Impact, Priority, StartDate, EndDate, TicketStatus, UpdateDateTime, Duration, Origin, Class) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, caseId);
                    preparedStatement.setString(2, activity);
                    preparedStatement.setString(3, urgency);
                    preparedStatement.setString(4, impact);
                    preparedStatement.setString(5, priority);
                    preparedStatement.setDate(6, new java.sql.Date(ticketStartDate.getTime()));
                    preparedStatement.setDate(7, new java.sql.Date(ticketEndDate.getTime()));
                    preparedStatement.setString(8, ticketStatus);
                    preparedStatement.setTimestamp(9, new java.sql.Timestamp(updateDateTime.getTime()));
                    preparedStatement.setInt(10, duration);
                    preparedStatement.setString(11, origin);
                    preparedStatement.setString(12, ticketClass);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println(numTickets + " tickets generated and inserted into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper methods to generate random data
    private static String generateRandomActivity() {
        String[] activities = { "Design", "Construction", "Test", "Password Reset" };
        return activities[new Random().nextInt(activities.length)];
    }

    private static String generateRandomUrgency() {
        return String.valueOf(new Random().nextInt(3) + 1);
    }

    private static String generateRandomImpact() {
        return String.valueOf(new Random().nextInt(3) + 1);
    }

    private static String generateRandomPriority(String urgency, String impact) {
        int urgencyValue = Integer.parseInt(urgency);
        int impactValue = Integer.parseInt(impact);
        int priorityValue = urgencyValue * impactValue;
        return String.valueOf(priorityValue);
    }

    private static Date generateRandomDate(Date startDate, Date endDate) {
        long randomTime = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
        return new Date(randomTime);
    }

    private static String generateRandomStatus() {
        String[] statuses = { "Open", "On Hold", "In Process", "Deployed", "Deployed Failed" };
        return statuses[new Random().nextInt(statuses.length)];
    }

    private static int calculateDuration(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    private static String generateRandomOrigin() {
        String[] origins = { "Joe S.", "Bill B.", "George E.", "Achmed M.", "Rona E." };
        return origins[new Random().nextInt(origins.length)];
    }

    private static String generateRandomClass() {
        String[] classes = { "Change", "Incident", "Problem", "SR for Service Request" };
        String randomClass = classes[new Random().nextInt(classes.length)];

        // Remove spaces from the class name
        randomClass = randomClass.replaceAll(" ", "");

        return randomClass;
    }

}

