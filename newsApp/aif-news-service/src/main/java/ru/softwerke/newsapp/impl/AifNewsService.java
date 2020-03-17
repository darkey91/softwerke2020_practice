package ru.softwerke.newsapp.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.softwerke.newsapp.abstimpl.AbstractNewsService;
import ru.softwerke.newsapp.api.NewsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings({"deprecation"})
@Component(name = "Lenta News Service")
@Service(value = NewsService.class)
@Property(name = "source", value = "aif")
public class AifNewsService extends AbstractNewsService implements NewsService {
    private static final String API_URL = "http://www.aif.ru/rss/news.php";
    private final Map<String, Integer> words = new HashMap<>();
    private static final int MAX_NUMBER = 10;

    @Override
    protected void evaluateStatistic(InputStream ins) {
        try {
            Document document = Jsoup.connect(API_URL).get();

            final Elements titles = new Elements();
            document.getElementsByTag("channel")
                    .forEach(n -> titles.addAll(n.getElementsByTag("title")));

            for (Element curNode : titles) {
                if (curNode != null) {
                    Arrays.stream(curNode.text().split("[\\p{Punct}\\s]+"))
                            .map(String::toLowerCase)
                            .filter(w -> !IGNORED_WORDS.contains(w))
                            .forEach(w -> {
                                Integer prevValue = words.get(w);
                                if (prevValue == null) {
                                    prevValue = 0;
                                }
                                words.put(w, prevValue + 1);
                            });

                }
            }
        } catch (IOException e) {
            System.out.println("Can't read from " + API_URL + " in " + this.getClass().getName());
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, Integer> getWordsStatistic() {
        return words;
    }


    @Override
    public List<Map.Entry<String, Integer>> getTopWords() {
        System.out.println("Thanks for using AiF api!");
        return getTopWords(API_URL, MAX_NUMBER);
    }
}
