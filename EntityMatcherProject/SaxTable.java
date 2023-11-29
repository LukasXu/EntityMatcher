package EntityMatcherProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SaxTable {
    private List<Double> saxTable = new ArrayList<>();
    private List<Double> normalSegments = Arrays.asList(34.13, 13.59, 2.14, 0.13);
   
    public SaxTable(int n) {
        generateSaxTable(n);
    }

    private void generateSaxTable(int n) {
        if(n <= 2) {
            return;
        }
        
        boolean isEven = (n % 2 == 0);
        if(isEven) {
            saxTable.add(0.0);
        }
        int loops = isEven ? (n / 2) - 1 : (n / 2);
        double segmentSize = isEven ? (double) 100 / n :  ((double) (100 / n) / 2);
        int listIndex = 0;
        double index = 0;
        double offset = 0;
        double lastResult = 0;
        for(int i = 0; i < loops; i++) {
            if(i > 0) {
                segmentSize = (double) 100 / n;
            }

            int counter = 0;

            while(segmentSize > offset) {
                offset += normalSegments.get(listIndex);
                listIndex++;
                counter++;
            }
            double distance = index + (1 * counter);
            double percentage = (double) segmentSize / offset;
            double x = lastResult + distance * percentage;

            System.out.println("Distance: " + distance + " Percentage: " + percentage + " X: " + x);
            saxTable.add(x);
            saxTable.add(-x);
            lastResult = x;
            index = distance - x;
            offset -= segmentSize;         
        }
        Collections.sort(saxTable);
    }
    
}
