package color;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Sample new color to predict
            int[] newColor = {160, 32, 240}; // Example: a shade of red

            // Fetch all preferences from the database for prediction
            List<Preference> allPreferences = new ArrayList<>();
            allPreferences.addAll(DatabaseConnector.getPreferences(1)); // Preferences for Customer 1
            allPreferences.addAll(DatabaseConnector.getPreferences(2)); // Preferences for Customer 2
            allPreferences.addAll(DatabaseConnector.getPreferences(3)); // Preferences for Customer 3

            // Use ColorPrediction to predict which customers will like the new color
            List<Customer> predictedCustomers = ColorPrediction.predictCustomersForNewColor(newColor, allPreferences);

            // Output prediction results
            if (predictedCustomers != null && !predictedCustomers.isEmpty()) {
                System.out.println("The new RGB color " + java.util.Arrays.toString(newColor) + " will likely be liked by:");
                for (Customer customer : predictedCustomers) {
                    System.out.println("Customer " + customer.getName());
                }
            } else {
                System.out.println("No customers predicted to like this color.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}