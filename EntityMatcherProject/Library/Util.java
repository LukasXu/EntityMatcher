package EntityMatcherProject.Library;

import java.util.List;

public class Util {

    public static String toStringList(List list, String matchString, String replaceString) {
        return list.toString().replaceAll(matchString, replaceString);
    }
    
}
