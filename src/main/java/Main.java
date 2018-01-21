import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;

import java.util.List;

public class Main {
    public static void main(String[] args){

        Sentence sent = new Sentence("Amazing park area to wander round, take a rowing boat out on the lake");
        Sentence sent2 = new Sentence("I even enjoyed walking down the beautiful Kärtnerstrasse admiring many nice, original shops");
        SemanticGraph semanticGraph = sent2.dependencyGraph();
        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(semanticGraph);
        System.out.println(verbContextPairs);
    }
}
