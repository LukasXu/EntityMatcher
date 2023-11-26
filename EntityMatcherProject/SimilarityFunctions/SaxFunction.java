package EntityMatcherProject.SimilarityFunctions;

import EntityMatcherProject.TableEntry;

public class SaxFunction extends SimilarityFunction{
    String name = "Sax Rule";
    
    //TODO Approximierung fehlt
    public boolean match(TableEntry entry1, TableEntry entry2) {
        String sax1 = entry1.getSax();
        String sax2 = entry2.getSax();

        String inverted = "";
        for(int i = 0; i < sax1.length(); i++) {
            if(sax1.charAt(i) == 'a') {
                inverted += 'c';
            } else if (sax1.charAt(i) == 'c') {
                inverted += 'a';
            } else {
                inverted += 'b';
            }
        }

        //System.out.println("OW:" + sax1 + " Inv: " + inverted + " SW: " + sax2 + " -> " + inverted.equals(sax2));
        if(inverted.equals(sax2)) {
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
