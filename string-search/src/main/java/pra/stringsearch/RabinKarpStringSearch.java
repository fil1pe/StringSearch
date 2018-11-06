package pra.stringsearch;

import pra.Hash;

/**
 * Rabin Karp strategy for finding substring
 */
public abstract class RabinKarpStringSearch extends StringSearchStrategy {
    
    private final Hash patternHash;
    
    public RabinKarpStringSearch(String pattern){
        super(pattern);
        patternHash = new Hash(pattern, 0, patternLength);
    }

    public int find(String content, int begin){
        int n = content.length();
        Hash contentPattern = new Hash(content, 0, patternLength);
        for(int i=begin; i<n - patternLength; i++){
            if( patternHash.equals(contentPattern) ) && pattern.equals( content.substring(i, i + patternLength) )) return i;
            contentPattern.updateHash();
        }
        return NOT_FOUND;
    }

}
