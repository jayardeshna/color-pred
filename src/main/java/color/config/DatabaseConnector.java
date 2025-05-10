package color.config;

import java.sql.*;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/color_predictor";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            // Register MySQL JDBC driver (if needed)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Connection details
        String url = "jdbc:mysql://localhost:3306/color_predictor";
        String user = "root"; // Your MySQL username
        String password = "root"; // Your MySQL password

        // Establish and return the connection
        return DriverManager.getConnection(url, user, password);
    }
}
