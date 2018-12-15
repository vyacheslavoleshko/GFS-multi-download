package util;

import org.junit.Test;

import java.util.regex.Pattern;

import static enumeration.ForecastHour.HOUR_1;
import static enumeration.ProductName.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GfsUrlUtilTest {

    @Test
    public void test_getDatasetNameFromProductName() {
        String datasetName_025 = GfsUrlUtil.getDatasetNameFromProductName(GFS_025_DEGREE.asString());
        assertEquals("gfs", datasetName_025);

        String datasetName_050 = GfsUrlUtil.getDatasetNameFromProductName(GFS_050_DEGREE.asString());
        assertEquals("gfs", datasetName_050);

        String datasetName_100 = GfsUrlUtil.getDatasetNameFromProductName(GFS_1_DEGREE.asString());
        assertEquals("gfs", datasetName_100);
    }

    @Test
    public void test_getProductNamePostfix() {
        String postfix_025 = GfsUrlUtil.getProductNamePostfix(GFS_025_DEGREE);
        assertEquals("0p25", postfix_025);

        String postfix_050 = GfsUrlUtil.getProductNamePostfix(GFS_050_DEGREE);
        assertEquals("0p50", postfix_050);

        String postfix_100 = GfsUrlUtil.getProductNamePostfix(GFS_1_DEGREE);
        assertEquals("1p00", postfix_100);
    }

    @Test
    public void test_getFullFileName() {
        String fileFullName_025 = GfsUrlUtil.getFullFileName(GFS_025_DEGREE, "00", HOUR_1);
        assertEquals("gfs.t00z.pgrb2.0p25.f001", fileFullName_025);

        String fileFullName_050 = GfsUrlUtil.getFullFileName(GFS_050_DEGREE, "06", HOUR_1);
        assertEquals("gfs.t06z.pgrb2full.0p50.f001", fileFullName_050);

        String fileFullName_100 = GfsUrlUtil.getFullFileName(GFS_1_DEGREE, "12", HOUR_1);
        assertEquals("gfs.t12z.pgrb2.1p00.f001", fileFullName_100);
    }

    @Test
    public void test_getServerDirectoriesUrl() {
        String serverDirectoriesUrl_025 = GfsUrlUtil.getServerDirectoriesUrl(GFS_025_DEGREE.asString());
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_0p25.pl", serverDirectoriesUrl_025);

        String serverDirectoriesUrl_050 = GfsUrlUtil.getServerDirectoriesUrl(GFS_050_DEGREE.asString());
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_0p50.pl", serverDirectoriesUrl_050);

        String serverDirectoriesUrl_100 = GfsUrlUtil.getServerDirectoriesUrl(GFS_1_DEGREE.asString());
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl", serverDirectoriesUrl_100);
    }

    @Test
    public void test_getServerFilesUrl() {
        String serverFilesUrl_025 = GfsUrlUtil.getServerFilesUrl(GFS_025_DEGREE.asString(), "20180605", "00");
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_0p25.pl?dir=%2Fgfs.2018060500", serverFilesUrl_025);

        String serverFilesUrl_050 = GfsUrlUtil.getServerFilesUrl(GFS_050_DEGREE.asString(), "20180605", "12");
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_0p50.pl?dir=%2Fgfs.2018060512", serverFilesUrl_050);

        String serverFilesUrl_100 = GfsUrlUtil.getServerFilesUrl(GFS_1_DEGREE.asString(), "20180606", "18");
        assertEquals("http://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?dir=%2Fgfs.2018060618", serverFilesUrl_100);
    }

    @Test
    public void test_getForecastDateFromDirectoryName() {
        String forecastDate = GfsUrlUtil.getForecastDateFromDirectoryName("2018060500");
        assertEquals("20180605", forecastDate);
    }

    @Test
    public void test_getModelRunCycleFromDirectoryName() {
        String modelRunCycle = GfsUrlUtil.getModelRunCycleFromDirectoryName("2018060500");
        assertEquals("00", modelRunCycle);
    }

    @Test
    public void test_getForecastHourFromString() {
        String modelRunCycle = GfsUrlUtil.getForecastHourFromString(".f000", Pattern.compile(".f\\d{3}"));
        assertEquals(".f000", modelRunCycle);
    }

    @Test
    public void test_stringMatches() {
        assertTrue(GfsUrlUtil.stringMatches(".f000", Pattern.compile("\\d{3}")));
    }

    @Test
    public void test_getDirNamePartOfUrl() {
        String dirName_025 = GfsUrlUtil.getDirNamePartOfUrl(GFS_025_DEGREE.asString(), "20180605", "00");
        assertEquals("dir=%2Fgfs.2018060500", dirName_025);

        String dirName_050 = GfsUrlUtil.getDirNamePartOfUrl(GFS_050_DEGREE.asString(), "20180606", "06");
        assertEquals("dir=%2Fgfs.2018060606", dirName_050);

        String dirName_100 = GfsUrlUtil.getDirNamePartOfUrl(GFS_1_DEGREE.asString(), "20180607", "12");
        assertEquals("dir=%2Fgfs.2018060712", dirName_100);
    }
}
