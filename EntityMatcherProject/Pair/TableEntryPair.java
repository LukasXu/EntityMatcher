package EntityMatcherProject.Pair;

import EntityMatcherProject.TableEntry;

public class TableEntryPair {
    TableEntry entry1;
    TableEntry entry2;

    public TableEntryPair(TableEntry entry1, TableEntry entry2) {
        this.entry1 = entry1;
        this.entry2 = entry2;
    }

    public boolean isSame(TableEntryPair p) {
        if(p.getEntry1().getId() == this.entry1.getId() && p.getEntry2().getId() == this.entry2.getId()) {
            return true;
        }
        if(p.getEntry1().getId() == this.entry2.getId() && p.getEntry2().getId() == this.entry1.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        TableEntryPair tableEntry = (TableEntryPair) obj;
        return isSame(tableEntry);        
    }

    public String toString() {
        return "TE:(" + entry1.getWord() + ", " + entry2.getWord() + ")";
    }

    public TableEntry getEntry1() {
        return entry1;
    }

    public TableEntry getEntry2() {
        return entry2;
    }
}
