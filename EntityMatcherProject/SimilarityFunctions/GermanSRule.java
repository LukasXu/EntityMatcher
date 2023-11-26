package EntityMatcherProject.SimilarityFunctions;

import EntityMatcherProject.TableEntry;

public class GermanSRule extends SimilarityFunction{
    private String name = "GermanSRule";

    @Override
    public boolean match(TableEntry entry1, TableEntry entry2) {
        String w1 = entry1.getWord();
        String w2 = entry2.getWord();
        

        String var1 = w1.replace("ss", "ß"); 
        String var2 = w1.replace("ß", "ss");
        
        if(var1.equals(w2) || var2.equals(w2)) {
            //System.out.println("Entry1:" + entry1.toString() + "Entry2: " + entry2.toString() + "\n");
            //System.out.println("Word1" + w1 + "->" + var1 + "=" + w2);
            //System.out.println("Word1" + w1 + "->" + var2 + "=" + w2);
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
