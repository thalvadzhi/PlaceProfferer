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
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {


    }
}