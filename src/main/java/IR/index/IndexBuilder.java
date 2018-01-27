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
    public static HashMap<Integer, ActivityPlace> buildIdIndex(List<ActivityPlace> places){
        HashMap<Integer, ActivityPlace> idIndex = new HashMap<>();

        for(ActivityPlace place : places){
            idIndex.put(place.getId(), place);
        }
        return idIndex;
    }

    public static HashMap<String, List<Posting>> buildCountryIndex(List<ActivityPlace> places){
        HashMap<String, List<Posting>> countryIndex = new HashMap<>();

        for(ActivityPlace place : places) {
            //building the country index
            Posting posting = new Posting(place.getId(), place.getRating());

            if (countryIndex.containsKey(place.getCountry())) {
                if(!countryIndex.get(place.getCountry()).contains(posting)){
                    countryIndex.get(place.getCountry()).add(posting);
                }
            } else {
                ArrayList<Posting> postings = new ArrayList<>();
                postings.add(posting);
                countryIndex.put(place.getCountry(), postings);
            }
        }
        sortPostings(countryIndex);
        return countryIndex;
    }

    public static HashMap<String, List<Posting>> buildCityIndex(List<ActivityPlace> places) {
        HashMap<String, List<Posting>> cityIndex = new HashMap<>();

        for(ActivityPlace place : places) {
            Posting posting = new Posting(place.getId(), place.getRating());

            if (cityIndex.containsKey(place.getCity())) {
                if(!cityIndex.get(place.getCity()).contains(posting)){
                    cityIndex.get(place.getCity()).add(posting);
                }
            } else {
                ArrayList<Posting> postings = new ArrayList<>();
                postings.add(posting);
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
                Posting posting = new Posting(place.getId(), place.getRating());

                if (categoryIndex.containsKey(category)) {
                    if(!categoryIndex.get(category).contains(posting)){
                        categoryIndex.get(category).add(posting);
                    }
                } else {
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(posting);
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
                Posting posting = new Posting(place.getId(), place.getRating());
                if(verbIndex.containsKey(activity.first())){
                    if(!verbIndex.get(activity.first()).contains(posting)){
                        verbIndex.get(activity.first()).add(posting);
                    }
                }else{
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(posting);
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
                Posting posting = new Posting(place.getId(), place.getRating());
                if(contextIndex.containsKey(activity.second())){
                    if(!contextIndex.get(activity.second()).contains(posting)){
                        contextIndex.get(activity.second()).add(posting);
                    }
                }else{
                    ArrayList<Posting> postings = new ArrayList<>();
                    postings.add(posting);
                    contextIndex.put(activity.second(), postings);
                }
            }
        }
        sortPostings(contextIndex);
        return contextIndex;
    }

    private static void sortPostings(HashMap<?, List<Posting>> index){
        for(Object key : index.keySet()){
            Collections.sort(index.get(key), (o1, o2) -> (int)(o2.rating.getRating()*10 - o1.rating.getRating()*10));
        }
    }



}
