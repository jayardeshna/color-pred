package color.service;

import color.model.ColorRating;
import color.model.Customer;
import color.model.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ColorPredictionImpl {
    /**
     * Predicts which customers will like a new color based on their historical preferences
     *
     * @param newColor    RGB values of the new color
     * @param preferences List of customer preferences
     * @return List of customers predicted to like the new color
     */

    public static List<Customer> predictCustomersForNewColor(int[] newColor, int rating, List<Preference> preferences) {
        // Store unique customers to avoid duplicates
        Map<Integer, Customer> uniqueCustomers = new HashMap<>();

        double baseThreshold;
        if (rating == 5) baseThreshold = 75.0;
        else if (rating == 4) baseThreshold = 50.0 * 1.10;
        else if (rating == 3) baseThreshold = 37.5 * 1.30;
        else if (rating == 2) baseThreshold = 37.5 * 1.60;
        else if (rating == 1) baseThreshold = 37.5 * 2.00;
        else return new ArrayList<>();

        // Process each preference
        for (Preference pref : preferences) {
            int prefRating = pref.getRating();
            if (prefRating < 3) continue;

            // Calculate color distance
            double distance = calculateColorDistance(newColor, pref.getRgb());

            // If within threshold, add customer to results
            if (distance <= baseThreshold && !uniqueCustomers.containsKey(pref.getCustomerId())) {
                Customer customer = new Customer(pref.getCustomerId(), "Customer " + pref.getCustomerId());
                uniqueCustomers.put(pref.getCustomerId(), customer);
            }
        }

        // Return list of unique customers
        return new ArrayList<>(uniqueCustomers.values());
    }

    /**
     * Calculates the Euclidean distance between two RGB colors
     * According to FR3: Distance(RGB1,RGB2) = √[(R1-R2)² + (G1-G2)² + (B1-B2)²]
     *
     * @param color1 First RGB color array [R,G,B]
     * @param color2 Second RGB color array [R,G,B]
     * @return Euclidean distance between the colors
     */
    public static double calculateColorDistance(int[] color1, int[] color2) {
        if (color1.length != 3 || color2.length != 3) {
            throw new IllegalArgumentException("RGB colors must have exactly 3 components");
        }

        int rDiff = color1[0] - color2[0];
        int gDiff = color1[1] - color2[1];
        int bDiff = color1[2] - color2[2];

        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

}
