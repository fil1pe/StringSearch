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
        int m = pattern.length();
        int hpattern = hash(pattern, 0, m);
        for(int i=begin;i<n-m;i++){
            int hs = hash(content, i, i+m);
            if(hs == hpattern) && pattern.equals( content.substring(i, i+m) )) return i;
        }
        return NOT_FOUND;
    }

}
