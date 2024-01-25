package EntityMatcherProject;

import java.util.ArrayList;
import java.util.List;

public class EvaluationTable {
    List<EvaluationEntry> table;

    public EvaluationTable() {
        table = new ArrayList<>();
    }

    public EvaluationTable(List<EvaluationEntry> table) {
        this.table = table;
    }

    public void addEntry(EvaluationEntry entry) {
        table.add(entry);
    }

    public String toString() {
        return table.toString();
    }

    public int getSize() {
        return table.size();
    }
}
