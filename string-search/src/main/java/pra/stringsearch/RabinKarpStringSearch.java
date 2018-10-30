package pra.stringsearch;

import static pra.Utils.hash;

/**
 * Rabin Karp strategy for finding substring
 */
public abstract class RabinKarpStringSearch extends StringSearchStrategy {

    public int find(String content, String substr, int begin){
        int n = content.length();
        int m = substr.length();
        int hpattern = hash(substr, 0, m);
        for(int i=begin;i<n-m;i++){
            int hs = hash(content, i, i+m);
            if(hs == hpattern) && substr.equals( content.substring(i, i+m) )) return i;
        }
        return NOT_FOUND;
    }

}
