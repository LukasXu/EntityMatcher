package EntityMatcherProject.SimilarityFunctions;

import EntityMatcherProject.TableEntry;

public class DumbFunction extends SimilarityFunction {
    String name = "Dumb Rule";

    @Override
    public boolean match(TableEntry entry1, TableEntry entry2) {
        int minYear1 = entry1.getMinYear();
        int maxYear1 = entry1.getMaxYear();
       
        int minYear2 = entry2.getMinYear();
        int maxYear2 = entry2.getMaxYear();

        //System.out.println("MinYear " + minYear1 + "-" + minYear2 + " MaxYear " + maxYear1 + "-" + maxYear2 + " -> " + (minYear1 == minYear2 && maxYear1 == maxYear2));
        if(minYear1 == minYear2 && maxYear1 == maxYear2) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
