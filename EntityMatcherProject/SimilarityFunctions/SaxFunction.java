package EntityMatcherProject.SimilarityFunctions;

import EntityMatcherProject.SaxTable;
import EntityMatcherProject.TableEntry;

public class SaxFunction extends SimilarityFunction{
    String name = "Sax Rule";
    
    //TODO Approximierung fehlt
    public boolean match(TableEntry entry1, TableEntry entry2) {
        String sax1 = entry1.getSax();
        String sax2 = entry2.getSax();

        double compressionRate  = (double) (entry1.getMaxYear() - entry1.getMinYear()) / sax1.length() ;
        double sum = 0;

        for(int i = 0; i < sax1.length(); i++) {
            char c1 = sax1.charAt(i);
            char c2 = sax2.charAt(i);
            double distance = SaxTable.getDistance(c1, c2);
            sum += Math.pow(distance, 2);
        }
        //System.out.println("Sax1: " + sax1 + " Sax2: " + sax2 + " Sum:" + sum);
        double result = Math.sqrt(compressionRate) * Math.sqrt(sum);
        

        if(result > 0.5) {
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
