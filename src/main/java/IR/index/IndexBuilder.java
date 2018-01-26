package IR.index;

import IR.Landmark;
import edu.stanford.nlp.util.Pair;
import javafx.geometry.Pos;
import nlp.Context;
import nlp.Verb;

import java.util.*;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class IndexBuilder {
//    public static HashMap<String, HashMap<String, List<Posting>>> buildIndices(List<ActivityPlace> places){
//        HashMap<String, List<Posting>> countryIndex = new HashMap<>();
//        HashMap<String, List<Posting>> cityIndex = new HashMap<>();
//
//        HashMap<String, List<Posting>> categoryIndex = new HashMap<>();
//
//        for(ActivityPlace place : places){
//            //building the country index
////            if(countryIndex.containsKey(place.getCountry())){
////                countryIndex.get(place.getCountry()).add(new Posting(place.getId(), place.getRating()));
////            }else{
////                ArrayList<Posting> postings = new ArrayList<>();
////                postings.add(new Posting(place.getId(), place.getRating()));
////                countryIndex.put(place.getCountry(), postings);
////            }
//
//            //building the city index
////            if(cityIndex.containsKey(place.getCity())){
////                cityIndex.get(place.getCity()).add(new Posting(place.getId(), place.getRating()));
////            }else{
////                ArrayList<Posting> postings = new ArrayList<>();
////                postings.add(new Posting(place.getId(), place.getRating()));
////                cityIndex.put(place.getCity(), postings);
////            }
//
//            //building the category index
////            if(categoryIndex.containsKey(place.getCategory())){
////                categoryIndex.get(place.getCategory()).add(new Posting(place.getId(), place.getRating()));
////            }else{
////                ArrayList<Posting> postings = new ArrayList<>();
////                postings.add(new Posting(place.getId(), place.getRating()));
////                categoryIndex.put(place.getCategory(), postings);
////            }
//
//        }
//
//    }

    public static HashMap<String, List<Posting>> buildCountryIndex(List<ActivityPlace> places){
        HashMap<String, List<Posting>> countryIndex = new HashMap<>();

        for(ActivityPlace place : places) {
            //building the country index
            if (countryIndex.containsKey(place.getCountry())) {
                countryIndex.get(place.getCountry()).add(new Posting(place.getId(), place.getRating()));
            } else {
                ArrayList<Posting> postings = new ArrayList<>();
                postings.add(new Posting(place.getId(), place.getRating()));
                countryIndex.put(place.getCountry(), postings);
            }
        }
        sortPostings(countryIndex);
        return countryIndex;
    }

    public static HashMap<String, List<Posting>> buildCityIndex(List<ActivityPlace> places) {
        HashMap<String, List<Posting>> cityIndex = new HashMap<>();

        for(ActivityPlace place : places) {

            if (cityIndex.containsKey(place.getCity())) {
                cityIndex.get(place.getCity()).add(new Posting(place.getId(), place.getRating()));
            } else {
                ArrayList<Posting> postings = new ArrayList<>();
                postings.add(new Posting(place.getId(), place.getRating()));
                cityIndex.put(place.getCity(), postings);
            }
        }
        sortPostings(cityIndex);
        return cityIndex;
    }

    public static HashMap<String, List<Posting>> buildCategoryIndex(List<ActivityPlace> places) {
        HashMap<String, List<Posting>> categoryIndex = new HashMap<>();

        for(ActivityPlace place : places) {
            for(String category : place.getCategory()){

                if (categoryIndex.containsKey(category)) {
                    categoryIndex.get(category).add(new Posting(place.getId(), place.getRating()));
                } else {
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(new Posting(place.getId(), place.getRating()));
                    categoryIndex.put(category, postings);
                }
            }
        }
        sortPostings(categoryIndex);
        return categoryIndex;
    }


        public static HashMap<Verb, List<Posting>> buildVerbIndex(List<ActivityPlace> places){
        HashMap<Verb, List<Posting>> verbIndex = new HashMap<>();
        //building the activity index
        for(ActivityPlace place : places){
            for(Pair<Verb,Context> activity: place.getActivities()){
                if(verbIndex.containsKey(activity.first())){
                    verbIndex.get(activity.first()).add(new Posting(place.getId(), place.getRating()));
                }else{
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(new Posting(place.getId(), place.getRating()));
                    verbIndex.put(activity.first(), postings);
                }
            }
        }
        sortPostings(verbIndex);

        return verbIndex;
    }

    public static HashMap<Context, List<Posting>> buildContextIndex(List<ActivityPlace> places){
        HashMap<Context, List<Posting>> contextIndex = new HashMap<>();

        for(ActivityPlace place : places){
            for(Pair<Verb,Context> activity: place.getActivities()){
                if(contextIndex.containsKey(activity.second())){
                    contextIndex.get(activity.second()).add(new Posting(place.getId(), place.getRating()));
                }else{
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(new Posting(place.getId(), place.getRating()));
                    contextIndex.put(activity.second(), postings);
                }
            }
        }
        sortPostings(contextIndex);
        return contextIndex;
    }

    private static void sortPostings(HashMap<?, List<Posting>> index){
        for(Object key : index.keySet()){
            Collections.sort(index.get(key), (o1, o2) -> (int)(o2.rating*10 - o1.rating*10));
        }
    }



}
