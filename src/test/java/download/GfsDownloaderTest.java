package download;

import dto.SpatialExtent;
import enumeration.*;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static download.GfsDownloader.METADATA_POSTFIX;
import static enumeration.ForecastHour.HOUR_0;
import static enumeration.ForecastHour.HOUR_384;
import static enumeration.Level.LEV_1_MB;
import static enumeration.Level.LEV_2_M_ABOVE_GROUND;
import static enumeration.ModelRunCycle.SIX_PM;
import static enumeration.ProductName.GFS_1_DEGREE;
import static enumeration.Variable.RH;
import static enumeration.Variable.TMP;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * @author Viacheslav Oleshko.
 */
public class GfsDownloaderTest {
    private GfsDownloader downloader = new GfsDownloader();

    private static final LocalDate TEST_DATE                = LocalDate.now().minusDays(2);
    private static final ModelRunCycle MODEL_RUN_CYCLE      = SIX_PM;
    private static final List<Level> LEVELS                 = Arrays.asList(LEV_2_M_ABOVE_GROUND, LEV_1_MB);
    private static final List<Variable> VARIABLES           = Arrays.asList(TMP, RH);
    private static final SpatialExtent SPATIAL_EXTENT       = new SpatialExtent(30, 31, 60, 59);

    private static final DateTimeFormatter FORMATTER        = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Path ROOT_PATH_TO_SAVE_FILES       = Paths.get("target");

    private static final String TEST_DATE_AS_STRING = FORMATTER.format(TEST_DATE);
    private static final Path PATH_TO_FILES = ROOT_PATH_TO_SAVE_FILES.resolve(TEST_DATE_AS_STRING + MODEL_RUN_CYCLE.asString());


    @After
    public void deleteTestDir() throws IOException {
        FileUtils.deleteDirectory(PATH_TO_FILES.toFile());
    }

    @Test
    public void testDownload() throws IOException, InterruptedException {
        ProductName productName = GFS_1_DEGREE;
        List<ForecastHour> forecastHours = ForecastHour.getRange(HOUR_0, HOUR_384);

        downloader.download(productName, TEST_DATE, MODEL_RUN_CYCLE, forecastHours, LEVELS, VARIABLES,
                SPATIAL_EXTENT, ROOT_PATH_TO_SAVE_FILES);

        assertTrue(Files.exists(PATH_TO_FILES));
        checkMetaData(productName);
        assertEquals(94, Files.list(PATH_TO_FILES).count());
    }

    private void checkMetaData(ProductName productName) throws IOException {
        Path generatedMetadata = PATH_TO_FILES.resolve(productName.asString() + METADATA_POSTFIX);
        assertTrue(Files.exists(generatedMetadata));
    }

}
