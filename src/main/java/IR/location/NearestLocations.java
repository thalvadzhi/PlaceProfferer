package IR.location;

import IR.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class NearestLocations {

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static Place findNearestCity(List<Place> places, double lat, double lon){
        Place closest = places.get(0);

        double closestDist = distance(Double.parseDouble(closest.getLocation()[0]), Double.parseDouble(closest.getLocation()[1]), lat, lon, "K");
        for(Place place : places){
            if(!"Bulgaria ".equals(place.getName())){
                double newDist = distance(Double.parseDouble(place.getLocation()[0]), Double.parseDouble(place.getLocation()[1]), lat, lon, "K");
                if (newDist < closestDist){
                    closest = place;
                    closestDist = newDist;
                }
            }
        }
        return closest;
    }

    public static List<Place> findCitiesWithinRadius(List<Place> places, double lat, double lon, double maxDistance){
        List<Place> placesWithinRadius = new ArrayList<>();
        for(Place place : places){
            if(!"Bulgaria ".equals(place.getName())){
                double placeDist = distance(Double.parseDouble(place.getLocation()[0]), Double.parseDouble(place.getLocation()[1]), lat, lon, "K");
                if(placeDist <= maxDistance){
                    placesWithinRadius.add(place);
                }
            }
        }
        return placesWithinRadius;

    }

}
