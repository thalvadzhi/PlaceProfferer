/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.util.ArrayList;

/**
 *
 * @author ILIYAN
 */
public class Landmark {
    private int id = 0;
    private String name;
    private String[] location;
    private String rating;
    private ArrayList<String> reviews;
    private String category;
    private String city;
    private String country;

    public Landmark() {
    }

    public Landmark(String name, String[] location, String rating, int id,
                    String country, String city, String category) {
        this.name = new String(name);
        this.location = new String[location.length];
        for (int i = 0; i < location.length; i++) {
            //this.location[i] = new String(location[i]);
            this.location[i] = location[i];
        }
        this.rating = rating;
        reviews = new ArrayList<>();
        this.id = id;
        this.country = country;
        this.city = city;
        this.category = category;
    }

    public void addReviews(String review) {
        this.reviews.add(review);
    }

    public ArrayList<String> getReviews() {
        return reviews;
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

    public String getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
