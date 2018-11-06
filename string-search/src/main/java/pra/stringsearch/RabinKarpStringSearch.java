package pra.stringsearch;

import static pra.Utils.hash;

/**
 * Rabin Karp strategy for finding substring
 */
public abstract class RabinKarpStringSearch extends StringSearchStrategy {
    
    public RabinKarpStringSearch(String pattern){
        super(pattern);
    }

    public int find(String content, int begin){
        int n = content.length();
        int hpattern = hash(pattern, 0, patternLength);
        for(int i=begin; i<n - patternLength; i++){
            int hs = hash(content, i, i+patternLength);
            if(hs == hpattern) && pattern.equals( content.substring(i, i + patternLength) )) return i;
        }
        return NOT_FOUND;
    }

}
