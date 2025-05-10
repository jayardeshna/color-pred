package color.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TempColorLike {

    private int customerId;
    private int[] rgb;
    private int rating;

}
