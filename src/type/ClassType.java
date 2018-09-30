package type;

import java.util.ArrayList;

import ast.FunNode;
import ast.ParNode;

public class ClassType extends Type {

	private String id;
	
	private ArrayList<Type> fieldTypeList = new ArrayList<>();
	private ArrayList<ArrowType> methodTypeList = new ArrayList<>(); 
	
	public ClassType(String id) {
		this.id = id;		
	}

	public void setFieldTypeList(ArrayList<Type> fieldTypeList) {
		this.fieldTypeList = fieldTypeList;
	}
	
	public void setMethodTypeList(ArrayList<ArrowType> methodTypeList) {
		this.methodTypeList = methodTypeList;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String toPrint(String s) {
		return s+"Class Type " + id + "\n"; 
	}

}
