package type;

public class ErrorType extends Type {

	public String msg = "";
	
	@Override
	public String toPrint(String s) {
		// TODO Auto-generated method stub
		return msg;
	}

	public void addErrorMessage(String msg) {
		this.msg += msg;
	}

}
