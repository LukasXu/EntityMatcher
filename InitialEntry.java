import java.util.ArrayList;
import java.util.List;

public class InitialEntry {
    String word;
    double mean;
    double deviation;
    List<InitialPair> pairList;

    public InitialEntry(String word, List<InitialPair> pairList) {
        this.word = word;
        this. pairList = pairList;
        this.mean = setMean();
        this.deviation = setDeviation();

        System.out.println("Mean:" + this.mean);
        System.out.println("Deviation" + this.deviation);
    }

    private double setMean() {
        double sum = 0;
        for(int i = 0; i < pairList.size(); i++) {
            sum += pairList.get(i).getFrequency();
        }
        return (sum / pairList.size());
    }

    private double setDeviation() {
        double sum = 0;
        for(int i = 0; i < pairList.size(); i++) {
            sum += Math.pow((pairList.get(i).getFrequency() - this.mean), 2);
        }
        return Math.sqrt(sum / pairList.size()); 
    }

    public String getWord() {
        return this.word;
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < pairList.size(); i++) {
            //String suffix = i % 8 == 0 ? "\n" : " "; 
            str += pairList.get(i).toString() + " ";
        }
        return this.word + " " + str + "\n";
    }

    public int getMinYear() {
        return pairList.get(0).getYear();
    }

    public int getMaxYear() {
        return pairList.get(pairList.size() - 1).getYear();
    }

    public int getMinFreqYear() {
        int num = 0;
        for(int i = 0; i < pairList.size(); i++) {
            int freq = pairList.get(i).getFrequency();
            if(freq >=  10 || num == 0) {
                num = pairList.get(i).getYear();
            }
        }
        return num;
    }

    public int getMaxFreqYear() {
        int num = 0;
        for(int i = 0; i < pairList.size(); i++) {
            int freq = pairList.get(i).getFrequency();
            if(freq > num) {
                num = pairList.get(i).getYear();
            }
        }
        return num;
    }

    public String getSaxString(int bracketCount) {
        List<Double> list = new ArrayList<>();
        int interval = (this.getMaxYear() - this.getMinYear()) / bracketCount;
        int counter = 0;
        double factor = (double) bracketCount / (this.getMaxYear() - this.getMinYear());

        //System.out.println("N: " + (this.getMaxYear() - this.getMinYear()) + " From: " + this.getMinYear() + " To: " + this.getMaxYear());
        //System.out.println("Factor:" + factor); 
        //System.out.println("Interval:" + interval);

        for(int i = 0; i < bracketCount; i++) {
            if(counter >= pairList.size()) {  
                break; 
            }

            double sum = 0;
           
            int start = this.getMinYear() + (interval * i);
            int end = (start + interval);
            System.out.println("Start: " + start + " End: " + end);
            int currentYear = pairList.get(counter).getYear();
            
            while (start <= currentYear && currentYear < end) {  
                int year = pairList.get(counter).getYear(); 
                currentYear = year;
                if(year >= end) {
                    break;
                }                  
                System.out.println("Year: " + year);                          
                sum += (pairList.get(counter).getFrequency() - this.mean) / this.deviation;
                counter++;
            }
            System.out.println("Sum:" + sum);
            double avg = sum * factor;
            System.out.println("AVG:" + avg);
            list.add(avg);
            start += interval;
        }
        
        //Für A = 3
        String sax = "";
        for(int i = 0; i < list.size(); i++) {
            double num = list.get(i);
            if(num < -0.43) {
                sax += "a";
            } else if (-0.43 <= num && num < 0.43) {
                sax += "b";
            } else {
                sax += "c";
            }           
        }
        return sax;
    }


}
