package pra.stringsearch;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    private int final NOT_FOUND = -1;

    public int find(String content, String substr, int begin);

}
