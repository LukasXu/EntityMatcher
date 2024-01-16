package EntityMatcherProject.RefCorpus;

import java.util.ArrayList;
import java.util.List;

import EntityMatcherProject.Library.Util;

public class RefTable {
    private List<RefEntry> refEntries = new ArrayList<>();

    public List<RefEntry> getRefEntries() {
        return refEntries;
    }

    public String toString() {
        String dummy = Util.toStringList(refEntries, ", ", "");    
        String noBracket = dummy.substring(1, dummy.length() - 1);
        return noBracket;
    }
}
