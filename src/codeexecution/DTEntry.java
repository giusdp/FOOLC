package codeexecution;

/**
 * @author Giuseppe
 *
 */

public class DTEntry {

	private String methodId;
    private String methodLabel;

    public DTEntry(String mId, String mLabel) {
        this.methodId = mId;
        this.methodLabel = mLabel;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getMethodLabel() {
        return methodLabel;
    }
}