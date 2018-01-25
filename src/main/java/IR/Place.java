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
public class Place {
    private String name;
    private String[] location;
    private ArrayList<Landmark> landmarks;

    public Place() {
    }

    public Place(String name, String[] location) {
        this.name = name;
        this.location = new String[location.length];
        for (int i = 0; i < location.length; i++) {
            this.location[i] = new String(location[i]);
        }
        landmarks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String[] getLocation() {
        return location;
    }
    
    public Place(String name, String[] location, ArrayList<Landmark> landmarks) {
        this.name = name;
        this.location = new String[location.length];
        for (int i = 0; i < location.length; i++) {
            this.location[i] = new String(location[i]);
        }
        this.landmarks = landmarks;
    }
    
    public ArrayList<Landmark> getLandmarks() {
        return landmarks;
    }
    
    public void addLandmark(Landmark lmark) {
        landmarks.add(lmark);
    }
    
    
}
