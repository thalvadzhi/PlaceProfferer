package IR.queryParser;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class ParseQueries {
    public static String parseQuery(String queryString) throws ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        BooleanQuery q = (BooleanQuery) new QueryParser("activity", analyzer).parse(queryString);
        return parse(q);
    }

    public static String parse(Query q){
        //TODO replace string with posting lists
        if (q instanceof TermQuery){
            return q.toString();
            //return posting for TermQuery(walking, eating, etc)
        }
        BooleanQuery query = (BooleanQuery) q;
        String result = "";
        for(int i = 0; i < query.clauses().size(); i++){
            BooleanClause clause = query.clauses().get(i);

            if (clause.getOccur().compareTo(BooleanClause.Occur.MUST_NOT) == 0){
                result += "NOT(" + parse(clause.getQuery()) + ")";
            }else{
                result += "(" + parse(clause.getQuery()) + ")";
            }

            if (i != query.clauses().size() - 1){
                if(clause.getOccur().compareTo(BooleanClause.Occur.MUST) == 0){
                    result += " AND ";
                }else if (clause.getOccur().compareTo(BooleanClause.Occur.SHOULD) == 0){
                    result += " OR ";
                }else if (clause.getOccur().compareTo(BooleanClause.Occur.MUST_NOT) == 0){
                    result += " NOT ";
                }
            }
        }
        return result;

    }
}
