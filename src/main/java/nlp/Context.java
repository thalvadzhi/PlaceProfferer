package nlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thalvadzhiev on 1/21/18.
 */
public class Context {
    public String value; //this is a noun

    public List<String> characteristics; //these are adjectives

    public List<String> compounds; //the other part of the compund noun for e.g. the "rowing" in rowing boat

    public Context(String value) {
        this.value = value;
        this.characteristics = new ArrayList<>();
        this.compounds = new ArrayList<>();
    }

    public void addCharacteristic(String characteristic){
        this.characteristics.add(characteristic);
    }

    public void addCompound(String compound){
        this.compounds.add(compound);
    }

    @Override
    public String toString() {
        return "Context: " + value + "; chars : " + characteristics + "; compounds: " + compounds;
    }
}
