package pra.stringsearch;

/**
 * Naive strategy for finding substring
 */
public class NaiveStringSearch extends StringSearchStrategy {

    public NaiveStringSearch(String pattern){
        super(pattern);
    }
    
    public int find(String content, int begin){
        int n = content.length();
        for(int i=begin;i<n - patternLength;i++){
            int j=0;
            while(j<patternLength && content.charAt(i+j) == pattern.charAt(j)) j++;
            if(j == patternLength) return i;
        }
        return NOT_FOUND;
    }

}
