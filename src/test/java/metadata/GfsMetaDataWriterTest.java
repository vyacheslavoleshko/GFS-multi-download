package metadata;

import dto.SpatialExtent;
import enumeration.ModelRunCycle;
import enumeration.ProductName;
import enumeration.Variable;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;
import url_builder.GfsUrlBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static enumeration.ForecastHour.HOUR_0;
import static enumeration.ForecastHour.HOUR_3;
import static enumeration.Level.ALL;

/**
 * @author Viacheslav Oleshko.
 */
public class GfsMetaDataWriterTest {
    private static final File ACTUAL_FILE = new File("target/metadata.txt");

    @After
    public void tearDown() {
        ACTUAL_FILE.delete();
    }

    @Test
    public void writeMetaData_successfulTest() throws IOException {
        GfsMetaDataWriter wr = new GfsMetaDataWriter(new GfsUrlBuilder()
                .forProductName(ProductName.GFS_1_DEGREE)
                .forDate(LocalDate.of(2018, 6, 10))
                .forModelRunCycle(ModelRunCycle.MIDNIGHT)
                .forHoursOfForecast(Arrays.asList(HOUR_0, HOUR_3))
                .forLevels(Collections.singletonList(ALL))
                .forVariables(Arrays.asList(Variable.TMP, Variable.LFTX4))
                .forSubRegion(new SpatialExtent(12, 24, 36, 12))
        );
        wr.writeMetaData(ACTUAL_FILE);

        File EXPECTED_FILE = new File("src/test/resources/expected-files/gfs-meta-data-writer/gfsDownloader-expected-report.txt");
        FileUtils.contentEquals(EXPECTED_FILE, ACTUAL_FILE);
    }
}
