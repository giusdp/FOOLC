package type;

public class ErrorType extends Type {

	public String msg = "TypeError: ";
	
	@Override
	public String toPrint(String s) {
		return s+msg;
	}

	public void addErrorMessage(String msg) {
		this.msg += msg;
	}

}
