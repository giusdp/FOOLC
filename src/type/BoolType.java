package type;

public class BoolType implements Type {

	@Override
	public String toPrint(String s) {
		return s+"Bool Type\n"; 
	}
}
