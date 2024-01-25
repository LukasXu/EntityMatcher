package EntityMatcherProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import EntityMatcherProject.Functions.EuclideanDistance;
import EntityMatcherProject.Library.Util;
import EntityMatcherProject.Pair.InitialPair;


public class Comparitor {
    private EvaluationTable evaluationTable = new EvaluationTable();

    public Comparitor() {
    }

    
    public void compare()  {
        File file = new File("refCorp.txt");
        int counter = 0;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            //Für jeden Eintrag in Ref Corpus wird Zeitreihe verglichen zwischen gef. und original Corpus
            while ((line = br.readLine()) != null) {
                String[] test = line.split("\t"); //0 == id, 1 == word, >=2 == frequency
                String word = test[1];

                if(word.length() == 1) {
                    continue;
                }
                TableEntry matchEntry = Parser.findEntry(word);

                //Falls die Wörter nicht im gef. Corpus oder zu wenige Einträge in der Time Series sind, wird es übersprungen
                if(matchEntry == null || test.length < 7) {
                    continue;
                }
                List<InitialPair> originialTimeSeries = GoogleTimeSeriesTable.getTimeSeries(word);
                List<InitialPair> filteredTimeSeries = matchEntry.getPairList();
                List<InitialPair> refTimeSeries = Util.formatToInitialPairArray(test, 2);

                if(originialTimeSeries == null || filteredTimeSeries == null) {
                    System.out.println(originialTimeSeries);
                }
                
                double orignalMetric = compareTimeSeries(originialTimeSeries, refTimeSeries);
                double filteredMetric = compareTimeSeries(filteredTimeSeries, refTimeSeries);

                EvaluationEntry evaluationEntry = new EvaluationEntry(word, orignalMetric, filteredMetric);
                evaluationTable.addEntry(evaluationEntry); 
                counter++;
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Counter: " + counter);
    }

    private static double compareTimeSeries(List<InitialPair> list1, List<InitialPair> list2) {
        int index1 = 0;
        int index2 = 0;
        List<Double> result = new ArrayList<>();

        while (index1 < list1.size() && index2 < list2.size()) {
            InitialPair p1 = index1 >= list1.size() ? null : list1.get(index1);
            InitialPair p2 = index2 >= list2.size() ? null : list2.get(index2);
            double distance;
       
            if(p2 == null || p1.getYear() < p2.getYear()) {
                distance = p1.getFrequency();
                result.add(distance);
                index1++;
            } else if(p1.getYear() == p2.getYear()) {
                distance = p2.getFrequency() - p1.getFrequency();
                result.add(distance);
                index1++;
                index2++;
            } else if(p1 == null || p1.getYear() > p2.getYear()) {
                distance = p2.getFrequency();
                result.add(distance);
                index2++;
            }
        }
        double euclidDistance = EuclideanDistance.euclidDistance(result);
        return euclidDistance;
    }

    public EvaluationTable getEvaluationTable() {
        return evaluationTable;
    }
}
