package parser;

import enumeration.ForecastHour;
import org.junit.Test;
import url_builder.GfsUrlBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static util.GfsUrlUtil.*;

/**
 * @author Viacheslav Oleshko.
 */
public class GfsHtmlParserTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private GfsHtmlParser parser = new GfsHtmlParser();
    private GfsUrlBuilder builder;

    public GfsHtmlParserTest() throws IOException {
        builder = new GfsUrlBuilder().forDate(LocalDate.now().minusDays(2));
    }

    @Test
    public void testParseForecastHoursFromUrl() throws MalformedURLException {
        List<ForecastHour> forecastHours = parser.parseForecastHoursFromUrl(
                new URL(getServerFilesUrl(
                        builder.getProductName().asString(),
                        builder.getDateOfForecast(),
                        builder.getModelRunCycle())));
        assertEquals(Arrays.stream(ForecastHour.values()).count(), forecastHours.size());
        assertEquals(Arrays.asList(ForecastHour.values()), forecastHours);
    }

    @Test
    public void testParseServerDirectoriesNamesFromUrl() throws IOException {
        List<String> dirNames = parser.parseServerDirectoriesNamesFromUrl(
                new URL(getServerDirectoriesUrl(builder.getProductName().asString())));
        assertEquals(40, dirNames.size());
    }

    @Test
    public void testGetServerDirectoryNameForLatestForecast() throws IOException {
        String latestDirName = parser.getServerDirectoryNameForLatestForecast(
                new URL(getServerDirectoriesUrl(builder.getProductName().asString())));

        LocalDate latestForecastDate = LocalDate.parse(getForecastDateFromDirectoryName(latestDirName), formatter);
        LocalDate now = LocalDate.now();
        assertTrue(latestForecastDate.isBefore(now) || latestForecastDate.isEqual(now));
    }
}
