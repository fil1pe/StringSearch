package pra.stringsearch;

import java.lang.StringBuffer;

/**
 * Naive strategy for finding substring
 */
public abstract class NaiveStringSearch extends StringSearchStrategy {

    public int find(StringBuffer content, String substr, int begin){
        int n = content.length();
        int m = substr.length();
        for(int i=begin;i<n-m;i++){
            int j=0;
            while(j<m && content.charAt(i+j) == substr.charAt(j)) j++;
            if(j == m) return i;
        }
        return NOT_FOUND;
    }

}
