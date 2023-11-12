package EntityMatcherProject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Main {
    
    
    public static void main(String[] args) {
        EntityMatcher em = new EntityMatcher();
        List<TableEntry> table = parseToList();
        //System.out.println(em.getExampleResult());
        Pair bestRule = em.getSortedRuleSet().get(em.getSortedRuleSet().size() - 1).getPair();
        em.filter(table, bestRule);
        System.out.println(table);
    
    }

    public static List<TableEntry> parseToList() {
        int counter = 0;
        File file = new File("1_20000_nopos.txt");
        List<TableEntry> list = new ArrayList<>();      
        Scanner sc;
        try {
            sc = new Scanner(file);
            //Reads every entry and turns it into a InitialEntry object
            while (sc.hasNextLine() && counter < 5) {
                InitialEntry entry = parseInitialEntry(sc.nextLine());
                TableEntry tableEntry = new TableEntry(entry);
                list.add(tableEntry);
                counter++;
            }
            //System.out.println(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static InitialEntry parseInitialEntry(String record) {
        String[] firstSplit = record.split("\t");  
        String word = firstSplit[0];   
        List<InitialPair> list = new ArrayList<InitialPair>();
        for(int i = 1; i < firstSplit.length; i++) {
            String[] sndSplit = firstSplit[i].split(",");
            InitialPair p = new InitialPair(Integer.valueOf(sndSplit[0]), Integer.valueOf(sndSplit[1]), Integer.valueOf(sndSplit[2]));
            list.add(p);
        } 
        return new InitialEntry(word, list);              
    }
}
