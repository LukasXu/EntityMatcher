package EntityMatcherProject.RefCorpus;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import EntityMatcherProject.InitialEntry;
import EntityMatcherProject.Pair.InitialPair;

public class RefParser {
    private static int counter = 0;
    private static HashMap<String, Integer> refMap = new HashMap<String, Integer>();
    private RefTable refTable;

    public RefParser(RefTable refTable) {
        this.refTable = refTable;
        scanAll();
    }

    public void scanAll() {
        for(int i = 1995; i < 2020; i++) {
            try {
               
                RefParser.scan("C:\\Users\\gabu0\\Downloads\\Ref_Corp\\Words\\deu_news_"+ i + "_300K-words.txt", refTable.getRefEntries(), i);
                //RefParser.scan("C:\\Users\\gabu0\\Downloads\\EM\\EntityMatcher-main\\EntityMatcher-main\\test.txt", refTable.getRefEntries(), i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(refTable.getRefEntries().size());
        //writeToFile(refTable.toString());
    }

    private void writeToFile(String text) {
        File refCorpFile = new File("refCorp.txt");
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("refCorp.txt");
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

    //Scans a ref file and writes into the given RefEntry List
    public static void scan(String filePath, List<RefEntry> list, int year) throws IOException {
        File file = new File(filePath);
        FileReader fr;
    
        fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
       
        while ((line = br.readLine()) != null) {
            
            String[] test = line.split("\t"); //0 == id, 1 == word, 2 == frequency
            String word = test[1];
            int frequency = Integer.parseInt(test[2]);

            if(containsNumber(word) == true) {
                //System.out.println(word);
                continue;
            }
            InitialPair initialPair = new InitialPair(year, frequency, 0);

           

            //Falls wort im map ist, add frequency or add entry otherwise
            if(refMap.get(word) == null) {
                refMap.put(word, counter);
                RefEntry entry = new RefEntry(counter, word);
                entry.addFreqEntry(initialPair);
                list.add(entry);
                counter++;
            } else {
                int refId = refMap.get(word);
                list.get(refId).addFreqEntry(initialPair);
            }
        }
        br.close();
        fr.close();
    }

    private static boolean containsNumber(String word) {
        Pattern noNumberPattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);

        Matcher matcherNumber = noNumberPattern.matcher(word);
        boolean containsNumber = matcherNumber.find();

        return containsNumber;
    }

    public static InitialEntry parseRefInitialEntry(String record) {
        String[] firstSplit = record.split("\t");  
        String word = firstSplit[1];   
        List<InitialPair> list = new ArrayList<InitialPair>();
        //System.out.println(record);
        for(int i = 2; i < firstSplit.length; i++) {
            String[] sndSplit = firstSplit[i].split(",");
            
            InitialPair p = new InitialPair(Integer.valueOf(sndSplit[0]), Integer.valueOf(sndSplit[1]), Integer.valueOf(sndSplit[2]));
            list.add(p);
        } 
        return new InitialEntry(word, list);              
    }

}