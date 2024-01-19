package EntityMatcherProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import EntityMatcherProject.Pair.InitialPair;
import EntityMatcherProject.RefCorpus.RefEntry;
import EntityMatcherProject.RefCorpus.RefParser;

public class Comparitor {
    private static File refCorp = new File("refCorp.txt");

    public Comparitor() {
    }

    public static void compare()  {
        /* Scanner sc;
        try {
            sc = new Scanner(refCorp);
            while (sc.hasNextLine()) {
                String refEntry = sc.nextLine();

                System.out.println(refEntry);
                InitialEntry entry = RefParser.parseRefInitialEntry(sc.nextLine());
                
                //Parser.findEntry(null);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } */

        File file = new File("refCorp.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
       
            while ((line = br.readLine()) != null) {
                
                String[] test = line.split("\t"); //0 == id, 1 == word, 2 == frequency
                String word = test[1];

            }
            br.close();
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
      
    }
}
