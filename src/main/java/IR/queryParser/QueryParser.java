package IR.queryParser;

import IR.Place;
import IR.index.ActivityPlace;
import IR.index.IndexManager;
import IR.index.IndexOperations;
import IR.index.Posting;
import nlp.Verb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 * Created by thalvadzhiev on 1/26/18.
 */
public class QueryParser {
    private IndexManager manager;
    private List<String> keysBlacklist;

    public QueryParser(IndexManager manager){
        this.manager = manager;
        keysBlacklist = Arrays.asList(LAT, LON, DISTANCE);

    }

    public List<Posting> parse(HashMap<String, String> nonActivities, List<String> activities){

        String lat = nonActivities.get(LAT);
        String lon = nonActivities.get(LON);
        String distance = nonActivities.get(DISTANCE);
        List<Posting> landmarksInNearestCity = null;

        if(!NONE.equals(lat) && !NONE.equals(lon) && NONE.equals(distance)){
            //means location is set but distance is not
            //so we find the nearest city to the location
            landmarksInNearestCity = this.manager.getLandmarkInNearestCity(lat, lon);
        }else if((!NONE.equals(lat) && !NONE.equals(lon) && !NONE.equals(distance))){
            //means we have location and distance
            //we can have multiple cities in the radius, so we get the postings of all the cities
            landmarksInNearestCity = this.manager.getLandmarksInCitiesWithinRadius(lat, lon, distance);
        }

        //if lat lon and distance are None we are not restricted by the place so landmarksInNearestCity is null


        List<Posting> current = landmarksInNearestCity;
        for(String key : nonActivities.keySet()){
            if(!ALL.equals(nonActivities.get(key)) && !keysBlacklist.contains(key)){
                //if key is within blacklist, then we've already handled it
                if(current == null){
                    current = getPostingFromCorrectIndex(key, nonActivities.get(key));
                }else{
                    List<Posting> temp = getPostingFromCorrectIndex(key, nonActivities.get(key));
                    current = IndexOperations.operationAnd(current, temp);
                }
            }
        }

        //now filter by activity
        for(String activity : activities){
            Verb verb = new Verb(activity);
            if(current == null){
                current = manager.indexVerb(verb);
            }else{
                List<Posting> temp = manager.indexVerb(verb);
                current = IndexOperations.operationAnd(current, temp);
            }
        }
        if(current != null){
            return current;
        }else{
            return new ArrayList<>();
        }
    }

    public List<String> parseGetNames(HashMap<String, String> nonActivities, List<String> activities){
        List<Posting> postings = parse(nonActivities, activities);
        List<String> strings = postings.stream().map((Posting pos) -> manager.getPlaceById(pos.getPlaceId()).getName()).collect(Collectors.toList());
        return strings;
    }

    public List<ActivityPlace> parseGetPlaces(HashMap<String, String> nonActivities, List<String> activities){
        List<Posting> postings = parse(nonActivities, activities);
        List<ActivityPlace> places = postings.stream().map((Posting pos) -> manager.getPlaceById(pos.getPlaceId())).collect(Collectors.toList());
        return places;
    }

    private List<Posting> getPostingFromCorrectIndex(String key, String value){
        switch (key){
            case "Country":
                return manager.indexCountry(value);
            case "City":
                return manager.indexCity(value);
            case "Category":
                return manager.indexCategory(value);
            default:
                return new ArrayList<>();
        }
    }
}
