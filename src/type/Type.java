package type;

import java.util.ArrayList;

import ast.Node;
import util.Environment;
import util.SemanticError;

public abstract class Type implements Node {
	
	public static enum types {INT, BOOL, VOID, CLASS};
	
	public abstract String toPrint(String s);

	@Override
	public Type typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
