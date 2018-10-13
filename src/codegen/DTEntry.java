package codegen;

/**
 * @author Giuseppe
 *
 */

public class DTEntry {

	private String methodID;
    private String methodLabel;

    public DTEntry(String mID, String mLabel) {
        methodID = mID;
        methodLabel = mLabel;
    }

    public String getMethodID() {
        return methodID;
    }

    public String getMethodLabel() {
        return methodLabel;
    }
}