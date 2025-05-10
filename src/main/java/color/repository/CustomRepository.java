package color.repository;

import color.config.DatabaseConnector;
import color.model.ColorRating;
import color.model.Preference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static color.config.DatabaseConnector.getConnection;

public class CustomRepository {

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


    public static List<Integer> getTempMatchedCustomerIds() {
        List<Integer> customerIds = new ArrayList<>();
        String query = "SELECT DISTINCT customer_id FROM temp_color_matches";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customerIds.add(rs.getInt("customer_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerIds;
    }


    public static void saveTempColorMatch(int customerId, ColorRating colorRatings) {
        String insertSQL = "INSERT INTO temp_color_matches (customer_id, r, g, b, rating) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

            int[] rgb = colorRatings.getRgb();
            stmt.setInt(1, customerId);
            stmt.setInt(2, rgb[0]);
            stmt.setInt(3, rgb[1]);
            stmt.setInt(4, rgb[2]);
            stmt.setInt(5, colorRatings.getRating());
            stmt.addBatch();

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getAllCustomerIds() {
        List<Integer> customerIds = new ArrayList<>();
        String query = "SELECT DISTINCT customer_id FROM customers";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customerIds.add(rs.getInt("customer_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerIds;
    }



}
