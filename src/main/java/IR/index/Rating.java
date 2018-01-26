package IR.index;

/**
 * Created by thalvadzhiev on 1/26/18.
 */
public class Rating {
    private double rating;
    private double originalRating;
    private double averageRating;
    private long numberOfRatings;
    private int ratingFrom;
    private final long DIMINISHING_FACTOR = 4;
//    private long ORIGINAL_RATING_WEIGHT = 100;

    //ratingfrom is how much reviews account for the original rating
    public Rating(double rating, int ratingFrom) {
        this.rating = rating;
        this.averageRating = rating;
        this.originalRating = rating;
        this.numberOfRatings = 0;
        this.ratingFrom = ratingFrom;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void updateRatingBy(double new_rating){
        numberOfRatings += 1;
        //calculating average on the fly
        this.averageRating += (new_rating - this.averageRating) / numberOfRatings;
        double original_weight = ratingFrom / DIMINISHING_FACTOR;
        this.rating = (original_weight * this.originalRating + this.averageRating * numberOfRatings)/(original_weight + numberOfRatings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating1 = (Rating) o;

        return Double.compare(rating1.rating, rating) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(rating);
        return (int) (temp ^ (temp >>> 32));
    }
}
