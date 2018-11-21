package pra.stringsearch;

import java.util.ArrayList;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    protected final int NOT_FOUND = -1;
    private final String pattern;
    private final int patternLength;

    public StringSearchStrategy(String pattern) {
        this.pattern = pattern;
        patternLength = pattern.length();
    }

    /**
     * Find the position of the pattern in a string
     *
     * @param content
     * @param begin
     * @return index of the substring, or -1 if the content doesn't contain it
     */
    public abstract int find(String content, int begin);

    /**
     * Find all the positions of the pattern in a string
     *
     * @param content
     * @return array of indexes where we can find the substring
     */
    public ArrayList<Integer> find(String content) {
        ArrayList<Integer> positions = new ArrayList();

        int foundAt, begin = 0;
        do {
            foundAt = find(content, begin);
            begin = foundAt + patternLength;
            if (foundAt == NOT_FOUND) {
                break;
            }
            positions.add(foundAt);
        } while (content.length() - begin >= patternLength);

        return positions;
    }

    public String getPattern() {
        return pattern;
    }

    public int getPatternLength() {
        return patternLength;
    }

}
