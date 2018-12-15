package util;

import enumeration.ForecastHour;
import enumeration.ProductName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Viacheslav Oleshko.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GfsUrlUtil {
    private static final String SERVER_NAME = "http://nomads.ncep.noaa.gov/";
    private static final String CGI_BIN = "cgi-bin/";
    private static final String FILTER_PREFIX = "filter_";
    private static final String FILTER_POSTFIX = ".pl";
    private static final String DIR_PREFIX = "dir=%2F";
    public static final String FORECAST_HOUR_REGEX = ".f\\d{3}";

    public static String getDatasetNameFromProductName(String productName) {
        return productName.split("_")[0];
    }

    public static String getProductNamePostfix(ProductName productName) {
        return productName.asString().split("_")[1];
    }

    public static String getFullFileName(ProductName productName, String modelRunCycle, ForecastHour hourOfForecast) {
        return new StringBuilder()
                .append(getDatasetNameFromProductName(productName.asString()))
                .append(".t")
                .append(modelRunCycle)
                .append(productName == ProductName.GFS_050_DEGREE ? "z.pgrb2full." : "z.pgrb2.")
                .append(getProductNamePostfix(productName))
                .append(".f")
                .append(hourOfForecast.asString())
                .toString();
    }

    public static String getServerDirectoriesUrl(String productName) {
        return new StringBuilder().append(SERVER_NAME)
                .append(CGI_BIN)
                .append(FILTER_PREFIX)
                .append(productName)
                .append(FILTER_POSTFIX)
                .toString();
    }

    public static String getServerFilesUrl(String productName, String dateOfForecast, String modelRunCycle) {
        return new StringBuilder()
                .append(getServerDirectoriesUrl(productName))
                .append("?")
                .append(getDirNamePartOfUrl(productName, dateOfForecast, modelRunCycle))
                .toString();
    }

    public static String getForecastDateFromDirectoryName(String directoryName) {
        return directoryName.substring(directoryName.indexOf(".") + 1, directoryName.length() - 2);
    }

    public static String getModelRunCycleFromDirectoryName(String directoryName) {
        return directoryName.substring(directoryName.length() - 2, directoryName.length());
    }

    public static String getForecastHourFromString(String str, Pattern pattern) {
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(0);
        }
        throw new RuntimeException(String.format("Error occurred while trying to parse string %s: " +
                "no forecast hour could be retrieved with pattern '.fd{3}'", str));
    }

    public static boolean stringMatches(String str, Pattern pattern) {
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    public static String getDirNamePartOfUrl(String productName, String dateOfForecast, String modelRunCycle) {
        return new StringBuilder().append(DIR_PREFIX)
                .append(getDatasetNameFromProductName(productName))
                .append(".")
                .append(dateOfForecast)
                .append(modelRunCycle)
                .toString();
    }

}
