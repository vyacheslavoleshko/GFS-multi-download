package url_builder;

import dto.SpatialExtent;
import enumeration.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import parser.GfsHtmlParser;
import util.GfsUrlUtil;
import validation.GfsValidator;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.GfsUrlUtil.*;

/**
 * Class is intended to provide bunch of URLs for downloading GFS data (one URL per specified forecast hour).
 *
 * @author Viacheslav Oleshko.
 */
@Getter
@Slf4j
public class GfsUrlBuilder {
    private GfsValidator validator = new GfsValidator();
    private GfsHtmlParser parser = new GfsHtmlParser();

    private ProductName productName = ProductName.GFS_025_DEGREE;
    private String serverDirectoriesUrl = GfsUrlUtil.getServerDirectoriesUrl(productName.asString());
    private String latestForecastDirectory = parser.getServerDirectoryNameForLatestForecast(new URL(serverDirectoriesUrl));
    private String dateOfForecast = getForecastDateFromDirectoryName(latestForecastDirectory);
    private String modelRunCycle = getModelRunCycleFromDirectoryName(latestForecastDirectory);
    private String filesDirectoryUrl = getServerFilesUrl(productName.asString(), dateOfForecast, modelRunCycle);
    private List<ForecastHour> forecastHours = parser.parseForecastHoursFromUrl(new URL(filesDirectoryUrl));
    private List<Level> levels = Collections.singletonList(Level.ALL);
    private List<Variable> variables = Collections.singletonList(Variable.ALL);
    private SpatialExtent spatialExtent = new SpatialExtent(0, 360, 90, -90);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public GfsUrlBuilder() throws IOException {
    }

    public GfsUrlBuilder forProductName(ProductName productName) {
        this.productName = productName;
        return this;
    }

    public GfsUrlBuilder forDate(LocalDate dateOfForecast) {
        this.dateOfForecast = formatter.format(dateOfForecast);
        return this;
    }

    public GfsUrlBuilder forModelRunCycle(ModelRunCycle modelRunCycle) {
        this.modelRunCycle = modelRunCycle.asString();
        return this;
    }

    public GfsUrlBuilder forHoursOfForecast(List<ForecastHour> forecastHours) {
        this.forecastHours = forecastHours;
        return this;
    }

    public GfsUrlBuilder forLevels(List<Level> levels) {
        this.levels = levels;
        return this;
    }

    public GfsUrlBuilder forVariables(List<Variable> variables) {
        this.variables = variables;
        return this;
    }

    public GfsUrlBuilder forSubRegion(SpatialExtent spatialExtent) {
        this.spatialExtent = spatialExtent;
        return this;
    }

    public List<URL> buildUrls() throws IOException {
        validator.validateInputParameters(productName, dateOfForecast, modelRunCycle, spatialExtent);
        List<ForecastHour> correctedForecastHours = validator.correctForecastHoursList(
                forecastHours, productName.asString(), dateOfForecast, modelRunCycle);

        log.info("===Building URLs...===");
        log.info("Parameters: \nProduct={}\nDate={}\nModel Run Cycle={}\nSpatial Extent=({}, {}, {}, {})\nForecast hours={}" +
                        "\nLevels={}\nVariables={}",
                productName.asString(), dateOfForecast, modelRunCycle, spatialExtent.getLeftlon(), spatialExtent.getRightlon(),
                spatialExtent.getToplat(), spatialExtent.getBottomlat(), correctedForecastHours, levels, variables);
        List<URL> urlList = new ArrayList<>();
        for (ForecastHour forecastHour : correctedForecastHours) {
            GfsUrlStringBuilder gfsStringBuilder = new GfsUrlStringBuilder();

            String urlAsString = gfsStringBuilder.buildUrlForForecastHour(productName,
                    dateOfForecast, modelRunCycle, forecastHour, levels, variables, spatialExtent);
            log.debug("{} url was built", urlAsString);
            URL urlForDownload = new URL(urlAsString);
            urlList.add(urlForDownload);
        }
        return urlList;
    }

}
