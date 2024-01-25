package EntityMatcherProject.Sax;

import java.util.ArrayList;
import java.util.List;

import EntityMatcherProject.InitialEntry;

public class SaxGenerator {
    static SaxTable saxTable;

    public SaxGenerator(SaxTable sax) {
        saxTable = sax;
    }
    
    public static String generateSaxString(int bracketCount, InitialEntry entry) {
        List<Double> list = new ArrayList<>();
        int interval = (entry.getMaxYear() - entry.getMinYear()) / bracketCount;
        int counter = 0;
        double factor = (double) bracketCount / (entry.getMaxYear() - entry.getMinYear()); //Decompression Rate

        for (int i = 0; i < bracketCount; i++) {
            if (counter >= entry.getPairList().size()) {
                break;
            }

            double sum = 0;

            int start = entry.getMinYear() + (interval * i);
            int end = (start + interval);
            int currentYear = entry.getPairList().get(counter).getYear();

            while (start <= currentYear && currentYear < end) {
                int year = entry.getPairList().get(counter).getYear();
                currentYear = year;
                if (year >= end) {
                    break;
                }           
                double value = (entry.getPairList().get(counter).getFrequency() - entry.getMean()) / entry.getDeviation();
        
                sum += value;
                counter++;
            }        
            double avg = sum * factor;         
            list.add(avg);
            start += interval;
        }

        
        String sax = "";
        for (int i = 0; i < list.size(); i++) {
            double num = list.get(i);
            sax += saxTable.getSaxString(num);
            //System.out.println("Num: " + num + "sax: " + saxTable.getSaxString(num));
        }
        return sax;
    }
    
}
