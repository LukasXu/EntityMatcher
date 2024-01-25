package EntityMatcherProject.Functions;

import java.util.List;

public class EuclideanDistance {

    public static double euclideanDistance2D(int x1, int y1, int x2 ,int y2) {
        double sum = (Math.pow(y2 - y1, 2) +  Math.pow(x2 - x1, 2));
        double result = Math.sqrt(sum);
        return result;
    }

    public static double euclideanDistance1D(int x1, int x2) {
        double sum = Math.pow(x2 - x1, 2);
        double result = Math.sqrt(sum);
        return result;
    }

    public static double euclidDistance(List<Double> distanceList) {
        double sum = 0;
        for (Double distance : distanceList) {
            sum += Math.pow(distance, 2);
        }
        double result = Math.sqrt(sum);
        return result;
    }
    
}
