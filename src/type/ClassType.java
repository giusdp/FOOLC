package type;

public class ClassType extends Type {

	private String id;
	
	public ClassType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toPrint(String s) {
		return s+"Class Type " + id + "\n"; 
	}

}
