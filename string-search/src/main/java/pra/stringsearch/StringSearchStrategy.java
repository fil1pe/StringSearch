package pra.stringsearch;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    protected final int NOT_FOUND = -1;
    protected final String pattern;
    protected final int patternLength;
    
    public StringSearchStrategy(String pattern){
        this.pattern = pattern;
        patternLength = pattern.length();
    }

    public abstract int find(String content, int begin);

}
