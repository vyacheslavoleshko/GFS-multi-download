package enumeration;

/**
 * @author Viacheslav Oleshko
 */
public enum ModelRunCycle implements StringRepresentable{
    MIDNIGHT    ("00"),
    SIX_AM      ("06"),
    NOON        ("12"),
    SIX_PM      ("18");

    private String modelRunCycle;

    ModelRunCycle(String modelRunCycle) {
        this.modelRunCycle = modelRunCycle;
    }

    @Override
    public String asString() {
        return this.modelRunCycle;
    }
}
