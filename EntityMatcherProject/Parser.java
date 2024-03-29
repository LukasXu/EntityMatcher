package EntityMatcherProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import EntityMatcherProject.Pair.InitialPair;

public class Parser {
    private File file = new File("1_20000_nopos.txt");
    private static HashMap<String, List<TableEntry>> tableEntryMap = new HashMap<>(); //Key = Wort, Prefix -> Wortgruppe#
    Pattern noNumberPattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);

    public Parser() {
        init();
        thinOutMap();
        //parseTableMap();
    }

    //Printet Inhalt von tableEntryMap 
    public void parseTableMap() {
        for(Map.Entry<String, List<TableEntry>> entry: tableEntryMap.entrySet()) {
            String key = entry.getKey();
            List<TableEntry> list = entry.getValue(); 
            System.out.println("Key: " + key + "\nList: " + list + "\n");
        }
    }
    
    //Parsed txt Einträge in TableEntry und speichert es in die Hashmap
    private void init() {
        Scanner sc;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine() ) {
                InitialEntry entry = parseInitialEntry(sc.nextLine());
                //Wörter der Länge <= 1 werden direkt rausgefiltert
                if(entry.getWord().length() <= 1) {
                    continue;
                }

                Matcher matcherNumber = noNumberPattern.matcher(entry.getWord()); // Um Wörter mit Zahlen zu filtern
                boolean containsNumber = matcherNumber.find();

                if(containsNumber == true) {
                    continue;
                }

                TableEntry tableEntry = new TableEntry(entry);     
                matchPrefix(2, tableEntry); //Schaut ob für Prefix der Länge n des Wortes im Entry schon ein Eintrag im Map existiert, falls nicht füge hinzu
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    //Sucht in der Hashmap nach dem Eintrag, dass dem Wort entspricht
    public static TableEntry findEntry(String word) {
        if(word.length() <= 1) {
            return null;
        }
        List<TableEntry> list = new ArrayList<>();
        for(int i = word.length(); i > 1 ; i--) {
            String prefix = getPrefix(i, word); // Start from full word as prefix 
            list = tableEntryMap.get(prefix);
            if(list == null) {
                continue;
            } else {
                break;
            }
        }

        if(list == null) {
            return null;
        }

        for(TableEntry entry: list) {
            if(entry.getWord().equals(word)) {
                return entry;
            }
        }
        //System.out.println("Found Entry: " + entry);
        return null;
    }

    //Gibt die ersten n-Stellen des Wortes wieder
    private static String getPrefix(int n, String word) {
        String prefix = "";
        //System.out.println("Word: "+ word+ " N: " + n);
        for (int i = 0; i < n; i++) {
            prefix += String.valueOf(word.charAt(i));
        }
        return prefix;
    }

    //Schaut ob für Prefix der Länge n des Wortes im Entry schon ein Eintrag im Map existiert, falls nicht füge hinzu
    private void matchPrefix(int n, TableEntry entry) {
        if (entry.getWord().length() <= 1) {
            return;
        }
        //Falls Länge vom Wort < n, dann füge Eintrag in map
        if (entry.getWord().length() < n) {
            //System.out.println("Special Case: " + entry.getWord() + " Length: " + entry.getWord().length());
            matchPrefix(entry.getWord().length(), entry);
            return;
        }

        String prefix = getPrefix(n, entry.getWord());
        if (tableEntryMap.get(prefix) == null) {
            List<TableEntry> prefixList = new ArrayList<>();
            prefixList.add(entry);
            tableEntryMap.put(prefix, prefixList);
        } else {
            tableEntryMap.get(prefix).add(entry);
        }
    }

    private void thinOutMap() {
        List<String> longEntryList =  new ArrayList<>();
        for(Map.Entry<String, List<TableEntry>> entry: tableEntryMap.entrySet()) {      
            String key = entry.getKey();
            List<TableEntry> list = entry.getValue();
            if(list.size() > 20) {
                //System.out.println("Key: " + key + " List: " + list + "\n");
                longEntryList.add(key);
            }
        }
    

        for(String key: longEntryList) {
            List<TableEntry> entryList = tableEntryMap.get(key);
            tableEntryMap.remove(key);
            //System.out.println("Key: " + key);
            for(int i = 0; i < entryList.size(); i++) {
                matchPrefix(4, entryList.get(i));
            }
        }

    }


    private static InitialEntry parseInitialEntry(String record) {
        String[] firstSplit = record.split("\t");  
        String word = firstSplit[0];   
        List<InitialPair> list = new ArrayList<InitialPair>();
        //System.out.println(record);
        for(int i = 1; i < firstSplit.length; i++) {
            String[] sndSplit = firstSplit[i].split(",");
            
            InitialPair p = new InitialPair(Integer.valueOf(sndSplit[0]), Integer.valueOf(sndSplit[1]), Integer.valueOf(sndSplit[2]));
            list.add(p);
        } 
        return new InitialEntry(word, list);              
    }


    public static HashMap<String, List<TableEntry>> getTableEntryMap() {
        return tableEntryMap;
    }

    
}
