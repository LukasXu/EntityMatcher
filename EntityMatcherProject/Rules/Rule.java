package EntityMatcherProject.Rules;
import EntityMatcherProject.TableEntry;

public interface Rule {

    public boolean match(TableEntry entry1, TableEntry entry2);

}
