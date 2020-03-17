package ru.softwerke.newsapp.api;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface NewsService {
    Set IGNORED_WORDS = Stream.of(
            "а", "за", "из", "на", "под", "с", "о", "без", "до", "к", "по", "от",
            "перед", "при", "через", "у", "над", "об", "про", "для", "в", "и"

    ).collect(Collectors.toCollection(HashSet::new));

    List<Map.Entry<String, Integer>> getTopWords();
}
