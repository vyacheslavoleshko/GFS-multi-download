package url_builder;

import dto.SpatialExtent;
import enumeration.Level;
import enumeration.ProductName;
import enumeration.Variable;
import org.junit.Before;
import org.junit.Test;
import parser.GfsHtmlParser;
import util.GfsUrlUtil;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static enumeration.ForecastHour.*;
import static enumeration.Level.LEV_450_MB;
import static enumeration.Level.LEV_5_MB;
import static enumeration.ModelRunCycle.NOON;
import static enumeration.ModelRunCycle.SIX_AM;
import static enumeration.ProductName.GFS_050_DEGREE;
import static enumeration.ProductName.GFS_1_DEGREE;
import static enumeration.Variable.LFTX4;
import static enumeration.Variable.TMP;
import static org.junit.Assert.*;
import static util.GfsUrlUtil.getForecastDateFromDirectoryName;
import static util.GfsUrlUtil.getModelRunCycleFromDirectoryName;

/**
 * @author Viacheslav Oleshko.
 */
public class GfsUrlBuilderTest {
    private GfsUrlBuilder gfsUrlBuilder;
    private String latestForecastDirectory;
    private ProductName defaultProductName = ProductName.GFS_025_DEGREE;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private LocalDate testDate = LocalDate.now().minusDays(2);
    private String sTestDate = formatter.format(testDate);

    public GfsUrlBuilderTest() throws IOException {
        this.latestForecastDirectory = getLatestForecastDate(defaultProductName);
    }

    @Before
    public void init() throws IOException {
        gfsUrlBuilder = new GfsUrlBuilder();
    }

    @Test
    public void testDefaultValues() throws IOException {
        assertEquals(defaultProductName, gfsUrlBuilder.getProductName());
        assertEquals(getForecastDateFromDirectoryName(latestForecastDirectory), gfsUrlBuilder.getDateOfForecast());
        assertEquals(getModelRunCycleFromDirectoryName(latestForecastDirectory), gfsUrlBuilder.getModelRunCycle());
        assertTrue(gfsUrlBuilder.getForecastHours().size() > 0);
        assertEquals(Level.ALL, gfsUrlBuilder.getLevels().get(0));
        assertEquals(Variable.ALL, gfsUrlBuilder.getVariables().get(0));
        assertEquals(new SpatialExtent(0, 360, 90, -90), gfsUrlBuilder.getSpatialExtent());
    }

    @Test
    public void testBuildUrls_GFS_1_DEGREE() throws IOException {
        gfsUrlBuilder
                .forProductName(GFS_1_DEGREE)
                .forDate(testDate)
                .forModelRunCycle(NOON)
                .forHoursOfForecast(Arrays.asList(HOUR_0, HOUR_3, HOUR_384))
                .forLevels(Collections.singletonList(Level.ALL))
                .forVariables(Collections.singletonList(TMP))
                .forSubRegion(new SpatialExtent(0, 10, 10, 5));

        List<URL> urls = gfsUrlBuilder.buildUrls();
        assertNotNull(urls);
        assertTrue(urls.size() == 3);
        String url_0_hour = "http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t12z.pgrb2.1p00.f000" +
                "&all_lev=on&var_TMP=on&subregion=&leftlon=0&rightlon=10&toplat=10&bottomlat=5&dir=%2Fgfs." + sTestDate
                + NOON.asString();
        String url_3_hour = "http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t12z.pgrb2.1p00.f003" +
                "&all_lev=on&var_TMP=on&subregion=&leftlon=0&rightlon=10&toplat=10&bottomlat=5&dir=%2Fgfs." + sTestDate
                + NOON.asString();
        String url_384_hour = "http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t12z.pgrb2.1p00.f384" +
                "&all_lev=on&var_TMP=on&subregion=&leftlon=0&rightlon=10&toplat=10&bottomlat=5&dir=%2Fgfs." + sTestDate
                + NOON.asString();
        assertEquals(Arrays.asList(new URL(url_0_hour), new URL(url_3_hour), new URL(url_384_hour)), urls);
    }

    @Test
    public void testBuildUrls_GFS_050_DEGREE() throws IOException {
        gfsUrlBuilder
                .forProductName(GFS_050_DEGREE)
                .forDate(testDate)
                .forModelRunCycle(SIX_AM)
                .forHoursOfForecast(Collections.singletonList(HOUR_0))
                .forLevels(Arrays.asList(LEV_5_MB, LEV_450_MB))
                .forVariables(Arrays.asList(LFTX4, TMP))
                .forSubRegion(new SpatialExtent(10, 20, 30, 10));

        List<URL> urls = gfsUrlBuilder.buildUrls();
        assertNotNull(urls);
        assertTrue(urls.size() == 1);
        String url_0_hour = "http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_0p50.pl?file=gfs.t06z.pgrb2full.0p50.f000" +
                "&lev_5_mb=on&lev_450_mb=on&var_4LFTX=on&var_TMP=on&subregion=&leftlon=10&rightlon=20&toplat=30&bottomlat=10" +
                "&dir=%2Fgfs." + sTestDate + SIX_AM.asString();
        assertEquals(Collections.singletonList(new URL(url_0_hour)), urls);
    }
    private String getLatestForecastDate(ProductName productName) throws IOException {
        GfsHtmlParser parser = new GfsHtmlParser();
        String serverDirectoriesUrl = GfsUrlUtil.getServerDirectoriesUrl(productName.asString());
        return latestForecastDirectory = parser.getServerDirectoryNameForLatestForecast(new URL(serverDirectoriesUrl));
    }

}
