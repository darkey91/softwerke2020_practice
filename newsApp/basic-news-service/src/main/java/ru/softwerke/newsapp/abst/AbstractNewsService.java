package ru.softwerke.newsapp.abst;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractNewsService {
    protected final Map<String, Integer> words = new HashMap<>();

    protected abstract Map<String, Integer> evaluateStatistic(InputStream ins);

    private Map<String, Integer> getWordsStatistic() {
        return words;
    }

    private void process(String apiUrl) {
        try {
            try (InputStream ins = new URL(apiUrl).openStream()) {
                words.putAll(evaluateStatistic(ins));
            }
        } catch (IOException e) {
            System.err.println("Can't open connection in " + this.getClass().getName());
            e.printStackTrace();
        }
    }

    protected List<Map.Entry<String, Integer>> getTopWords(String apiUrl, Integer maxNumber) {
        process(apiUrl);
        return getWordsStatistic().entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(maxNumber)
                .collect(Collectors.toList());
    }
}
