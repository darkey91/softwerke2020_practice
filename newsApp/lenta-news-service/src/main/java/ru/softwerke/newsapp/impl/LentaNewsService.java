package ru.softwerke.newsapp.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.softwerke.newsapp.abst.AbstractNewsService;
import ru.softwerke.newsapp.api.NewsService;

import java.io.InputStream;
import java.util.*;

@SuppressWarnings("deprecation")
@Component(name = "Lenta News Service")
@Service(value = NewsService.class)
@Property(name = "source", value = "lenta")
public class LentaNewsService extends AbstractNewsService implements NewsService {
    private static final String API_URL = "https://api.lenta.ru/lists/latest";
    private static final int MAX_NUMBER = 10;

    private String readUrl(InputStream ins) {
        return new Scanner(ins, "UTF-8").useDelimiter("\\Z").next();
    }

    @Override
    protected Map<String, Integer> evaluateStatistic(InputStream ins) {
        Map<String, Integer> statistic = new HashMap<>();

        try {
            JSONObject jsonObj = new JSONObject(readUrl(ins));
            JSONArray news = jsonObj.getJSONArray("headlines");

            assert news != null;
            assert news.length() != 0;

            for (int i = 0; i < news.length(); ++i) {
                JSONObject curNews = news.getJSONObject(i);
                if (curNews != null) {
                    try {
                        String title = (String) curNews.getJSONObject("info").get("title");
                        Arrays.stream(title.split("[\\p{Punct}\\s]+"))
                                .map(String::toLowerCase)
                                .filter(w -> !IGNORED_WORDS.contains(w))
                                .forEach(w -> {
                                    Integer prevValue = statistic.get(w);
                                    if (prevValue == null) {
                                        prevValue = 0;
                                    }
                                    statistic.put(w, prevValue + 1);
                                });
                    } catch (NullPointerException e) {
                        System.out.println("NPE in " + this.getClass().getName() + ": In some cases some fields don't exist.. ");
                    } catch (JSONException e) {
                        System.out.println("Can't read content of news[" + i + "].");
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("Can't read headlines in " + this.getClass().getName());
        }
        return statistic;
    }


    @Override
    public List<Map.Entry<String, Integer>> getTopWords() {
        System.out.println("Thanks for using Lenta api!");
        return getTopWords(API_URL, MAX_NUMBER);
    }
}
