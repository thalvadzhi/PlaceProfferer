package nlp;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Context context = (Context) o;

        if (value != null ? !value.equals(context.value) : context.value != null) return false;
        return compounds != null ? compounds.equals(context.compounds) : context.compounds == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (compounds != null ? compounds.hashCode() : 0);
        return result;
    }
}
