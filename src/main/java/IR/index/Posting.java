package IR.index;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class Posting {
    int placeId;
//    double rating;
    Rating rating;

    @Override
    public String toString() {
        return "Posting{" +
                "placeId=" + placeId +
                ", rating=" + rating.toString() +
                '}';
    }

    public Posting(int placeId, Rating rating) {
        this.placeId = placeId;
        this.rating = rating;
    }

    public int getPlaceId() {
        return placeId;
    }

    public Rating getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posting posting = (Posting) o;

        return placeId == posting.placeId;
    }

    @Override
    public int hashCode() {
        return placeId;
    }
}
