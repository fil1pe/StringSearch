package pra.stringsearch;

public class SSRuntimeDecorator extends StringSearchStrategy {
    
    private StringSearchStrategy strategy;
    private String decoratedText;
    
    public SSRuntimeDecorator(StringSearchStrategy strategy, String decoratedText){
        super("");
        this.strategy = strategy;
        this.decoratedText = decoratedText;
    }
    
    public int find(String content, int begin){
        long startTime = System.nanoTime();
        int answer = strategy.find(content, begin);
        long totalTime = System.nanoTime() - startTime;
        decoratedText = totalTime + " ns";
        return answer;
    }
    
}
