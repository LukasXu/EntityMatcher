package EntityMatcherProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import EntityMatcherProject.Rules.DumbRule;
import EntityMatcherProject.Rules.Rule;
import EntityMatcherProject.Rules.SaxRule;
import EntityMatcherProject.Rules.WordRule;

public class EntityMatcher {
    private Map<Integer, Rule> ruleMap = new HashMap<>(); //Mappt regel id auf Regel
    private List<RulePair> sortedRuleSet;
    public static List<Pair> ruleSet; //Konjunktion von Lamda

    static List<TableEntry> exampleTable = Arrays.asList(
        new TableEntry("dass", "abbbabba", 1991, 2001, 0),
        new TableEntry("daß", "abbbabba", 1991, 2001, 1),
        new TableEntry("dann", "cbbbcbbc", 1991, 2001, 2),
        new TableEntry("dasss", "abbbabba", 1991, 2001, 3),
        new TableEntry("den", "cbbbcbbc", 1991, 2001, 4)
        );

    static List<Pair> posEx = Arrays.asList(
        new Pair(0, 1),
        new Pair(0, 3),
        new Pair(3, 1)
        );

    static List<Pair> negEx = Arrays.asList(
        new Pair(0, 2),
        new Pair(0, 4),
        new Pair(1, 2),
        new Pair(1, 4),
        new Pair(2, 3),
        new Pair(2, 4),
        new Pair(3, 4)
        );


    public EntityMatcher() {
        initRules();
        if(ruleSet == null) {            
            ruleSet = generateRuleSet();
            this.sortedRuleSet = getExampleResult();
        }   
    }

    private void initRules() {
    	ruleMap.put(0, new WordRule());
        ruleMap.put(1, new SaxRule());
        ruleMap.put(2, new DumbRule());
    }

    //Wendet rule auf table an.
    //Einträge die Matchen werden zusammengefasst
    public void filter(List<TableEntry> table, Pair rule) {
        List<TableEntry> dummy = new ArrayList<>();
        for(int i = 0; i < table.size(); i++) {
             for (int j = i + 1; j < table.size(); j++) {
                boolean b = checkRule(table.get(i), table.get(j), rule);
                if(b) {
                    dummy.add(table.get(i));
                    dummy.add(table.get(j));
                    table.remove(table.get(i));
                    table.remove(table.get(j));
                }
            }
        }
        System.out.println("Dummy: " + dummy);
        if(dummy.get(0) == null) {
            table.add(dummy.get(0));
        }
    }

    private boolean checkRule(TableEntry entry1, TableEntry entry2, Pair rule) {
        Rule r1 = ruleMap.get(rule.getFirst()); 
        Rule r2 = ruleMap.get(rule.getSecond()); 
        boolean b1 = r1.match(entry1, entry2);  
        boolean b2 = r2.match(entry1, entry2); 
        return  b1 && b2;
    }

    
    private List<RulePair> getExampleResult() {
        List<RulePair> resultList = new ArrayList<>();
       
        for(int i = 0; i < ruleSet.size(); i++) {
            Pair pair = ruleSet.get(i);
            double percentage = matchEntries(pair, exampleTable);
            resultList.add(new RulePair(pair, percentage));
        }
        Collections.sort(resultList, new RulePairComparitor());
        return resultList;
    }

    //Funktioniert nur für Size 2 regeln
    private double matchEntries(Pair rule, List<TableEntry> table) {
        List<Pair> pos = new ArrayList<>();
        List<Pair> neg = new ArrayList<>();
        Rule r1 = ruleMap.get(rule.getFirst()); 
        Rule r2 = ruleMap.get(rule.getSecond()); 

        for(int i = 0; i < table.size(); i++) {
             for (int j = i + 1; j < table.size(); j++) {
                boolean b1 = r1.match(table.get(i), table.get(j));  
                boolean b2 = r2.match(table.get(i), table.get(j));     
                Pair entryPair = new Pair(table.get(i).getId(), table.get(j).getId());          
                if(b1 && b2) {
                    pos.add(entryPair);
                } else {
                    neg.add(entryPair);
                }
            }
        }
         
      /*   //Prints content of pos
        System.out.println("Pos:");
        for(Pair p : pos) {
            System.out.println(p);
        }  */

       /*  //Prints content of Neg
        System.out.println("Neg:");
        for(Pair p : neg) {
            System.out.println(p);
        }  */


        int m1 = matchPairLists(pos, posEx);
        int m2 = matchPairLists(neg, negEx);
/* 
        System.out.println("M1: " + m1);
        System.out.println("M2: " + m2); */

        int denom = posEx.size() + negEx.size();
        double result = (double) (m1 + m2) / denom;

        //System.out.println("Result: " + result + "\n");
        return result;
    }

    

    //list1 ausgerechnet, list2 musterlösung
    private int matchPairLists(List<Pair> list1, List<Pair> list2) {
        int counter = 0;

        for(int i = 0; i < list1.size(); i++) {
            for(int j = 0; j < list2.size(); j++) {
                if(list1.get(i).isSame(list2.get(j))) {
                    counter++;
                    continue;
                }
            }      
        }
        return counter;
    }

    //Generates rule of size 2
    // Ex: lambda1 ^ lambda2
    private List<Pair> generateRuleSet() {
        List<Pair> ruleSet = new ArrayList<>();
        for(int i = 0; i < ruleMap.size(); i++) {
            for (int j = i + 1; j < ruleMap.size(); j++) {
                ruleSet.add(new Pair(i, j));
                //System.out.println("Pair " + "(" + i + "," + j + ")");
            }
        }
        return ruleSet;
    }

    public List<RulePair> getSortedRuleSet() {
        return sortedRuleSet;
    }
    
}
