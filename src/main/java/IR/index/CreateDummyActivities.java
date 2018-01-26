package IR.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thalvadzhiev on 1/26/18.
 */
public class CreateDummyActivities {
    public static HashMap<String, List<String>> create(){
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> ls = new ArrayList<>();
        ls.addAll(Arrays.asList("photograph", "enjoy", "relax", "sightseeing"));
        map.put("Sights", ls);

        List<String> ls1 = new ArrayList<>();

        ls1.addAll(Arrays.asList("photograph", "enjoy", "sightseeing", "walking"));

        map.put("Architectural Buildings", ls1);

        List<String> ls2 = new ArrayList<>();
        ls2.addAll(Arrays.asList("photograph", "pray", "light", "worship"));

        map.put("Sacred", ls2);


        List<String> ls3 = new ArrayList<>();
        ls3.addAll(Arrays.asList("photograph", "enjoy", "criticize", "shopping"));

        map.put("Art Galleries", ls3);

        map.put("Churches", ls2);

        List<String> ls4 = new ArrayList<>();
        ls4.addAll(Arrays.asList("photograph", "enjoy", "admire", "shopping", "storytelling"));

        map.put("Monuments", ls4);

        List<String> ls5 = new ArrayList<>();
        ls5.addAll(Arrays.asList("skiing", "walking", "snowboarding", "hiking", "climbing", "enjoying"));

        map.put("Snowboard Areas", ls5);

        List<String> ls11 = new ArrayList<>();
        ls11.addAll(Arrays.asList("shopping","spending","walking","watching","seeing"));
        map.put("Shopping Malls", ls11);

        List<String> ls10 = new ArrayList<>();
        ls10.addAll(Arrays.asList("watching","seeing","enjoy", "sightseeing", "photograph"));
        map.put("Landmarks", ls10);

        List<String> ls9 = new ArrayList<>();
        ls9.addAll(Arrays.asList("watching","seeing","enjoy","photograph"));
        map.put("Museums", ls9);

        List<String> ls8 = new ArrayList<>();
        ls8.addAll(Arrays.asList("watching","seeing","enjoy", "sightseeing", "photograph", "relax", "running", "hiking", "claiming"));
        map.put("Nature", ls8);

        List<String> ls7 = new ArrayList<>();
        ls7.addAll(Arrays.asList("eating","enjoy", "relax", "photograph", "walking", "running", "playing"));
        map.put("Parks", ls7);
        return map;
    }
}
