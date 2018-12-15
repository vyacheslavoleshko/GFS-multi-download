package download;

import dto.SpatialExtent;
import enumeration.*;
import lombok.extern.slf4j.Slf4j;
import metadata.GfsMetaDataWriter;
import url_builder.GfsUrlBuilder;
import util.NetUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static util.GfsUrlUtil.FORECAST_HOUR_REGEX;
import static util.GfsUrlUtil.getForecastHourFromString;


/**
 * Class containing main API to download GFS data programmatically
 *
 * @author Viacheslav Oleshko
 */
@Slf4j
public class GfsDownloader {
    private static final int NUMBER_OF_THREADS = 5;
    private static final Pattern PATTERN = Pattern.compile(FORECAST_HOUR_REGEX);
    public static final String METADATA_POSTFIX = "_meta.txt";

    /**
     * Method to download GFS data according to the input parameters
     *
     * @param productName    - product name from ProductName enum
     * @param dateOfForecast - date when the forecast was computed
     * @param modelRunCycle  - model run cycle from ModelRunCycle enum
     * @param forecastHours  - list of forecast hours. One forecast grib2 file will be downloaded per one specific forecast hour
     * @param levels         - list of height levels to download forecasts for them
     * @param variables      - list of variables to download forecasts for them
     * @param spatialExtent  - spatial extent to download forecasts for it. Consists of leftlon, rightlon, toplat, bottomlat
     * @param rootDirectory  - root directory to store downloaded grib2 files. After downloading new directory with
     *                        downloaded data will be created automatically
     * @throws IOException          - in case of input/output exception
     * @throws InterruptedException - in case of thread interruption
     */
    public void download(ProductName productName,
                         LocalDate dateOfForecast,
                         ModelRunCycle modelRunCycle,
                         List<ForecastHour> forecastHours,
                         List<Level> levels,
                         List<Variable> variables,
                         SpatialExtent spatialExtent,
                         Path rootDirectory) throws IOException, InterruptedException {
        download(rootDirectory, new GfsUrlBuilder()
                .forProductName(productName)
                .forDate(dateOfForecast)
                .forModelRunCycle(modelRunCycle)
                .forHoursOfForecast(forecastHours)
                .forLevels(levels)
                .forVariables(variables)
                .forSubRegion(spatialExtent));
    }

    /**
     * Method to download GFS data from the latest available directory on server according to the input parameters
     *
     * @param productName    - product name from ProductName enum
     * @param forecastHours  - list of forecast hours. One forecast grib2 file will be downloaded per one specific forecast hour
     * @param levels         - list of height levels to download forecasts for them
     * @param variables      - list of variables to download forecasts for them
     * @param spatialExtent  - spatial extent to download forecasts for it. Consists of leftlon, rightlon, toplat, bottomlat
     * @param rootDirectory  - root directory to store downloaded grib2 files. After downloading new directory with
     *                        downloaded data will be created automatically
     * @throws IOException          - in case of input/output exception
     * @throws InterruptedException - in case of thread interruption
     */
    public void downloadLatest(ProductName productName,
                               List<ForecastHour> forecastHours,
                               List<Level> levels,
                               List<Variable> variables,
                               SpatialExtent spatialExtent,
                               Path rootDirectory) throws IOException, InterruptedException {
        download(rootDirectory, new GfsUrlBuilder()
                .forProductName(productName)
                .forHoursOfForecast(forecastHours)
                .forLevels(levels)
                .forVariables(variables)
                .forSubRegion(spatialExtent));
    }

    /**
     * Method to download GFS data from the latest available directory on server. All forecast grib2 files
     * for specified product will be downloaded from this directory
     *
     * @param productName   - product name from ProductName enum
     * @param rootDirectory - root directory to store downloaded grib2 files. After downloading new directory with
     *                        downloaded data will be created automatically
     * @throws IOException          - in case of input/output exception
     * @throws InterruptedException - in case of thread interruption
     */
    public void downloadLatest(ProductName productName, Path rootDirectory) throws IOException, InterruptedException {
        download(rootDirectory, new GfsUrlBuilder().forProductName(productName));
    }

    private void download(Path rootDirectory, GfsUrlBuilder urlBuilder) throws InterruptedException, IOException {
        log.info("===Downloading files...===");
        String productName = urlBuilder.getProductName().asString();
        Path directory = rootDirectory.resolve(urlBuilder.getDateOfForecast() + urlBuilder.getModelRunCycle());
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        List<URL> urlList = urlBuilder.buildUrls();
        for (URL url : urlList) {
            String fileName = productName
                    + "_"
                    + urlBuilder.getDateOfForecast()
                    + "_"
                    + urlBuilder.getModelRunCycle()
                    + getForecastHourFromString(url.toString(), PATTERN) + ".grb2";
            Path destinationFile = directory.resolve(fileName);

            executorService.execute(() -> {
                try {
                    downloadFromUrl(url, destinationFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        GfsMetaDataWriter metaDataWriter = new GfsMetaDataWriter(urlBuilder);
        metaDataWriter.writeMetaData(directory.resolve(productName + METADATA_POSTFIX).toFile());
    }

    private void downloadFromUrl(URL url, Path destinationFile) throws IOException {
        log.debug("Starting to download {}", url.toString());
        NetUtils.download(url, destinationFile);
        log.info("{}: Successfully downloaded!", getForecastHourFromString(url.toString(), PATTERN));
    }

}



