package pra.stringsearch;

/**
 * Abstract strategy for finding substring
 */
public abstract class StringSearchStrategy {

    protected final int NOT_FOUND = -1;
    private final String pattern;
    private final int patternLength;
    
    public StringSearchStrategy(String pattern){
        this.pattern = pattern;
        patternLength = pattern.length();
    }
    
    /**
     * Find the position of the pattern (substring)
     * 
     * @param content
     * @param begin
     * @return index of the substring, or -1 if the content doesn't contain it
     */
    public abstract int find(String content, int begin);
    
    public String getPattern(){ return pattern; }
    
    public int getPatternLength(){ return patternLength; }

}
