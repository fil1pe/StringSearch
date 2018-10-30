package pra.stringsearch;

/**
 * Knuth–Morris–Prat strategy for finding substring
 * source: http://www.stoimen.com/blog/2012/04/09/computer-algorithms-morris-pratt-string-searching/
 */
public abstract class RabinKarpStringSearch extends StringSearchStrategy {

    public int find(String content, String substr, int begin){
        int n = content.length();
        int m = substr.length();
        int i = 0;
        int j = 0;
        int[] T = preprocessKMP(substr);
        while(j < n){
            while(i > -1 && substr.charAt(i) != content.charAt(j)) i = T[i];
            i++;
            j++;
            if(i >= m) return j - i;
        }
        return NOT_FOUND;
    }
    
    private int[] preprocessKMP(String pattern){
        int[] T = new int[pattern.length()+1];
        int i = 0;
        int j = T[0] = -1;
        int m = pattern.length();
        while(i < m){
            while(j > -1 && pattern.charAt(i) != pattern.charAt(j)) j = T[j];
            T[++i] = ++j;
        }
        return T;
    }

}
