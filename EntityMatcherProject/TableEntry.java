package EntityMatcherProject;
public class TableEntry {
    private String word;
    private String sax;
    private int minYear;
    private int maxYear;
    private int id;
    private static int counter = 0;

    public TableEntry(InitialEntry entry) {
        this.word = entry.getWord();
        this.sax = entry.getSaxString();
        this.minYear = entry.getMinFreqYear();
        this.maxYear = entry.getMaxFreqYear();
        this.id = counter;
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
}