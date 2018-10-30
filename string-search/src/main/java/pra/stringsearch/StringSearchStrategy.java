package pra.stringsearch;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    private final int NOT_FOUND = -1;
    private final String pattern;
    
    public StringSearchStrategy(String pattern){
        this.pattern = pattern;
    }

    public int find(String content, int begin);

}
