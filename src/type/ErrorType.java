package type;

public class ErrorType extends Type {

	public String msg = "TypeError: ";
	
	@Override
	public String toPrint(String s) {
		return msg;
	}

	public void addErrorMessage(String msg) {
		this.msg += msg;
	}

}
