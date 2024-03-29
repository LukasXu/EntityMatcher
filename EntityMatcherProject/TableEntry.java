package EntityMatcherProject;

import java.util.List;

import EntityMatcherProject.Pair.InitialPair;

public class TableEntry {
    private String word;
    private String sax;
    private int minYear;
    private int maxYear;
    private int id;
    private double occurenceAvg;
    private static int counter = 0;
    private List<InitialPair> pairList; // time series entry list

    public TableEntry(InitialEntry entry) {
        this.word = entry.getWord();
        this.sax = entry.getSaxString();
        this.minYear = entry.getMinFreqYear();
        this.maxYear = entry.getMaxFreqYear();
        this.id = counter;
        this.occurenceAvg = entry.getOccurenceAvg();
        this.pairList = entry.getPairList();
        counter++;
    }

    public TableEntry(String name, String sax, int minYear, int maxYear, int id) {
        this.word = name;
        this.sax = sax;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.id = id;
    }

    public String toString() {
        return "(" + word + ", " + sax + ", " + minYear + ", " + maxYear + ", " + id + ")";
    }

    public String getWord() {
        return word;
    }

    public int getMaxYear() {
        return maxYear;
    }
    public int getMinYear() {
        return minYear;
    }

    public String getSax() {
        return sax;
    }

    public int getId() {
        return id;
    }

    public double getOccurenceAvg() {
        return occurenceAvg;
    }

    public List<InitialPair> getPairList() {
        return pairList;
    }

    public void setPairList(List<InitialPair> pairList) {
        this.pairList = pairList;
    }
}