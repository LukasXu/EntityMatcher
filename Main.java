import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EntityMatcher {
    public static void main(String[] args) {
        int counter = 0;
        File file = new File("1_20000_nopos.txt");

        List<String> wordList = new ArrayList<String>();
        Scanner sc;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine() && counter < 1) {
                InitialEntry entry = parseInitialEntry(sc.nextLine());
                System.out.println(entry.getSaxString(8));
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
