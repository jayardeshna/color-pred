package color;

public class Preference {

    private int customerId;
    private int[] rgb;  // RGB color as an array
    private int rating;

    public Preference(int customerId, int[] rgb, int rating) {
        this.customerId = customerId;
        this.rgb = rgb;
        this.rating = rating;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
