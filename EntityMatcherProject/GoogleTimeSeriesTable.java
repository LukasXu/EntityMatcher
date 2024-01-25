package EntityMatcherProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import EntityMatcherProject.Library.Util;
import EntityMatcherProject.Pair.InitialPair;

public class GoogleTimeSeriesTable {
    static HashMap<String, List<InitialPair>> table = new HashMap<>();

    public GoogleTimeSeriesTable() {
        init();
    }

    private void init() {
        File file = new File("1_20000_nopos.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] test = line.split("\t"); //0 == word, >=1 == frequency
                String word = test[0];

                if(word.length() == 1) {
                    continue;
                }
                List<InitialPair> timeSeries = Util.formatToInitialPairArray(test, 1);
                table.put(word, timeSeries);         
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Google Corpus Size: " + table.size());
    }

    public static List<InitialPair> getTimeSeries(String word) {
        return table.get(word);
    }
    
}
