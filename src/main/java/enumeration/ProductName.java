package enumeration;

/**
 * @author Viacheslav Oleshko.
 */
public enum ProductName implements StringRepresentable{
    GFS_025_DEGREE  ("gfs_0p25"),
    GFS_050_DEGREE  ("gfs_0p50"),
    GFS_1_DEGREE    ("gfs_1p00");

    private String productName;

    ProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String asString() {
        return this.productName;
    }
}
