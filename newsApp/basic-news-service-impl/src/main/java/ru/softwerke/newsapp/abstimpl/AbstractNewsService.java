package ru.softwerke.newsapp.abstimpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractNewsService {

    protected abstract void evaluateStatistic(InputStream ins);

    protected abstract Map<String, Integer> getWordsStatistic();

    private void process(String apiUrl) {
        try {
            try (InputStream ins = new URL(apiUrl).openStream()) {
                evaluateStatistic(ins);
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
