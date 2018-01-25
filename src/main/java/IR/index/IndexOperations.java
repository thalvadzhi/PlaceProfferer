package IR.index;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by thalvadzhiev on 1/25/18.
 */
public class IndexOperations {

    public static List<Posting> operationAnd(List<Posting> first, List<Posting> second){
        HashSet<Posting> firstSet = new HashSet<>(first);
        HashSet<Posting> secondSet = new HashSet<>(second);

        firstSet.retainAll(secondSet);

        ArrayList<Posting> intersection = new ArrayList<>(firstSet);
        Collections.sort(intersection, (o1, o2)-> (int)(o2.rating*10 - o1.rating*10));
        return intersection;
    }

    public static List<Posting> operationOr(List<Posting> first, List<Posting> second){
        HashSet<Posting> firstSet = new HashSet<>(first);
        HashSet<Posting> secondSet = new HashSet<>(second);

        firstSet.addAll(secondSet);

        ArrayList<Posting> union = new ArrayList<>(firstSet);
        Collections.sort(union, (o1, o2)-> (int)(o2.rating*10 - o1.rating*10));
        return union;
    }

}
