package pra.stringsearch;

import pra.Hash;

/**
 * Rabin Karp strategy for finding substring
 */
public class RabinKarpStringSearch extends StringSearchStrategy {

    private final Hash patternHash;

    public RabinKarpStringSearch(String pattern) {
        super(pattern);
        patternHash = new Hash(pattern, 0, pattern.length());
    }

    public int find(String content, int begin) {
        int n = content.length();

        if (n - begin < getPatternLength()) {
            return NOT_FOUND;
        }

        Hash contentPattern = new Hash(content, begin, getPatternLength());
        int i;
        for (i = begin; i < n - getPatternLength(); i++) {
            if (patternHash.equals(contentPattern)
                    && getPattern().equals(content.substring(i, i + getPatternLength()))) {
                return i;
            }
            contentPattern.updateHash();
        }
        if (patternHash.equals(contentPattern)
                && getPattern().equals(content.substring(i, i + getPatternLength()))) {
            return i;
        }

        return NOT_FOUND;
    }

}
