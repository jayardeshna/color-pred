package color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Preference> getPreferences(int customerId) throws SQLException {
        List<Preference> preferences = new ArrayList<>();

        String query = "SELECT r, g, b, rating FROM preferences WHERE customer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int[] rgb = new int[] {
                            rs.getInt("r"),
                            rs.getInt("g"),
                            rs.getInt("b")
                    };
                    int rating = rs.getInt("rating");
                    preferences.add(new Preference(customerId, rgb, rating));
                }
            }
        }

        return preferences;
    }


}
