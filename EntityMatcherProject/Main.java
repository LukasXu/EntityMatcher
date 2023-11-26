package EntityMatcherProject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import EntityMatcherProject.Pair.TableEntryPair;
import EntityMatcherProject.Rules.Rule;
import EntityMatcherProject.Rules.RulePercentagePair;

class Main {
    static Parser parser = new Parser();
    static EntityMatcher em = new EntityMatcher();
    
    public static void main(String[] args) {
        boolean token = true;
        Scanner sc = new Scanner(System.in);

        List<RulePercentagePair> bestRuleList = em.getSortedRuleSet();
        System.out.println("Best Rule List:" + bestRuleList + "\n");
        while(token) {
            if(bestRuleList.size() == 0 || EntityMatcher.getPosEx().size() == 0) {
                break;
            }
            
            for(int i = 0 ; i < bestRuleList.size(); i++) {
                System.out.println("Accept Rule " + bestRuleList.get(i).toString() + " ?");
                bestRuleList.get(i).parseExamples();
                String response = sc.next();

                //Falls ja, dann 
                if(response.equals("y")) {
                    System.out.println("//////////////////////////////////////////// \n");
                    Rule bestRule = bestRuleList.get(i).getRule();                    
                    em.addAcceptedRule(bestRule);

                    //Entferne jeden Match der Regel aus posEx
                    List<TableEntryPair> matches = bestRuleList.get(i).getMatches();
                    for(TableEntryPair pair: matches) {
                        em.removePair(pair);
                    }

                    em.filterRuleSet(bestRule); //Entfernt Beste Regel aus möglichen Rulesets
                    bestRuleList = em.recalculateBestRule();  //Neuste beste Regel

                    System.out.println("New Pos Ex: " + EntityMatcher.getPosEx());
                    System.out.println("Neue Beste Regeln: " + bestRuleList + "\n");
                    i = -1;

                } else if(response.equals("n")) {
                    continue;
                } else {
                    System.out.println("Incorrect Input");
                    break;
                }

                if(bestRuleList.size() == 0 || EntityMatcher.getPosEx().size() == 0) {
                    break;
                }

                System.out.println("Continue ?");
                String cont = sc.next();
                if(!cont.equals("y")) {
                    break;
                } 
            }
            token = false;
            sc.close();    
        }
        List<Rule> disjunction = em.getDisjunctionRule();
        System.out.println("Disjunktion: " + disjunction);
       /*  HashMap<String, List<TableEntry>> map = Parser.getTableEntryMap();      
        applyOnTable(disjunction, map); 
          */
        //System.out.println("Ergebnis: " + table);
    }

    private static void applyOnTable(List<Rule> disjunction, HashMap<String, List<TableEntry>> map) {

        //Für jeden Präfix Gruppe wird die Disjunktion darauf ausgeführt 
        for(Map.Entry<String, List<TableEntry>> entry: map.entrySet()) {
            String key = entry.getKey();
            List<TableEntry> list = entry.getValue(); 

            if(list.size() <= 1) {
                continue;
            }
            //System.out.println("Key: " + key + "\nList: " + list);
            for(Rule rule : disjunction) {
                em.filter(list, rule);
            }
        }
    }
    
}
