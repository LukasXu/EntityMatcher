package EntityMatcherProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import EntityMatcherProject.Pair.TableEntryPair;
import EntityMatcherProject.Rules.Rule;
import EntityMatcherProject.Rules.RulePairComparitor;
import EntityMatcherProject.Rules.RulePercentagePair;
import EntityMatcherProject.SimilarityFunctions.DumbFunction;
import EntityMatcherProject.SimilarityFunctions.SaxFunction;
import EntityMatcherProject.SimilarityFunctions.SimilarityFunction;
import EntityMatcherProject.SimilarityFunctions.WordEditDistance;

public class EntityMatcher {
    private Map<Integer, SimilarityFunction> ruleMap = new HashMap<>(); //Mappt regel id auf Regel
    private List<RulePercentagePair> sortedRuleSet;
    public static List<Rule> ruleSet; //Konjunktion von Lamda

    static List<TableEntry> exampleTable = Arrays.asList(
        Parser.findEntry("dass"),
        Parser.findEntry("daß"),
        Parser.findEntry("niedrig"),
        Parser.findEntry("dann"),
        Parser.findEntry("denn"),
        Parser.findEntry("den"),
        Parser.findEntry("niedriger")
    );

    static List<TableEntryPair> posEx = Arrays.asList(
        new TableEntryPair(Parser.findEntry("dass"), Parser.findEntry("daß")),
        new TableEntryPair(Parser.findEntry("niedrig"), Parser.findEntry("niedriger"))
    );

    public EntityMatcher() {
        initRules();
        System.out.println("Example Table: " + exampleTable);
        if(ruleSet == null) {
            List<Rule> rules = generateRuleSetSize1();
            rules.addAll(generateRuleSetSize2());  
            rules.addAll(generateRuleSetSize3());            
            ruleSet = rules;
            //System.out.println("Ruleset: " + ruleSet);
            this.sortedRuleSet = getExampleResult();
            //System.out.println("Best Rule List: " + sortedRuleSet);
        }   
    }

    private void initRules() {
    	ruleMap.put(0, new WordEditDistance());
        ruleMap.put(1, new SaxFunction());
        ruleMap.put(2, new DumbFunction());
    }

    //Wendet rule auf table an.
    //Einträge die Matchen werden zusammengefasst
    public void filter(List<TableEntry> table, Rule rule) {
        System.out.println("Zusammenfassen von: " + table);
        List<TableEntry> dummy = new ArrayList<>();
        for(int i = 0; i < table.size(); i++) {
             for (int j = i + 1; j < table.size(); j++) {
                boolean b = rule.match(table.get(i), table.get(j));
                if(b) {
                    dummy.add(table.get(i));
                    dummy.add(table.get(j));
                    table.remove(table.get(i));
                    table.remove(table.get(j - 1));
                }
            }
        }
        System.out.println("Zusammengefasste Elemente: " + dummy);

        //Falls Elemente zusammengefasst werden, schreibe es in table und die Paare in posEx
        if(dummy.size() > 0) {
            table.add(dummy.get(0));
            /* for(int i = 0; i < dummy.size(); i++) {
                for (int j = i + 1; j < table.size(); j++) {
                    posEx.add(new TableEntryPair(dummy.get(i), dummy.get(j)));
                }
            } */
        }
        System.out.println("Gefilterte Liste: " + table + "\n");
        //System.out.println("Neue PosEx " + posEx);
    }

    //Errechnet anhand des Beispiels die beste Regel aus
    private List<RulePercentagePair> getExampleResult() {
        List<RulePercentagePair> resultList = new ArrayList<>();
       
        for(int i = 0; i < ruleSet.size(); i++) {
            Rule pair = ruleSet.get(i);
            double percentage = matchEntries(pair, exampleTable);
            resultList.add(new RulePercentagePair(pair, percentage));
        }
        Collections.sort(resultList, new RulePairComparitor());
        return resultList;
    }

    //Gibt die Erfolgsrate einer Regel angewendet an einer Tabelle an
    private double matchEntries(Rule rule, List<TableEntry> table) {
        int matchCounter = 0;
        int cartesianProductSize = 0;

        //System.out.println("Rule: " + rule.toString());
        for(int i = 0; i < table.size(); i++) {
             for (int j = i + 1; j < table.size(); j++) {    

                if(table.get(i)== null || table.get(j) == null) {
                    continue;
                }
                
                boolean match = rule.match(table.get(i), table.get(j));    
                TableEntryPair entryPair = new TableEntryPair(table.get(i), table.get(j));    
                cartesianProductSize++;

                if((match && posEx.contains(entryPair)) || (match == false && posEx.contains(entryPair) == false)) {
                    //System.out.println("Entry: " + entryPair.toString() + " - Contains " + posEx.contains(entryPair) + " - Match: " + match);
                    matchCounter++;
                }
            }
        }
        int denom = cartesianProductSize;
        double result = (double) (matchCounter) / denom;

        //System.out.println("Result: " + matchCounter + "/" + denom + " = " + result + "\n");
        return result;
    }

    private List<Rule> generateRuleSetSize1() {
        List<Rule> ruleSet = new ArrayList<>();
        for(int i = 0; i < ruleMap.size(); i++) {         
            SimilarityFunction r1 = ruleMap.get(i);          
            List<SimilarityFunction> list = Arrays.asList(r1);
            Rule rule = new Rule(list);
            ruleSet.add(rule);   
        }
        return ruleSet;
    }

    //Generates rule of size 2
    // Ex: lambda1 ^ lambda2
    private List<Rule> generateRuleSetSize2() {
        List<Rule> ruleSet = new ArrayList<>();
        for(int i = 0; i < ruleMap.size(); i++) {
            for (int j = i + 1; j < ruleMap.size(); j++) {
                SimilarityFunction r1 = ruleMap.get(i);
                SimilarityFunction r2 = ruleMap.get(j);
                List<SimilarityFunction> list = Arrays.asList(r1, r2);
                Rule rule = new Rule(list);
                ruleSet.add(rule);
            }
        }
        return ruleSet;
    }

    private List<Rule> generateRuleSetSize3() {
        List<Rule> ruleSet = new ArrayList<>();
        for(int i = 0; i < ruleMap.size(); i++) {
            for (int j = i + 1; j < ruleMap.size(); j++) {
                for (int k = j + 1; k < ruleMap.size(); k++) {
                    SimilarityFunction r1 = ruleMap.get(i);
                    SimilarityFunction r2 = ruleMap.get(j);
                    SimilarityFunction r3 = ruleMap.get(k);
                    List<SimilarityFunction> list = Arrays.asList(r1, r2, r3);
                    Rule rule = new Rule(list);
                    ruleSet.add(rule);
                }
            }
        }
        //System.err.println(ruleSet);
        return ruleSet;
    }

    public List<RulePercentagePair> getSortedRuleSet() {
        return sortedRuleSet;
    }
    
}
