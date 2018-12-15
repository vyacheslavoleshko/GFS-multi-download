package metadata;

import enumeration.Variable;
import lombok.extern.slf4j.Slf4j;
import url_builder.GfsUrlBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static enumeration.Level.ALL;

/**
 * @author Viacheslav Oleshko.
 */
@Slf4j
public class GfsMetaDataWriter {
    private GfsUrlBuilder urlBuilder;

    public GfsMetaDataWriter(GfsUrlBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    public void writeMetaData(File outputFile) throws IOException {
        log.info("===Writing metadata report...===");
        try (FileWriter fw = new FileWriter(outputFile)) {
            String newLine = System.getProperty("line.separator");
            StringBuilder metadata = new StringBuilder();
            metadata.append("Product................").append(urlBuilder.getProductName()).append(newLine)
                    .append("Date...................").append(urlBuilder.getDateOfForecast()).append(newLine)
                    .append("Model run cycle........").append(urlBuilder.getModelRunCycle()).append(newLine)
                    .append("Levels.................");
                    urlBuilder.getLevels().forEach(lev ->
                            metadata.append((lev == ALL) ?
                                    "ALL" : lev.asString()).append(","));
            metadata.append(newLine);
            metadata.append("Variables..............");
                    urlBuilder.getVariables().forEach(var ->
                            metadata.append((var == Variable.ALL) ?
                                    "ALL" : var.asString()).append(","));
            metadata.append(newLine);
            metadata.append("Spatial extent:").append(newLine);
            metadata.append("leftlon................").append(urlBuilder.getSpatialExtent().getLeftlon()).append(newLine);
            metadata.append("rightlon...............").append(urlBuilder.getSpatialExtent().getRightlon()).append(newLine);
            metadata.append("toplat.................").append(urlBuilder.getSpatialExtent().getToplat()).append(newLine);
            metadata.append("bottomlat..............").append(urlBuilder.getSpatialExtent().getBottomlat());

            fw.write(metadata.toString());

        }
    }
}
