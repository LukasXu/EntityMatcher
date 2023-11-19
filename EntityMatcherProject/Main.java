package EntityMatcherProject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import EntityMatcherProject.Rules.Rule;
import EntityMatcherProject.Rules.RulePercentagePair;

class Main {
    static Parser parser = new Parser();
    
    
    public static void main(String[] args) {
        EntityMatcher em = new EntityMatcher();

        //long start1 = System.nanoTime();
        HashMap<String, List<TableEntry>> map = Parser.getTableEntryMap();
        //long end1 = System.nanoTime(); 
        //System.out.println("Parsing List took: "+ (end1-start1) + "ns");


        boolean token = true;
        Scanner sc = new Scanner(System.in);
        int counter = 0;
        List<RulePercentagePair> bestRuleList = em.getSortedRuleSet();
        //System.out.println("Best Rule List:" + bestRuleList);
        while(token) {
            
            for(int i = 0 ; i < bestRuleList.size(); i++) {
                System.out.println("Accept Rule " + bestRuleList.get(i).toString() + " ?");
                String response = sc.next();
                if(response.equals("y")) {
                    Rule bestRule = bestRuleList.get(i).getRule();
                    
                    //long start2 = System.nanoTime();
                    for(Map.Entry<String, List<TableEntry>> entry: map.entrySet()) {
                        /* if(counter > 5) {
                            break;
                        } */
                        String key = entry.getKey();
                        List<TableEntry> list = entry.getValue();
                        //System.out.println("Key: " + key + "\nList: " + list);
                        em.filter(list, bestRule);
                        counter++;
                    }
                    //long end2 = System.nanoTime(); 
                    //System.out.println("Filtering took: "+ (end2-start2) + "ns");           
                } else if(response.equals("n")) {
                    continue;
                } else {
                    System.out.println("Incorrect Input");
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
        //System.out.println("Ergebnis: " + table);
    }

    
}
