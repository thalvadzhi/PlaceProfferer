package IR.index;

import IR.Landmark;
import nlp.Context;
import nlp.Verb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.stanford.nlp.util.Pair;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class ActivityPlace {
    int id;
    String name;
    String[] location;
    Rating rating;
    List<String> category;
    String city;
    String country;
    List<Pair<Verb, Context>> activities;

    public ActivityPlace(Landmark landmark){
        this.id = landmark.getId();
        this.name = landmark.getName().replace("\n", "").split(" -")[0];
        this.location = landmark.getLocation();
        int numberOfReviews = landmark.getReviews().size() * 10;
        this.rating = new Rating(Double.parseDouble(landmark.getRating().replace("RATING: ", "")), numberOfReviews);
        this.category = Arrays.stream(landmark.getCategory().replace("&amp;","\n").split("\n")).map(String::trim).collect(Collectors.toList());
        this.city = landmark.getCity();
        this.country = landmark.getCountry();


    }

    public void setCity(String city){
        this.city = city;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }



    public void setCountry(String country) {
        this.country = country;
    }

    public void setActivities(List<Pair<Verb, Context>> activities) {
        this.activities = activities;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getLocation() {
        return location;
    }

    public Rating getRating() {
        return rating;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public List<Pair<Verb, Context>> getActivities() {
        return activities;
    }
}
