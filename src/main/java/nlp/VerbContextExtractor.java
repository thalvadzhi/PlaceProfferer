package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.Pair;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.*;


/**
 * Created by thalvadzhiev on 1/20/18.
 */
public class VerbContextExtractor {

    public static List<Pair<Verb, Context>> getVerbContextPairs(SemanticGraph semanticGraph){

        //TODO handle negatives
        List<Pair<Verb, Context>> vcPairs = new ArrayList<>();

        //find all the verbs in the sentence
        List<IndexedWord> allVerbs = getAllVerbs(semanticGraph);
        for(IndexedWord verb : allVerbs){
            List<Pair<GrammaticalRelation, IndexedWord>> allChildren = getAllChildren(semanticGraph, verb);

            for(Pair<GrammaticalRelation, IndexedWord> verbChild : allChildren){
                if(RELATION_DIRECT_OBJECT.equals(verbChild.first().getShortName())
                        || RELATION_ADJUNCT.equals(verbChild.first().getShortName())){
                    //find all direct objects or adjuncts that are governed by the verb

                    Context ctx = new Context(verbChild.second().value());
                    List<Pair<GrammaticalRelation, IndexedWord>> objectSpecifiers =
                            getAllChildren(semanticGraph, verbChild.second());

                    objectSpecifiers.stream()
                            .filter((Pair<GrammaticalRelation, IndexedWord> p) -> POS_CAT_ADJECTIVE
                                    .equals(p.second().getString(CoreAnnotations.PartOfSpeechAnnotation.class)))
                            .forEach(((Pair<GrammaticalRelation, IndexedWord> p) -> ctx.addCharacteristic(p.second().value())));
                    //get all adjectives that specify the object

                    objectSpecifiers.stream()
                            .filter((Pair<GrammaticalRelation, IndexedWord> p) -> RELATION_COMPOUND
                                    .equals(p.first().getShortName()))
                            .forEach(((Pair<GrammaticalRelation, IndexedWord> p) -> ctx.addCompound(p.second().value())));
                    //also get compound nouns
                    vcPairs.add(new Pair<>(new Verb(verb.value()), ctx));
                }
            }
        }
        return vcPairs;
    }

    /**
     * Wrapper of the function childPairs. Returns empty List instead of null when there are no children.
     * @param semanticGraph
     * @param word
     * @return all children of the given node (can never be null)
     */
    private static List<Pair<GrammaticalRelation, IndexedWord>> getAllChildren(SemanticGraph semanticGraph, IndexedWord word) {
        Optional<List<Pair<GrammaticalRelation, IndexedWord>>> maybeAllChildren = Optional.ofNullable(semanticGraph
                                                                                                        .childPairs(word));
        return maybeAllChildren.orElse(new ArrayList<>());
    }

    /**
     * Return all the verbs from the sentence whose dependency graph is given by {@code semanticGraph}
     * @param semanticGraph the dependency graph
     * @return all the verbs in the sentence (can never be null)
     */
    public static List<IndexedWord> getAllVerbs(SemanticGraph semanticGraph){
        Optional<List<IndexedWord>> maybeAllVerbs = Optional.ofNullable(semanticGraph
                .getAllNodesByPartOfSpeechPattern(PATTERN_VERB));
        //find all the verbs in the sentence
        return maybeAllVerbs.orElse(new ArrayList<>());
    }

    public static HashMap<Integer, List<Sentence>> getAllSentences(HashMap<Integer, List<String>> reviews){
        HashMap<Integer, List<Sentence>> sentences = new HashMap<>();
        for(Integer key : reviews.keySet()){
            List<String> reviewsString = reviews.get(key);
            for (String review : reviewsString){
                Document doc = new Document(review);
//                doc.sentences()
                if(sentences.containsKey(key)){
                    sentences.get(key).addAll(splitSentences(review));
                }else{
                    sentences.put(key, doc.sentences());
                }
            }
        }
        return sentences;
    }

    private  static List<Sentence> splitSentences(String text){
        List<Sentence> collect = null;
        try {
            collect = Arrays.stream(text.split("\\.")).map(Sentence::new).collect(Collectors.toList());
        }catch (Exception e){
            return new ArrayList<>();
        }
        return collect;
    }

    public static List<Pair<Verb, Context>> getActivityTest(List<Sentence> reviews){
        List<Pair<Verb, Context>> lst = new ArrayList<>();
        for(Sentence s : reviews){
            SemanticGraph semanticGraph = s.dependencyGraph();
            List<Pair<Verb, Context>> verbContextPairs = getVerbContextPairs(semanticGraph);

            lst.addAll(verbContextPairs);
        }
        return lst;
    }
}
