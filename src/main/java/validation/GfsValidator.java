package validation;

import dto.SpatialExtent;
import enumeration.ForecastHour;
import enumeration.ProductName;
import exception.NoSuchDateOfForecastException;
import exception.NoSuchModelRunCycleException;
import exception.WrongSpatialExtentException;
import lombok.extern.slf4j.Slf4j;
import parser.GfsHtmlParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static util.GfsUrlUtil.*;

/**
 * @author Viacheslav Oleshko
 */
@Slf4j
public class GfsValidator {
    private GfsHtmlParser parser = new GfsHtmlParser();

    public void validateInputParameters(ProductName productName,
                                        String dateOfForecast,
                                        String modelRunCycle,
                                        SpatialExtent spatialExtent) throws IOException{
        String serverDirectoriesUrl = getServerDirectoriesUrl(productName.asString());
        String datasetName = getDatasetNameFromProductName(productName.asString());
        List<String> serverDirectories = parser.parseServerDirectoriesNamesFromUrl(new URL(serverDirectoriesUrl));

        log.info("===Validate input parameters...===");
        validateDateOfForecast(serverDirectories, dateOfForecast);
        validateModelCycle(serverDirectories, datasetName, dateOfForecast, modelRunCycle);
        validateSpatialExtent(spatialExtent);
    }

    public List<ForecastHour> correctForecastHoursList(List<ForecastHour> inputForecastHours,
                                                       String productName,
                                                       String dateOfForecast,
                                                       String modelRunCycle) throws MalformedURLException {
        log.info("Validating forecast hours list...");
        String serverFilesUrl = getServerFilesUrl(productName, dateOfForecast, modelRunCycle);
        Set<ForecastHour> availableForecastHours = new HashSet<>(parser.parseForecastHoursFromUrl(new URL(serverFilesUrl)));

        List<ForecastHour> correctedList = new ArrayList<>();
        inputForecastHours.forEach(hour -> {
            if (!availableForecastHours.contains(hour)) {
                log.warn("Wrong input: {} product doesn't include forecast for {} hour. It will be skipped!"
                        , productName, hour.asString());
            } else {
                correctedList.add(hour);
            }
        });
        return correctedList;
    }

    private void validateDateOfForecast(List<String> directoryNames, String dateOfForecast) {
        log.info("Validating date of forecast...");
        if (!forecastForDateIsAvailableForDownload(directoryNames, dateOfForecast)) {
            throw new NoSuchDateOfForecastException(
                    String.format(
                            "Forecasts on date %s are not available for download on server", dateOfForecast));
        }
    }

    private void validateModelCycle(List<String> directoryNames,
                                    String datasetName,
                                    String dateOfForecast,
                                    String modelRunCycle) {
        if (!forecastForModelRunCycleIsAvailableForDownload(directoryNames, datasetName, dateOfForecast, modelRunCycle)) {
            log.info("Validating model run cycle...");
            throw new NoSuchModelRunCycleException(
                    String.format("Model run cycle %s is not available for download on date %s",
                            modelRunCycle,
                            dateOfForecast));
        }
    }

    private void validateSpatialExtent(SpatialExtent spatialExtent) {
        log.info("Validating spatial extent...");
        if (spatialExtent.getLeftlon() > spatialExtent.getRightlon()) {
            throw new WrongSpatialExtentException("Wrong input: West longitude > East longitude");
        }
        if (spatialExtent.getBottomlat() > spatialExtent.getToplat()) {
            throw new WrongSpatialExtentException("Wrong input: South latitude > North latitude");
        }
    }

    private boolean forecastForDateIsAvailableForDownload(List<String> directoryNames, String dateOfForecast) {
        return directoryNames.stream().anyMatch(directory -> directory.contains(dateOfForecast));
    }

    private boolean forecastForModelRunCycleIsAvailableForDownload(List<String> directoryNames,
                                                                   String datasetName,
                                                                   String dateOfForecast,
                                                                   String modelRunCycle) {
        return directoryNames.stream()
                .anyMatch(directory -> directory.equals(datasetName + "." + dateOfForecast + modelRunCycle));
    }

}
