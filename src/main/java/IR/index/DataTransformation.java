package IR.index;

import IR.Landmark;
import IR.Place;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class DataTransformation {
    public static List<ActivityPlace> transform(List<Place> places){
        ArrayList<ActivityPlace> activityPlaces = new ArrayList<>();
        for(Place place : places){
            for(Landmark landmark : place.getLandmarks()){
                ActivityPlace activityPlace = new ActivityPlace(landmark);
                activityPlaces.add(activityPlace);

            }
        }
        return activityPlaces;
    }

    public static List<ActivityPlace> transformDummy(List<Place> places, HashMap<String, List<String>> mapping){
        ArrayList<ActivityPlace> activityPlaces = new ArrayList<>();
        for(Place place : places){
            for(Landmark landmark : place.getLandmarks()){
                ActivityPlace activityPlace = new ActivityPlace(landmark);
                List<String> all = new ArrayList<>();
                for(String cat : activityPlace.getCategory()){
                    if(mapping.containsKey(cat)){
                        List<String> activities = mapping.get(cat);
                        all.addAll(activities);
                    }
                }

                List<Pair<Verb, Context>> vc = new ArrayList<>();
                for(String act : all){
                    vc.add(new Pair<>(new Verb(act), new Context("")));
                }
                activityPlace.setActivities(vc);
                activityPlaces.add(activityPlace);

            }
        }
        return activityPlaces;
    }


    public static HashMap<Integer, List<String>> normalizeReviews(List<Place> places){
        HashMap<Integer, List<String>> normalizedReviews = new HashMap<>();
        for(Place place : places){
            for(Landmark landmark : place.getLandmarks()){
                for(String review : landmark.getReviews()){
                    List<String> reviewsNormalized = Arrays.stream(review.split("\n"))
                            .map((str) -> str.replaceAll("\\.\\.\\.<.*", "").replace("<span>", "")
                                    .replace("&amp;", "and")
                                    .replaceAll("[^\\x0A\\x0D\\x20-\\x7E]", "")).collect(Collectors.toList());
                    if(normalizedReviews.containsKey(landmark.getId())){
                        normalizedReviews.get(landmark.getId()).addAll(reviewsNormalized);
                    }else{
                        normalizedReviews.put(landmark.getId(), reviewsNormalized);
                    }
                }
            }
        }
        return normalizedReviews;
    }
}
