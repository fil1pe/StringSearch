package pra.stringsearch;

/**
 * Boyer Moore strategy for finding substring
 */
public class BoyerMooreStringSearch extends StringSearchStrategy {
    
    private int[] right = new int[256];
    
    public BoyerMooreStringSearch(String pattern){
        super(pattern);
    }
    
    public int find(String content, int begin){
        
        return NOT_FOUND;
    }

}
