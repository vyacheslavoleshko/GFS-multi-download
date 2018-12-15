package parser;

import enumeration.ForecastHour;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.NetUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static util.GfsUrlUtil.getForecastHourFromString;
import static util.GfsUrlUtil.stringMatches;

/**
 * @author Viacheslav Oleshko.
 */
public class GfsHtmlParser {
    private static final Pattern PATTERN = Pattern.compile("\\d{3}");

    public List<ForecastHour> parseForecastHoursFromUrl(URL serverFilesUrl)  {
        String html = NetUtils.read(serverFilesUrl);

        Document doc = Jsoup.parse(html);
        Elements options = doc.getElementsByTag("option");
        return options
                .stream()
                .map(Element::val)
                .filter(fileName -> stringMatches(fileName, PATTERN))
                .map(fileName -> getForecastHourFromString(fileName, PATTERN))
                .map(ForecastHour::fromString)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public List<String> parseServerDirectoriesNamesFromUrl(URL serverDirectoriesUrl) throws IOException {
        String html = NetUtils.read(serverDirectoriesUrl);

        Document doc = Jsoup.parse(html);
        Elements links = doc.getElementsByTag("a");
        return links
                .stream()
                .map(Element::text)
                .collect(toList());
    }

    public String getServerDirectoryNameForLatestForecast(URL serverDirectoriesUrl) throws IOException {
        return parseServerDirectoriesNamesFromUrl(serverDirectoriesUrl).get(0);
    }

}
