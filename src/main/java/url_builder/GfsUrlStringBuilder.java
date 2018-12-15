package url_builder;

import dto.SpatialExtent;
import enumeration.ForecastHour;
import enumeration.Level;
import enumeration.ProductName;
import enumeration.Variable;

import java.util.List;

import static util.GfsUrlUtil.*;

/**
 * Class is intended to build URL for downloading grib2 file based on input parameters.
 *
 * @author Viacheslav Oleshko.
 */
public class GfsUrlStringBuilder {
    private static final String FILE_PREFIX = "file=";
    private static final String ENABLE = "=on";

    private StringBuilder url = new StringBuilder();

    private GfsUrlStringBuilder folderName(ProductName productName) {
        url.append(getServerDirectoriesUrl(productName.asString()));
        return this;
    }

    private GfsUrlStringBuilder fileName(ProductName productName,
                                         String modelRunCycle,
                                         ForecastHour forecastHour) {
        url.append("?")
                .append(FILE_PREFIX)
                .append(getFullFileName(productName, modelRunCycle, forecastHour))
                .append("&");
        return this;
    }

    private GfsUrlStringBuilder levels(List<Level> levels) {
        levels.forEach(level -> {
            url.append(level.asString())
                    .append(ENABLE)
                    .append("&");
        });
        return this;
    }

    private GfsUrlStringBuilder variables(List<Variable> variables) {
        variables.forEach(var -> {
            url.append(var.asString())
                    .append(ENABLE)
                    .append("&");
        });
        return this;
    }

    private GfsUrlStringBuilder spatialExtent(SpatialExtent spatialExtent) {
        url.append(spatialExtent.asString());
        return this;
    }

    private GfsUrlStringBuilder dirName(ProductName productName,
                                        String dateOfForecast,
                                        String modelRunCycle) {
        url.append(getDirNamePartOfUrl(productName.asString(), dateOfForecast, modelRunCycle));
        return this;
    }

    String buildUrlForForecastHour(ProductName productName,
                                          String dateOfForecast,
                                          String modelRunCycle,
                                          ForecastHour forecastHour,
                                          List<Level> levels,
                                          List<Variable> variables,
                                          SpatialExtent spatialExtent) {
        this.folderName(productName)
                .fileName(productName, modelRunCycle, forecastHour)
                .levels(levels)
                .variables(variables)
                .spatialExtent(spatialExtent)
                .dirName(productName, dateOfForecast, modelRunCycle);
        return this.url.toString();
    }

}
