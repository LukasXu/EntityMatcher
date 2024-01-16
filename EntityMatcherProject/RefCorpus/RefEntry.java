package EntityMatcherProject.RefCorpus;

import java.util.ArrayList;
import java.util.List;

import EntityMatcherProject.Library.Util;
import EntityMatcherProject.Pair.InitialPair;

public class RefEntry {
    private int id;
    private String word;
    private List<InitialPair> freqList; // Initial Entry === Jahr , Freq, Mentions

    public RefEntry(int id, String word) {
        this.id = id;
        this.word = word;
        this.freqList = new ArrayList<>();
    }

    public List<InitialPair> getFreqList() {
        return freqList;
    }

    public void addFreqEntry(InitialPair pair) {
        freqList.add(pair);
    }
    
    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

   /*  public String toString() {
        String listString = Util.toStringList(freqList, ", ", " ");     
        String noBracket = listString.substring(1, listString.length() - 1);
        return Integer.toString(this.id) + " " + word + " " + noBracket + "\n";
    } */

    public String toString() {
        String listString = Util.toStringList(freqList, ", ", "\t");
        String noBracket = listString.substring(1, listString.length() - 1);
        String result =  Integer.toString(this.id) + "\t" + word + "\t" + noBracket + "\n";
        return result;
    }
}
