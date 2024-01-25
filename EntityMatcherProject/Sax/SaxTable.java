package EntityMatcherProject.Sax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;

public class SaxTable {
    private static List<Double> saxTable = new ArrayList<>();
   
    
    
    public SaxTable(int n) {
        generateSaxTable(n);
        System.out.println("SaxTable:" + saxTable);
    }

    private void generateSaxTable(int n) {
        int counter = 0;
        int counter2 = 1;
        double index = -3.0;
        double percentage = round2Dec((double) 1 / n);
       
        while(counter < (n - 1) && index < 3) {
            double dist = Math.abs(getCDF(index) - (percentage * counter2));
            if(dist < 0.0001) {
                //System.out.println("CDf:" + getCDF(index) + " index: " + index);
                saxTable.add(index);               
                counter++;
                counter2++;
            }
            index += 0.01;
        }
        Collections.sort(saxTable);
    }
    
    double getCDF(double x) {
        NormalDistribution normal = new NormalDistribution(0, 1);
        double y = normal.cumulativeProbability(x);
        double roundedY = round2Dec(y);
        //System.out.println("Y:" + roundedY);
        return roundedY;
    } 

    double round2Dec(double n) {
        return (double) Math.round(n * 100) / 100;
    }

    public String getSaxString(double n) {
        char c = 97;
        for(int i = 0; i < saxTable.size(); i++) {
            //System.out.println("N" + n);
            if(n < saxTable.get(i)) {
                return String.valueOf(c);
            } 
            c++;
        }
        return String.valueOf(c);
    }

    public static double getDistance(char x, char y) {
        char a = 'a';
        if(Math.abs(x - y) <= 1) {
            return 0;
        }
        int normX = x >= y ? ((x - a) - 1) : (x - a);
        int normY = x >= y ? (y - a) : ((y - a) - 1);

        double v1 = Math.abs(saxTable.get(normX));
        double v2 = Math.abs(saxTable.get(normY));
        double result = x >= y ? v1 - v2 : v2 - v1;

        //System.out.println("X: " + x + " Y: " + y + "Result" +  result);

        return result;
    }


}
