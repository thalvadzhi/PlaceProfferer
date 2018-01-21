package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.util.Pair;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constants.Constants.RELATION_DIRECT_OBJECT;
import static constants.Constants.RELATION_COMPOUND;
import static constants.Constants.POS_CAT_ADJECTIVE;
import static constants.Constants.PATTERN_VERB;


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
                if(RELATION_DIRECT_OBJECT.equals(verbChild.first().getShortName())){
                    //find all direct objects that are governed by the verb
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
}
