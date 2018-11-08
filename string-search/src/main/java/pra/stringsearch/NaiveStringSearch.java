package pra.stringsearch;

/**
 * Naive strategy for finding substring
 */
public class NaiveStringSearch extends StringSearchStrategy {

    public NaiveStringSearch(String pattern){ super(pattern); }
    
    public int find(String content, int begin){
        int n = content.length();
        for(int i=begin; i<=n - getPatternLength(); i++){
            int j=0;
            while(j<getPatternLength() && content.charAt(i+j) == getPattern().charAt(j)) j++;
            if(j == getPatternLength()) return i;
        }
        
        return NOT_FOUND;
    }

}
