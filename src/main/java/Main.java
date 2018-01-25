import IR.Converter;
import IR.Place;
import IR.index.*;
import IR.queryParser.ParseQueries;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;
import edu.stanford.nlp.util.Index;
import edu.stanford.nlp.util.Pair;
import javafx.geometry.Pos;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.IndexableBinaryStringTools;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
////
//        Sentence sent = new Sentence("Amazing park area to wander round, take a rowing boat out on the lake");
//        sent.lemmas();

//        Sentence sent3 = new Sentence("Unless you want to play in the snow, ski or toboggan you might find yourself at a bit if a lose end.");
//        Sentence sent2 = new Sentence("I even enjoyed walking down the beautiful Kärtnerstrasse admiring many nice, original shops");
//        Sentence sent4 = new Sentence("You can enjoy a great view, hike or just walk for a few hours and recharge your batteries ");
//        SemanticGraph semanticGraph = sent4.dependencyGraph();
//        semanticGraph.prettyPrint();
//        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(semanticGraph);
//        System.out.println(verbContextPairs);
//        StandardAnalyzer analyzer = new StandardAnalyzer();
//        BooleanQuery q = (BooleanQuery) new QueryParser("title", analyzer).parse("(gyz AND myz AND bz) OR (NOT (byz AND (byz OR zyz)))");
//        BooleanQuery q1 = (BooleanQuery) new QueryParser("title", analyzer).parse("(gyz OR myz)");
//        List<BooleanClause> clauses = q.clauses();
//        clauses.get(0).getQuery();
//        System.out.println(q.toString());
////        System.out.println(q1.toString());
//
//        System.out.println(ParseQueries.parseQuery("(gyz AND (myz AND bz)) OR (NOT (byz AND (byz OR zyz))) AND near:Sofia"));
//
//        ActivityPlace a1 = new ActivityPlace();
//        a1.setCity("Sofia");
//        a1.setRating(1);
//        ActivityPlace a2 = new ActivityPlace();
//        a2.setCity("Sofia");
//        a2.setRating(0);
//        ActivityPlace a3 = new ActivityPlace();
//        a3.setCity("Sofia");
//        a3.setRating(4);
//        ActivityPlace a4 = new ActivityPlace();
//        a4.setCity("Sofia");
//        a4.setRating(3);
//
//
//        ArrayList<ActivityPlace> places = new ArrayList<>();
//        places.add(a1);
//        places.add(a2);
//        places.add(a3);
//        places.add(a4);
//
//        HashMap<String, List<Posting>> stringListHashMap = IndexBuilder.buildCityIndex(places);
//
////        System.out.println(stringListHashMap.toString());
//
//        Posting posting1 = new Posting(1, 3.5);
//        Posting posting2 = new Posting(2, 4.5);
//        Posting posting3 = new Posting(3, 4.6);
//        Posting posting4 = new Posting(4, 5.0);
//        Posting posting5 = new Posting(5, 3.8);
//        Posting posting6 = new Posting(6, 4.3);
//        Posting posting7 = new Posting(7, 4.8);
//        Posting posting8 = new Posting(8, 3.9);
//        List<Posting> ls1 = new ArrayList<>();
//        ls1.add(posting1); ls1.add(posting2); ls1.add(posting3); ls1.add(posting6);
//        List<Posting> ls2 = new ArrayList<>();
//        ls2.add(posting1); ls2.add(posting2); ls2.add(posting3); ls2.add(posting5);
//
//
//        for(Posting p : IndexOperations.operationAnd(ls1,ls2))
//        System.out.print(p.toString() + "   ");
//        //IndexOperations.operationOr(ls1,ls2).toString();
        File file = new File("Bulgaria.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String json = reader.readLine();
        ArrayList<Place> places = Converter.deserialization(json);
        List<ActivityPlace> transform = DataTransformation.transform(places);
        HashMap<Integer, List<String>> integerListHashMap = DataTransformation.normalizeReviews(places);
        System.out.println(transform.toString());

    }
}
