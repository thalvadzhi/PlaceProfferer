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
    private String name;
    private String[] location;
    private String rating;
    private ArrayList<String> reviews;

    public Landmark() {
    }

    public Landmark(String name, String[] location, String rating) {
        this.name = new String(name);
        this.location = new String[location.length];
        for (int i = 0; i < location.length; i++) {
            this.location[i] = new String(location[i]);
        }
        this.rating = rating;
        reviews = new ArrayList<>();
    }

    public void addReviews(String review) {
        this.reviews.add(review);
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }
    
    
}
