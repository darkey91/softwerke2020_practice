package ru.softwerke.newsapp.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.softwerke.newsapp.abst.AbstractNewsService;
import ru.softwerke.newsapp.api.NewsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings({"deprecation"})
@Component(name = "Aif News Service")
@Service(value = NewsService.class)
@Property(name = "source", value = "aif")
public class AifNewsService extends AbstractNewsService implements NewsService {
    private static final String API_URL = "http://www.aif.ru/rss/news.php";
    private static final int MAX_NUMBER = 10;

    @Override
    protected Map<String, Integer> evaluateStatistic(InputStream ins) {
        Map<String, Integer> statistic = new HashMap<>();

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
                                Integer prevValue = statistic.get(w);
                                if (prevValue == null) {
                                    prevValue = 0;
                                }
                                statistic.put(w, prevValue + 1);
                            });

                }
            }
        } catch (IOException e) {
            System.out.println("Can't read from " + API_URL + " in " + this.getClass().getName());
            e.printStackTrace();
        }
        return statistic;
    }

    @Override
    public List<Map.Entry<String, Integer>> getTopWords() {
        System.out.println("Thanks for using AiF api!");
        return getTopWords(API_URL, MAX_NUMBER);
    }
}
