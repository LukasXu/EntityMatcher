package EntityMatcherProject.Rules;

import java.util.List;

import EntityMatcherProject.TableEntry;
import EntityMatcherProject.SimilarityFunctions.SimilarityFunction;

public class Rule {
   List<SimilarityFunction> rules; // r1 ^ r2 ^ r3  möglich

   public Rule(List<SimilarityFunction> rules) {
    this.rules = rules;
   }

   public boolean match(TableEntry entry1, TableEntry entry2) {
      for(int i = 0; i < rules.size(); i++) {
         //System.out.println("Comparde: " + entry1.toString() + "," + entry2.toString());
         //System.out.println("Rule Name: " +  rules.get(i).getName());
         boolean b = rules.get(i).match(entry1, entry2);  
         //System.out.println("Result: " + b + "\n");
         if(b == false) {
            return false;
         }
      }
      return  true;
   }

   public String toString() {
      if(rules.size() <= 0) {
         return "Leere Rule Liste";
      }
      if(rules.size() == 1) {
         return rules.get(0).getName();
      }
      String string = "";
      for(int i = 0; i < rules.size(); i++) {
         string += rules.get(i).getName();
         if(i < rules.size() - 1) {
            string += " ^ ";
         }
      }
      return string;
   }
}
