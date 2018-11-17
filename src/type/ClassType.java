package type;

public class ClassType extends Type {

	private String id;
	private boolean isInstantiated = false;

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

    public boolean isInstantiated() {
        return isInstantiated;
    }

    public void setInstantiated(boolean instantiated) {
        isInstantiated = instantiated;
    }
}
