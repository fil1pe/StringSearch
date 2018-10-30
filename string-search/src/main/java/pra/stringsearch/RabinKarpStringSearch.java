package pra.stringsearch;

import static Utils.hash;

/**
 * Rabin Karp strategy for finding substring
 */
public abstract class NaiveStringSearch extends StringSearchStrategy {

    public int find(String content, String substr, int begin){
        int hpattern = hash(substr);
        int n = content.length();
        int m = substr.length();
        for(int i=begin;i<n-m;i++){
            String s = content.substring(i, i+m);
            int hs = hash(s);
            if(hs == hpattern) if(substr.equals(s)) return i;
        }
        return NOT_FOUND;
    }

}
