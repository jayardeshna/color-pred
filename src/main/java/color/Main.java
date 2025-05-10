package color;

import color.config.DatabaseConnector;
import color.model.ColorRating;
import color.model.Customer;
import color.model.Preference;
import color.model.TempColorLike;
import color.repository.CustomRepository;
import color.service.ColorPredictionImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            List<ColorRating> newShirtColors = Arrays.asList(
                    new ColorRating(new int[]{0, 128, 255}, 3),
                    new ColorRating(new int[]{150, 75, 0}, 4),
                    new ColorRating(new int[]{210, 180, 140}, 2),
                    new ColorRating(new int[]{255, 127, 80}, 4),
                    new ColorRating(new int[]{48, 213, 200}, 3)
            );


            List<Integer> allCustomerIds = CustomRepository.getAllCustomerIds();

            for (ColorRating colorRating: newShirtColors) {

                List<Integer> matchedCustomerIds = CustomRepository.getTempMatchedCustomerIds();

                List<Preference> allPreferences = new ArrayList<>();

                // Only load preferences for unmatched customers
                for (int customerId : allCustomerIds) {
                    if (!matchedCustomerIds.contains(customerId)) {
                        allPreferences.addAll(CustomRepository.getPreferences(customerId));
                    }
                }

                // Predict customers for this color
                List<Customer> predictedCustomers = ColorPredictionImpl.predictCustomersForNewColor(
                        colorRating.getRgb(),
                        colorRating.getRating(),
                        allPreferences
                );

                for (Customer customer : predictedCustomers) {
                    CustomRepository.saveTempColorMatch(customer.getId(), colorRating);
                }

                if (!predictedCustomers.isEmpty()) {
                    System.out.println("Color " + Arrays.toString(colorRating.getRgb()) + " liked by:");
                    for (Customer customer : predictedCustomers) {
                        System.out.println("Customer " + customer.getId());
                    }
                } else {
                    System.out.println("No customers liked color " + Arrays.toString(colorRating.getRgb()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}