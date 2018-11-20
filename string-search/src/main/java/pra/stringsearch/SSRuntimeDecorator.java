package pra.stringsearch;

public class SSRuntimeDecorator extends StringSearchStrategy {
    
    private StringSearchStrategy strategy;
    private StringBuilder decoratedText;
    
    public SSRuntimeDecorator(StringSearchStrategy strategy, StringBuilder decoratedText){
        super("");
        this.strategy = strategy;
        this.decoratedText = decoratedText;
    }
    
    public int find(String content, int begin){
        long startTime = System.nanoTime();
        int answer = strategy.find(content, begin);
        long totalTime = System.nanoTime() - startTime;
        decoratedText.append(totalTime + " ns");
        return answer;
    }
    
}
