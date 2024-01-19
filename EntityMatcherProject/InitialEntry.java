package EntityMatcherProject;
import java.util.List;

import EntityMatcherProject.Pair.InitialPair;

public class InitialEntry {
    private String word;
    private double mean;
    private double deviation;
    private List<InitialPair> pairList;
    private String saxString;
    private double occurenceAvg;
    private static int saxLength = 8;

    public InitialEntry(String word, List<InitialPair> pairList) {
        this.word = word;
        this.pairList = pairList;
        setOccurenceAvg();
        this.mean = setMean();
        this.deviation = setDeviation();
        this.saxString = SaxGenerator.generateSaxString(saxLength, this);
    }

    private double setMean() {
        double sum = 0;
        for (int i = 0; i < pairList.size(); i++) {
            sum += pairList.get(i).getFrequency();
        }
        return (sum / pairList.size());
    }

    private double setDeviation() {
        double sum = 0;
        for (int i = 0; i < pairList.size(); i++) {
            sum += Math.pow((pairList.get(i).getFrequency() - this.mean), 2);
        }
        return Math.sqrt(sum / pairList.size());
    }

    //Gets freqency average accros time line
    private void setOccurenceAvg() {
        double sum = 0;
        for(InitialPair p : pairList) {
            sum += (double) p.getFrequency() / (getMaxYear() - getMinYear());
        }
        this.occurenceAvg = sum;
        //System.out.println("OccAVg: " + occurenceAvg);
    }

    public int getMinFreqYear() {
        int num = 0;
        for (int i = 0; i < pairList.size(); i++) {
            int freq = pairList.get(i).getFrequency();
            if (freq >= 10 || num == 0) {
                num = pairList.get(i).getYear();
            }
        }
        return num;
    }

    public int getMaxFreqYear() {
        int num = 0;
        for (int i = 0; i < pairList.size(); i++) {
            int freq = pairList.get(i).getFrequency();
            if (freq > num) {
                num = pairList.get(i).getYear();
            }
        }
        return num;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < pairList.size(); i++) {
            // String suffix = i % 8 == 0 ? "\n" : " ";
            str += pairList.get(i).toString() + " ";
        }
        return this.word + " " + str + "\n";
    }

    /////////////////////////// Setter/Getter

    public String getWord() {
        return this.word;
    }

    public String getSaxString() {
        return saxString;
    }

    public int getMinYear() {
        return pairList.get(0).getYear();
    }

    public int getMaxYear() {
        return pairList.get(pairList.size() - 1).getYear();
    }
    
    public List<InitialPair> getPairList() {
        return pairList;
    }

    public double getMean() {
        return mean;
    }

    public double getDeviation() {
        return deviation;
    }

    public double getOccurenceAvg() {
        return occurenceAvg;
    }

}
