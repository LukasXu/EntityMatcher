package EntityMatcherProject.Rules;

public class RulePairComparitor implements java.util.Comparator<RulePercentagePair>{

    @Override
    public int compare(RulePercentagePair p1, RulePercentagePair p2) {
        if(p1.getPercentage() < p2.getPercentage()) {
            return 1;
        };
        if(p1.getPercentage() > p2.getPercentage()) {
            return -1;
        };
        return 0;
    }
    
}
