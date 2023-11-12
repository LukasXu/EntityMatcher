package EntityMatcherProject;

public class RulePairComparitor implements java.util.Comparator<RulePair>{

    @Override
    public int compare(RulePair p1, RulePair p2) {
        if(p1.getPercentage() < p2.getPercentage()) {
            return -1;
        };
        if(p1.getPercentage() > p2.getPercentage()) {
            return 1;
        };
        return 0;
    }
    
}
