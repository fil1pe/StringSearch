package pra.stringsearch;

/**
 * Knuth–Morris–Prat strategy for finding substring
 */
public class RabinKarpStringSearch extends StringSearchStrategy {
    
    private final int[] next;
    
    public RabinKarpStringSearch(String pattern){
        super(pattern);
        preprocessKMP();
    }

    public int find(String content, int begin){
        int n = content.length();
        int m = substr.length();
        int i = 0;
        int j = 0;
        while(j < n){
            while(i > -1 && substr.charAt(i) != content.charAt(j)) i = next[i];
            i++;
            j++;
            if(i >= m) return j - i;
        }
        return NOT_FOUND;
    }
    
    private final void preprocessKMP(){
        next = new int[pattern.length()+1];
        int i = 0;
        int j = next[0] = -1;
        int m = pattern.length();
        while(i < m){
            while(j > -1 && pattern.charAt(i) != pattern.charAt(j)) j = next[j];
            next[++i] = ++j;
        }
    }

}
