package pra.stringsearch;

/**
 * Knuth–Morris–Prat strategy for finding substring
 */
public class KMPStringSearch extends StringSearchStrategy {
    
    private final int[] next;
    
    public KMPStringSearch(String pattern){
        super(pattern);
        
        next = new int[patternLength+1];
        preprocessKMP();
    }

    public int find(String content, int begin){
        int n = content.length();
        int i = 0;
        int j = begin;
        while(j < n){
            while(i > -1 && pattern.charAt(i) != content.charAt(j)) i = next[i];
            i++;
            j++;
            if(i >= patternLength) return j - i;
        }
        return NOT_FOUND;
    }
    
    private void preprocessKMP(){
        int i = 0;
        int j = next[0] = -1;
        while(i < patternLength){
            while(j > -1 && pattern.charAt(i) != pattern.charAt(j)) j = next[j];
            next[++i] = ++j;
        }
    }

}
