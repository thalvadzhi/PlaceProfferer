package IR.index;

import IR.Converter;
import IR.Place;
import IR.location.NearestLocations;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thalvadzhiev on 1/26/18.
 */
public class IndexManager {
    private List<Place> crawledPlaces;
    private List<ActivityPlace> transformedPlaces;
    private List<ActivityPlace> dummyTransformedPlaces;

    private HashMap<String, List<Posting>> countryIndex;
    private HashMap<String, List<Posting>> cityIndex;
    private HashMap<String, List<Posting>> categoryIndex;
    private HashMap<Verb, List<Posting>> verbIndex;
    private HashMap<Context, List<Posting>> contextIndex;
    private HashMap<Integer, ActivityPlace> idIndex;

    private HashMap<Integer, List<Pair<Verb, Context>>> idToVerbContext;


    public IndexManager(List<Place> crawledPlaces){
        this.crawledPlaces = crawledPlaces;
        this.transformedPlaces = DataTransformation.transform(this.crawledPlaces);

    }


    private IndexManager(String indexPathString){
        File file = new File(indexPathString);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String json = null;
        try {
            json = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.crawledPlaces = Converter.deserialization(json);
        this.transformedPlaces = DataTransformation.transform(this.crawledPlaces);
        String parse_final_js = Converter.readJsonFromFile("parse_final");
        idToVerbContext = Converter.deserializeParserOutput(parse_final_js);
        this.transformedPlaces = DataTransformation.addActivityInfoToPlaces(this.transformedPlaces, idToVerbContext);
    }

    public IndexManager(String indexPathString, boolean dummy){
        this(indexPathString);
        if(dummy){
            HashMap<String, List<String>> categoryToActivities = CreateDummyActivities.create();
            this.dummyTransformedPlaces = DataTransformation.transformDummy(this.crawledPlaces, categoryToActivities);
            this.buildDummyIndices();
        }else{
            this.buildIndices();
        }
    }

    public IndexManager(boolean dummy){
        this("Bulgaria.txt", dummy);
    }

    private void buildIndices(){
        verbIndex = IndexBuilder.buildVerbIndex(this.transformedPlaces);
        contextIndex = IndexBuilder.buildContextIndex(this.transformedPlaces);
        cityIndex = IndexBuilder.buildCityIndex(this.transformedPlaces);
        categoryIndex = IndexBuilder.buildCategoryIndex(this.transformedPlaces);
        countryIndex = IndexBuilder.buildCountryIndex(this.transformedPlaces);
        idIndex = IndexBuilder.buildIdIndex(this.transformedPlaces);
    }

    private void buildDummyIndices(){
        verbIndex = IndexBuilder.buildVerbIndex(this.dummyTransformedPlaces);
        cityIndex = IndexBuilder.buildCityIndex(this.dummyTransformedPlaces);
        categoryIndex = IndexBuilder.buildCategoryIndex(this.dummyTransformedPlaces);
        countryIndex = IndexBuilder.buildCountryIndex(this.dummyTransformedPlaces);
        idIndex = IndexBuilder.buildIdIndex(this.dummyTransformedPlaces);

    }

    public List<String> getAllVerbs(){
        return (new ArrayList<>(verbIndex.keySet()).stream()
                .map((Verb v) -> v.value)).collect(Collectors.toList());
    }

    public List<String> getAllCities(){
        return new ArrayList<>(cityIndex.keySet());
    }

    public List<String> getAllCategories(){
        return new ArrayList<>(categoryIndex.keySet());
    }

    public List<String> getAllCountries(){
        return new ArrayList<>(countryIndex.keySet());
    }

    public List<String> getAllContexts(){
        //TODO implement this
        return new ArrayList<>();
    }

    public List<Posting> indexCountry(String country){
        if(countryIndex.containsKey(country)){
            return countryIndex.get(country);
        }else{
            return new ArrayList<>();
        }
    }

    public List<Posting> indexCategory(String category){
        if(categoryIndex.containsKey(category)){
            return categoryIndex.get(category);
        }else{
            return new ArrayList<>();
        }
    }

    public List<Posting> indexCity(String city){
        if(cityIndex.containsKey(city)){
            return cityIndex.get(city);
        }else{
            return new ArrayList<>();
        }
    }

    public List<Posting> indexVerb(Verb verb){
        if(verbIndex.containsKey(verb)){
            return verbIndex.get(verb);
        }else{
            return new ArrayList<>();
        }
    }

    public List<Posting> indexContext (Context context){
        if(contextIndex.containsKey(context)){
            return contextIndex.get(context);
        }else{
            return new ArrayList<>();
        }
    }

    public Place getNearestCity(String lat, String lon){
        double latD = Double.parseDouble(lat);
        double lonD = Double.parseDouble(lon);
        return NearestLocations.findNearestCity(this.crawledPlaces, latD, lonD);
    }

    public List<Posting> getLandmarkInCity(Place place){
        if(this.cityIndex.containsKey(place.getName())){
            return this.cityIndex.get(place.getName());
        }else{
            return new ArrayList<>();
        }
    }

    public List<Posting> getLandmarkInNearestCity(String lat, String lon){
        Place nearestCity = getNearestCity(lat, lon);
        return getLandmarkInCity(nearestCity);
    }

    public List<Posting> getLandmarksInCitiesWithinRadius(String lat, String lon, String radius){
        double latD = Double.parseDouble(lat);
        double lonD = Double.parseDouble(lon);
        double radiusD = Double.parseDouble(radius);
        List<Place> citiesWithinRadius = NearestLocations.findCitiesWithinRadius(this.crawledPlaces, latD, lonD, radiusD);
        List<List<Posting>> postings = new ArrayList<>();
        for(Place place : citiesWithinRadius){
            postings.add(getLandmarkInCity(place));
        }
        List<Posting> unionOfPostings = IndexOperations.operationOr(postings);
        return unionOfPostings;
    }

    public ActivityPlace getPlaceById(int id){
        if(idIndex.containsKey(id)){
            return idIndex.get(id);
        }else{
            return null;
        }
    }

    public void setRatingById(int id, double rating){
        if(idIndex.containsKey(id)){
            idIndex.get(id).getRating().setRating(rating);
        }
        resortUpdated(id);
    }

    public void updateRatingById(int id, double rating){
        if(rating > 5){
            rating = 5;
        }else if (rating < 0){
            rating = 0;
        }

        if(idIndex.containsKey(id)){
            idIndex.get(id).getRating().updateRatingBy(rating);
        }
        resortUpdated(id);
    }

    private void sortByRating(List<Posting> ls){
        Collections.sort(ls, ((o1, o2) -> (int)(o2.rating.getRating()*10 - o1.rating.getRating()*10)));
    }

    public void resortUpdated(int id){
        if(idIndex.containsKey(id)){
            ActivityPlace activityPlace = idIndex.get(id);
            sortByRating(countryIndex.get(activityPlace.getCountry()));
            sortByRating(cityIndex.get(activityPlace.getCity()));
            for(String cat : activityPlace.getCategory()){
                sortByRating(categoryIndex.get(cat));
            }
            for(Pair<Verb, Context> verbContextPair : activityPlace.getActivities()){
                if(verbContextPair.first() != null){
                    sortByRating(verbIndex.get(verbContextPair.first()));
                }
                //todo implement context resorting
                if(verbContextPair.second() != null){
                    sortByRating(contextIndex.get(verbContextPair.second()));
                }
            }
        }
    }

}
