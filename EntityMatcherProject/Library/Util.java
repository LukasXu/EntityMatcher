package EntityMatcherProject.Library;

import java.util.ArrayList;
import java.util.List;

import EntityMatcherProject.Pair.InitialPair;

public class Util {

    public static String toStringList(List list, String matchString, String replaceString) {
        return list.toString().replaceAll(matchString, replaceString);
    }

    public static List<InitialPair> formatToInitialPairArray(String[] strings, int startIndex) {
        List<InitialPair> result = new ArrayList<>();
        for(int i = startIndex; i < strings.length; i++) {
            String[] placeholder = strings[i].split(",");
            int year = Integer.valueOf(placeholder[0]);
            if(year < 1995) {
                continue;
            }
            int frequency = Integer.valueOf(placeholder[1]);
            InitialPair p = new InitialPair(year, frequency, 0);
            result.add(p);
        }
        return result;
    }
    
}
