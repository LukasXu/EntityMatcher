package EntityMatcherProject.SimilarityFunctions;
import EntityMatcherProject.TableEntry;

public abstract class SimilarityFunction {
    private int id;
    private static int counter = 0;

    public abstract boolean match(TableEntry entry1, TableEntry entry2);

    public abstract String getName();

    public SimilarityFunction() {
        this.id = counter;
        counter++;
    }

    public int getId() {
        return this.id;
    }

}
