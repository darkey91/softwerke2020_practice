package ru.softwerke.newsapp.gogo;

import org.apache.felix.scr.annotations.*;
import org.apache.felix.service.command.Descriptor;
import ru.softwerke.newsapp.api.NewsService;

@SuppressWarnings("deprecation")
@Component(name = "News Statistic Command")
@Service(value = Object.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "news"),
        @Property(name = "osgi.command.function", value = {"stats"})
})
public class NewsStatsCommand {
    private static final String LENTA_SOURCE = "lenta";
    private static final String AIF_SOURCE = "aif";

    @Reference(
            referenceInterface = NewsService.class,
            target = "(source=lenta)"
    )
    private NewsService lentaNewsService;

    @Reference(
            referenceInterface = NewsService.class,
            target = "(source=aif)"
    )
    private NewsService aifNewsService;

    @Descriptor("stats <source> - shows 10 most common headline words news\n" +
            "\tscope: news\n" +
            "\tparameters: String\t one of these sources [ aif, lenta ]")
    public void stats(final String[] source) {
        if (source == null || source.length == 0) {
            System.err.println("Indicate required parameter (source). Use `help news:stats` for more information");
            return;
        }

        switch (source[0].toLowerCase()) {
            case AIF_SOURCE: {
                aifNewsService.getTopWords()
                        .forEach(p -> System.out.println(p.getKey() + " : " + p.getValue() + " occurrences"));
                return;
            }
            case LENTA_SOURCE: {
                lentaNewsService.getTopWords()
                        .forEach(p -> System.out.println(p.getKey() + " : " + p.getValue() + " occurrences"));
                return;
            }
            default:
                System.err.println("Unsupported source. Use `help news:stats` for more information");
        }
    }
}
