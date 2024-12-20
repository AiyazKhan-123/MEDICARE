package medicare.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {

    public Connection connection;
    public Statement statement;

    // Constructor to establish the connection
    public conn() {
        try {
            // Establish the connection"jdbc:mysql://localhost:3306/MEDICARE
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MEDICARE", "root", "AiyaKhan@0872");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the connection object
    public Connection getConnection() {
        return connection;  // Return the connection object
    }

    // Optionally, you can create a method to close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Optionally, you can also create a method to get the statement object
    public Statement getStatement() {
        return statement;  // Return the statement object
    }
}
