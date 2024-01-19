package EntityMatcherProject.SimilarityFunctions;

import EntityMatcherProject.TableEntry;

public class OccurenceRule extends SimilarityFunction {
    String name = "OcurrenceRule";

    @Override
    public boolean match(TableEntry entry1, TableEntry entry2) {
        double occAvg1 = entry1.getOccurenceAvg();
        double occAvg2 = entry2.getOccurenceAvg();

        double max = Math.max(occAvg1, occAvg2);
        double min = Math.min(occAvg1, occAvg2);

        double value = Math.abs(max - min) / max;

        //System.out.println("Word1: " + entry1.getWord() + " Word2: " + entry2.getWord());
        //System.out.println("Max: " + max + " Min: " + min + " Value: " + value);
        if(value < 0.5) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
