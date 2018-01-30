import IR.Converter;
import IR.Place;
import IR.index.*;
import IR.location.NearestLocations;
import IR.queryParser.ParseQueries;
import IR.queryParser.QueryParser;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;
import edu.stanford.nlp.util.Index;
import edu.stanford.nlp.util.Pair;
import javafx.geometry.Pos;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;
import nlp.dictionary.SynonymFinder;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.IndexableBinaryStringTools;
import org.apache.lucene.util.Version;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
////
//        Sentence sent = new Sentence("Amazing park area to wander round, take a rowing boat out on the lake");
//        sent.lemmas();

//        Sentence sent3 = new Sentence("Unless you want to play in the snow, ski or toboggan you might find yourself at a bit if a lose end.");
//        Sentence sent2 = new Sentence("I even enjoyed walking down the beautiful Kartnerstrasse admiring many nice, original shops");
//        Sentence sent4 = new Sentence("You can enjoy a great view, hike or just walk for a few hours and recharge your batteries ");
//        SemanticGraph semanticGraph = sent4.dependencyGraph();
//        semanticGraph.prettyPrint();
//        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(semanticGraph);
//        System.out.println(verbContextPairs);
//
//
//        File file = new File("Bulgaria.txt");
//        BufferedReader reader = new BufferedReader(new FileReader(file));
////
//        String json = reader.readLine();
//        ArrayList<Place> places = Converter.deserialization(json);
//        List<ActivityPlace> transform = DataTransformation.transform(places);
//        HashMap<Integer, List<String>> reviews = DataTransformation.normalizeReviews(places);
//
//        HashMap<Integer, List<Sentence>> allSentences = VerbContextExtractor.getAllSentences(reviews);
//
//        HashMap<String, List<Pair<Verb, Context>>> vcs = new HashMap<>();
//        for(Integer i : allSentences.keySet()){
//            vcs.addAll(VerbContextExtractor.getActivityTest(allSentences.get(i)));
//        }
//        vcs.putAll(VerbContextExtractor.getActivityTest(allSentences.get(5)));
//        Sentence s = new Sentence("Awful place to make a picnic");
//        System.out.println(s.sentiment());
//        vcs.forEach(System.out::println);
        SynonymFinder finder = new SynonymFinder();
        List<String> synonymsOf = finder.findSynonymsOf("NN", "car");
        synonymsOf.forEach(System.out::println);
        System.out.println("gyz");

//        Sentence ss = new Sentence("Nice park to visit in Sofia");
//        ss.sentiment();
//        SemanticGraph semanticGraph = ss.dependencyGraph();
//        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(semanticGraph);
//        System.out.println(verbContextPairs);
//        semanticGraph.prettyPrint();


    }
}