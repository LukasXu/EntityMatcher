package EntityMatcherProject.Rules;

public class RulePercentagePair {
    private Rule rule;
    private double percentage;
    

    public RulePercentagePair(Rule rule, double percentage) {
        this.rule = rule;
        this.percentage = percentage;
    }

    public Rule getRule() {
        return rule;
    }

    public double getPercentage() {
        return percentage;
    }

    public String toString() {
        return "("+ rule.toString() + ", " + percentage + ")"; 
    }
}
