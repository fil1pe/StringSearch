package pra.stringsearch;

/**
 * A run-time decorator for string-searching strategies
 */
public class SSRunTimeDecorator extends StringSearchStrategy {

    private final StringSearchStrategy strategy;
    private final StringBuilder decoratedText;

    public SSRunTimeDecorator(StringSearchStrategy strategy, StringBuilder decoratedText) {
        super("");
        this.strategy = strategy;
        this.decoratedText = decoratedText;
    }

    public int find(String content, int begin) {
        long startTime = System.nanoTime();
        int answer = strategy.find(content, begin);
        long totalTime = System.nanoTime() - startTime;
        decoratedText.append(totalTime + " ns");
        return answer;
    }

}
