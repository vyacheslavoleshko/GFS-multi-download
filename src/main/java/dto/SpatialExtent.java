package dto;

import enumeration.StringRepresentable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Viacheslav Oleshko
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SpatialExtent implements StringRepresentable {
    private int leftlon;
    private int rightlon;
    private int toplat;
    private int bottomlat;

    @Override
    public String asString() {
        return "subregion=&" +
                "leftlon=" + leftlon + "&" +
                "rightlon=" + rightlon + "&" +
                "toplat=" + toplat + "&" +
                "bottomlat=" + bottomlat + "&";
    }
}
