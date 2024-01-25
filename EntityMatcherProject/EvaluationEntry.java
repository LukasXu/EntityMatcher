package EntityMatcherProject;

public class EvaluationEntry {
    String word;
    double originalMetric;
    double filterdMetric;

    public EvaluationEntry(String word, double orignalMetric, double filteredMetric) {
        this.word = word;
        this.originalMetric = orignalMetric;
        this.filterdMetric = filteredMetric;
    }

    public String toString() {
        return "Word: " + word + " orig.Metric: " + originalMetric + " filt.Metric: " + filterdMetric;
    }
}
