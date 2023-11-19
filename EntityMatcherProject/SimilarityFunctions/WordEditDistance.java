package EntityMatcherProject.SimilarityFunctions;
import EntityMatcherProject.TableEntry;
import EntityMatcherProject.Functions.AltEditDistance;
import EntityMatcherProject.Functions.EditDistance;

public class WordEditDistance extends SimilarityFunction {
    String name = "Edit Distance";
    static private double threshhold = 0.5;

    public boolean match(TableEntry entry1, TableEntry entry2) {
        String word1 = entry1.getWord();
        String word2 = entry2.getWord();

        int editDistance = AltEditDistance.editDistance(word1, word2);
        int length = Math.max(word1.length(), word2.length());
        double editSimilarity = 1 - ((double) editDistance / length);
        //System.out.println("EditDistance: " + editDistance);
        //System.out.println("EditSim: " + editSimilarity + " >= " + threshhold);
        if (editSimilarity >= threshhold) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
       return name;
    }

}
