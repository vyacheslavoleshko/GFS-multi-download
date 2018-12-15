package validation;


import dto.SpatialExtent;
import enumeration.ForecastHour;
import enumeration.ProductName;
import exception.NoSuchDateOfForecastException;
import exception.NoSuchModelRunCycleException;
import exception.WrongSpatialExtentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static enumeration.ForecastHour.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Viacheslav Oleshko
 */
@RunWith(MockitoJUnitRunner.class)
public class GfsValidatorTest {
    private GfsValidator validator = new GfsValidator();;

    @Mock
    private SpatialExtent spatialExtent;

    @Mock
    private List<ForecastHour> forecastHours;

    private DateTimeFormatter formatter_yyyymmdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String testDate = formatter_yyyymmdd.format(LocalDate.now().minusDays(2));


    @Test(expected = NoSuchDateOfForecastException.class)
    public void test_validateInputParameters_noSuchDateOfForecast() throws IOException {
        validator.validateInputParameters(ProductName.GFS_050_DEGREE, "20100101", "00", spatialExtent);
    }

    @Test(expected = NoSuchModelRunCycleException.class)
    public void test_validateInputParameters_noSuchModelRunCycle() throws IOException {
        validator.validateInputParameters(ProductName.GFS_050_DEGREE, testDate,"01", spatialExtent);
    }

    @Test(expected = WrongSpatialExtentException.class)
    public void test_validateInputParameters_wrongSpatialExtent_wrongLon() throws IOException {
        SpatialExtent spatialExtent = new SpatialExtent(10, 0, 10, 0);
        validator.validateInputParameters(ProductName.GFS_050_DEGREE, testDate,"00", spatialExtent);
    }

    @Test(expected = WrongSpatialExtentException.class)
    public void test_validateInputParameters_wrongSpatialExtent_wrongLat() throws IOException {
        SpatialExtent spatialExtent = new SpatialExtent(0, 10, 0, 10);
        validator.validateInputParameters(ProductName.GFS_050_DEGREE, testDate,"00", spatialExtent);
    }

    @Test
    public void test_validateInputParameters_successfulCase() throws IOException {
        validator.validateInputParameters(ProductName.GFS_050_DEGREE, testDate,"00", spatialExtent);
    }

    @Test
    public void test_correctForecastHoursList_successfulCase() throws IOException {
        List<ForecastHour> forecastHours = Arrays.asList(HOUR_0, HOUR_1, HOUR_2, HOUR_3);
        List<ForecastHour> actualList = validator.correctForecastHoursList(
                forecastHours, ProductName.GFS_1_DEGREE.asString(), testDate, "00");
        List<ForecastHour> expectedList = Arrays.asList(HOUR_0, HOUR_3);
        assertEquals(expectedList, actualList);
    }
}
