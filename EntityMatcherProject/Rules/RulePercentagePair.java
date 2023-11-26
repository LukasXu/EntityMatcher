package EntityMatcherProject.Rules;

import java.text.DecimalFormat;
import java.util.List;

import EntityMatcherProject.Pair.TableEntryPair;

public class RulePercentagePair {
    private Rule rule;
    private double percentage;
    private List<TableEntryPair> matches;
    

    public RulePercentagePair(Rule rule, double percentage, List<TableEntryPair> matches) {
        this.rule = rule;
        this.percentage = percentage;
        this.matches = matches;
    }

    public Rule getRule() {
        return rule;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<TableEntryPair> getMatches() {
        return matches;
    }

    public String toString() {
        String roundedPercentage = new DecimalFormat("#.##").format(percentage);
        return "("+ rule.toString() + ", " + roundedPercentage + ", " + "Matches: " + matches.size() + ")"; 
    }

    public void parseMatches() {
        if(matches.size() < 2) {
            return;
        } 
        for(int i = 0; i < 3; i++) {
            System.out.println("Matched " + matches.get(i).toString());
        }
    }

    public void parseExamples() {
        if(matches.size() == 0) {
            return;
        }

        String example = "Matches: \n";
        int end = matches.size() < 2 ? matches.size() : 2;
        for(int i = 0; i < end; i++) {
            example += matches.get(i).getEntry1().toString() + "," + matches.get(i).getEntry2().toString() + "\n";
        }
        System.out.println(example);
    }
}
