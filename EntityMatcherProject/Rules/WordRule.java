package EntityMatcherProject.Rules;
import EntityMatcherProject.TableEntry;
import EntityMatcherProject.Functions.EditDistance;

public class WordRule implements Rule {
    static private double threshhold = 0.5;

    public boolean match(TableEntry entry1, TableEntry entry2) {
        String word1 = entry1.getWord();
        String word2 = entry2.getWord();

        int editDistance = EditDistance.editDist(word1, word2, word1.length(), word2.length());
        int length = Math.max(word1.length(), word2.length());
        double editSimilarity = 1 - ((double) editDistance / length);
        //System.out.println("EditSim: " + editSimilarity + " >= " + threshhold);
        if (editSimilarity >= threshhold) {
            return true;
        }
        return false;
    }

}
