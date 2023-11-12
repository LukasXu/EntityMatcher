package EntityMatcherProject;


public class RulePair {
    private Pair pair;
    private double percentage;
    

    public RulePair(Pair pair, double percentage) {
        this.pair = pair;
        this.percentage = percentage;
    }

    public Pair getPair() {
        return pair;
    }

    public double getPercentage() {
        return percentage;
    }

    public String toString() {
        return "("+ pair.toString() + ", " + percentage + ")"; 
    }
}
