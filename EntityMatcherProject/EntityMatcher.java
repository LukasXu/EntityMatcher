package EntityMatcherProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.TabExpander;

import EntityMatcherProject.Pair.TableEntryPair;
import EntityMatcherProject.Rules.Rule;
import EntityMatcherProject.Rules.RulePairComparitor;
import EntityMatcherProject.Rules.RulePercentagePair;
import EntityMatcherProject.SimilarityFunctions.GermanSRule;
import EntityMatcherProject.SimilarityFunctions.OccurenceRule;
import EntityMatcherProject.SimilarityFunctions.SaxFunction;
import EntityMatcherProject.SimilarityFunctions.SimilarityFunction;
import EntityMatcherProject.SimilarityFunctions.WordEditDistance;

public class EntityMatcher {
    private Map<Integer, SimilarityFunction> ruleMap = new HashMap<>(); //Mappt regel id auf Regel
    private List<RulePercentagePair> sortedRuleSet; //Best Rule List
    public static List<Rule> ruleSet; //Konjunktion von Lamda
    private List<Rule> disjunctionRule = new ArrayList<>(); //Disjuntion von Regeln
    static List<TableEntry> exampleTable = new ArrayList<>();
    static List<TableEntryPair> posEx = new ArrayList<>();
    static List<TableEntryPair> negEx = new ArrayList<>();


    public EntityMatcher() {
        initExampleLists();
        initPosExampleList();
        initNegExampleList();
        initRules();
        //System.out.println("Example Table: " + exampleTable);

        if(ruleSet == null) {
            List<Rule> rules = generateRuleSetSize1();
            rules.addAll(generateRuleSetSize2());  
            rules.addAll(generateRuleSetSize3());            
            ruleSet = rules;
            System.out.println("Ruleset: " + ruleSet + "\n");

            this.sortedRuleSet = getExampleResult();
            //System.out.println("Best Rule List: " + sortedRuleSet);
        }   
    }

    private void initExampleLists() {
        exampleTable.add(Parser.findEntry("dass"));
        exampleTable.add(Parser.findEntry("daß"));
        exampleTable.add(Parser.findEntry("niedrig"));
        exampleTable.add(Parser.findEntry("niedriger"));
        exampleTable.add(Parser.findEntry("dann"));
        exampleTable.add(Parser.findEntry("denn"));
        exampleTable.add(Parser.findEntry("den"));
    }

    private void initPosExampleList() {
        posEx.add(new TableEntryPair(Parser.findEntry("dass"), Parser.findEntry("daß")));
        posEx.add(new TableEntryPair(Parser.findEntry("niedrig"), Parser.findEntry("niedriger")));
    }

    private void initNegExampleList() {
        for(int i = 0; i < exampleTable.size(); i++) {
            for(int j = i + 1; j < exampleTable.size(); j++) {
                TableEntryPair p = new TableEntryPair(
                    Parser.findEntry(exampleTable.get(i).getWord()),
                    Parser.findEntry(exampleTable.get(j).getWord())
                    );
                if(posEx.contains(p) == false) {
                    negEx.add(p);
                }
            }
        }
        System.out.println("NegEx: " + negEx);
    }

    

    private void initRules() {
    	ruleMap.put(0, new WordEditDistance());
        ruleMap.put(1, new SaxFunction());
        ruleMap.put(2, new GermanSRule());
        ruleMap.put(3, new OccurenceRule());
    }

    //Wendet rule auf table an.
    //Einträge die Matchen werden zusammengefasst
    public void filter(List<TableEntry> table, Rule rule) {
        System.out.println("Zusammenfassen von: " + table);
        List<TableEntry> dummy = new ArrayList<>();

        //Schaue ob jedes Paar matched und falls es matched, schreibe es in dummy und entferne es aus der Table
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
        }
        System.out.println("Gefilterte Liste: " + table + "\n");
    }

    //Errechnet anhand des Beispiels die beste Regel aus
    private List<RulePercentagePair> getExampleResult() {
        List<RulePercentagePair> resultList = new ArrayList<>();
       
        for(int i = 0; i < ruleSet.size(); i++) {
            Rule pair = ruleSet.get(i); //Konjunktion von allen möglichen Regeln
            List<TableEntryPair> placeholder = new ArrayList<>();
            double percentage = matchEntries(pair, exampleTable, placeholder);

            //System.out.println("Regel: " + pair.toString());
            //System.out.println("Placholder: " + placeholder);

            resultList.add(new RulePercentagePair(pair, percentage, placeholder));
        }
        Collections.sort(resultList, new RulePairComparitor());
        return resultList;
    }

    //Gibt die Erfolgsrate einer Regel angewendet an einer Tabelle an
    private double matchEntries(Rule rule, List<TableEntry> table, List<TableEntryPair> placeholder) {
        int matchCounter = 0;
        //int cartesianProductSize = posEx.size() + negEx.size();

        //System.out.println("Rule: " + rule.toString());

        for(int i = 0; i < table.size(); i++) {
             for (int j = i + 1; j < table.size(); j++) {    

                if(table.get(i)== null || table.get(j) == null) {
                    continue;
                }
                
                //System.out.println("Entry1:" + table.get(i) + "Entry2: " + table.get(j) + " Regel: " + rule.toString());
        
                boolean match = rule.match(table.get(i), table.get(j));    
                TableEntryPair entryPair = new TableEntryPair(table.get(i), table.get(j));    
                //cartesianProductSize++;

                if((match && posEx.contains(entryPair)) || (match == false && negEx.contains(entryPair) == true)) {
                    //System.out.println("Entry: " + entryPair.toString() + " - Contains " + posEx.contains(entryPair) + " - Match: " + match);

                    if(match && posEx.contains(entryPair)) {
                        placeholder.add(entryPair);
                    } 
                    matchCounter++;
                }
            }
        }
        int denom = posEx.size() + negEx.size();
        double result = (double) (matchCounter) / denom;

        //System.out.println("Result: " + matchCounter + "/" + denom + " = " + result + "\n");
        return result;
    }

    ////////// Setter/Getter /////////////////////////////////////////////////////////////////////////////

    public void removePair(TableEntryPair pair) {
        //System.out.println("Contains: " + posEx.contains(pair));
        
        posEx.remove(pair);
    }

    public void addAcceptedRule(Rule rule) {
        this.disjunctionRule.add(rule);
    }

    public List<Rule> getDisjunctionRule() {
        return disjunctionRule;
    }

    public static List<TableEntryPair> getPosEx() {
        return posEx;
    }

    public List<RulePercentagePair> recalculateBestRule() {
        List<RulePercentagePair> list = getExampleResult();
        return list;
    }

    public List<RulePercentagePair> getSortedRuleSet() {
        return sortedRuleSet;
    }

    //////////////////////////////////////////////////////////////////////////

    public void filterRuleSet(Rule rule) {
        List<Rule> toRemove = new ArrayList<>();
        
        for(int i = 0; i < ruleSet.size(); i++) {
            if(ruleSet.get(i).hasRule(rule)) {
                toRemove.add(ruleSet.get(i));
            }
        } 
        //Entferne jede Regel von toRemove aus RuleSet
        for(Rule r: toRemove) {
            ruleSet.remove(r);
        }

        //System.out.println("Filtered Rules Set: " + ruleSet + "\n");
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

    
}
