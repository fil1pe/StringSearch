package pra.stringsearch;

/**
 * Boyer Moore strategy for finding substring
 */
public class BoyerMooreStringSearch extends StringSearchStrategy {

    private final int R = 256;
    private final int[] right = new int[R];

    public BoyerMooreStringSearch(String pattern) {
        super(pattern);
        for (int i = 0; i < R; i++) {
            right[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            right[pattern.charAt(i)] = i;
        }
    }

    public int find(String content, int begin) {
        int n = content.length();
        int skip;
        for (int i = 0; i <= n - getPatternLength(); i += skip) {
            skip = 0;
            for (int j = getPatternLength() - 1; j >= 0; j--) {
                if (getPattern().charAt(j) != content.charAt(i + j)) {
                    skip = Math.max(1, j - right[content.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }

        return NOT_FOUND;
    }

}
