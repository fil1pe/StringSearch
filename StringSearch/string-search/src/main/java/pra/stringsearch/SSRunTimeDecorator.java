package pra.stringsearch;

import java.util.ArrayList;

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

    @Override
    public ArrayList<Integer> find(String content) {
        long startTime = System.nanoTime();
        ArrayList<Integer> answer = strategy.find(content);
        long totalTime = System.nanoTime() - startTime;
        decoratedText.append(totalTime + " ns");
        return answer;
    }

}
