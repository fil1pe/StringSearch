package pra.stringsearch;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    private int final NOT_FOUND = -1;

    public static int find(StringBuffer content, String substr);

}
