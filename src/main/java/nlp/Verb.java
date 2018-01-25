package nlp;

/**
 * Created by thalvadzhiev on 1/21/18.
 */
public class Verb {
    public String value;

    public Verb(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Verb: " + this.value;
    }

    @Override
    public boolean equals(Object o) {
        Verb verb = (Verb) o;

        return value.equals(verb.value) ;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
