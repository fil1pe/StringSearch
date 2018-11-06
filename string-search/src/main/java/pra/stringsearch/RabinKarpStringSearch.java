package pra.stringsearch;

import pra.Hash;

/**
 * Rabin Karp strategy for finding substring
 */
public class RabinKarpStringSearch extends StringSearchStrategy {
    
    private final Hash patternHash;
    
    public RabinKarpStringSearch(String pattern){
        super(pattern);
        patternHash = new Hash(pattern, 0, patternLength);
    }

    public int find(String content, int begin){
        if(content.length() - begin < patternLength) return NOT_FOUND;
        int n = content.length();
        Hash contentPattern = new Hash(content, begin, patternLength);
        for(int i=begin; i<=n - patternLength; i++){
            if( patternHash.equals(contentPattern) && pattern.equals( content.substring(i, i + patternLength) ) ) return i;
            System.out.print(patternHash.value + " ");
            contentPattern.updateHash(); // problem here
            System.out.println(patternHash.value);
        }
        return NOT_FOUND;
    }

}
