package IR.queryParser;

import IR.Place;
import IR.index.ActivityPlace;
import IR.index.IndexManager;
import IR.index.IndexOperations;
import IR.index.Posting;
import constants.Constants;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;
import nlp.dictionary.SynonymFinder;

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

        current = handleActivities(activities, current);
        if(current != null){
            return current;
        }else{
            return new ArrayList<>();
        }
    }

    private List<Posting> handleActivities(List<String> activities, List<Posting> current){
        List<Posting> output = current;
        List<Posting> temp;
        for(String activity : activities){
            Sentence s = new Sentence(activity);
            List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(s);
            if(verbContextPairs.size() == 0){
                //search for verbs only
                temp = getPostingsForVerbOnly(current, s);
                if(output == null){
                    output = temp;
                }else{
                    output = IndexOperations.operationAnd(output, temp);
                }

            }else{
                //index verb and context index
                temp = getPostingsForVerbContext(current, verbContextPairs);
                if(output == null){
                    output = temp;
                }else{
                    output = IndexOperations.operationAnd(output, temp);
                }
//                current = getPostingsForVerbContext(current, verbContextPairs);
            }
        }
        return output;
    }

    private List<Posting> getPostingsForVerbContext(List<Posting> current, List<Pair<Verb, Context>> verbContextPairs) {
        for(Pair<Verb, Context> pair : verbContextPairs){
            List<Posting> postingsVerb = manager.indexVerb(pair.first());
            List<Posting> postingsContext = manager.indexContext(pair.second());
            List<Posting> temp = new ArrayList<>();
            if(postingsVerb.size() == 0){
                temp = handleSynonyms(pair, postingsContext, temp);
            }else{
                temp = IndexOperations.operationAnd(postingsContext, postingsVerb);
            }
            if(current == null){
                current = temp;
            }else{
//                List<Posting> temp = IndexOperations.operationAnd(postingsContext, postingsVerb);
                current = IndexOperations.operationAnd(current, temp);
            }
        }
        return current;
    }

    private List<Posting> handleSynonyms(Pair<Verb, Context> pair, List<Posting> postingsContext, List<Posting> temp) {
        List<Posting> postingsVerb;
        SynonymFinder finder = new SynonymFinder();
        List<String> verbSynonyms = finder.findSynonymsOf("V", pair.first().value);
        for(String synonym : verbSynonyms){
            postingsVerb = manager.indexVerb(new Verb(synonym));
            if(postingsVerb.size() != 0){
                //means we've got a hit with the synonym
                temp = IndexOperations.operationAnd(postingsContext, postingsVerb);
                if(temp.size() != 0){
                    return temp;
                }else{
                    //no match between verb and context
                    //try to find context synonym
                    List<String> contextSynonyms = finder.findSynonymsOf("N", pair.second().value);
                    for(String ctxSyn : contextSynonyms){
                        postingsContext = manager.indexContext(new Context(ctxSyn));
                        temp = IndexOperations.operationAnd(postingsContext, postingsVerb);
                        if(temp.size() != 0){
                            return temp;
                        }
                    }
                }
            }
        }
        return temp;
    }

    private List<Posting> getPostingsForVerbOnly(List<Posting> current, Sentence s) {
        for(int i = 0; i < s.posTags().size(); i++){
            if(s.posTags().get(i).matches("V.*")){
                //means its a verb
                Verb verb = new Verb(s.lemma(i));
                List<Posting> temp = manager.indexVerb(verb);
                if(temp.size() == 0){
                    temp = getVerbSynonymPostings(verb, temp);
                }
                if(current == null){
                    current = temp;
                }else{
                    List<Posting> intersect = IndexOperations.operationAnd(current, temp);
                    if(intersect.size() == 0){
                        temp = getVerbSynonymPostings(verb, temp);
                    }
                    current = IndexOperations.operationAnd(current, temp);
                }
            }
        }
        return current;
    }

    private List<Posting> getVerbSynonymPostings(Verb verb, List<Posting> temp) {
        SynonymFinder finder = new SynonymFinder();
        List<String> synonyms = finder.findSynonymsOf("V", verb.value);
        for(String synonym : synonyms){
            List<Posting> synVerb = manager.indexVerb(new Verb(synonym));
            if(synVerb.size() != 0){
                temp = synVerb;
                return temp;
            }
        }
        return temp;
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
