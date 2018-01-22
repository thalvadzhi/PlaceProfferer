import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;

import java.util.List;

public class Main {
    public static void main(String[] args){
//
        Sentence sent = new Sentence("Amazing park area to wander round, take a rowing boat out on the lake");
        Sentence sent3 = new Sentence("Unless you want to play in the snow, ski or toboggan you might find yourself at a bit if a lose end.");
        Sentence sent2 = new Sentence("I even enjoyed walking down the beautiful Kärtnerstrasse admiring many nice, original shops");
        SemanticGraph semanticGraph = sent2.dependencyGraph();
        semanticGraph.prettyPrint();
        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(semanticGraph);
        System.out.println(verbContextPairs);
    }
}
