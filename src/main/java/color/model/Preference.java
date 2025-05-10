package color.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {

    private int customerId;
    private int[] rgb;  // RGB color as an array
    private int rating;

}
