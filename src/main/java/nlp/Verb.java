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
}
