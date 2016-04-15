package fel.cvut.af.model.input;

/**
 * An enum representing variable response formats.
 * <p>
 * Client app understands those constants and creates response object according to it.
 */
public enum ResponseFormat {

    VALUE("VALUE"), NAME("NAME"), NAMES("NAMES"), VALUES("VALUES"), OPTION("OPTION"), OPTIONS("OPTIONS");

    /**
     * The string representation.
     */
    private String strVal;

    /**
     *
     * @param strVal the string representation
     */
    ResponseFormat(String strVal) {
        this.strVal = strVal;
    }

    @Override
    public String toString() {
        return strVal;
    }
}
