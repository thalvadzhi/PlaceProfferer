package IR.index;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class Posting {
    int placeId;
    double rating;

    @Override
    public String toString() {
        return "Posting{" +
                "placeId=" + placeId +
                ", rating=" + rating +
                '}';
    }

    public Posting(int placeId, double rating) {
        this.placeId = placeId;
        this.rating = rating;
    }
}
