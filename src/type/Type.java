package type;

import java.util.ArrayList;

import ast.Node;
import util.Environment;
import util.SemanticError;

public abstract class Type implements Node {
	
	// public static enum types {INT, BOOL, VOID, CLASS};
	
	public abstract String toPrint(String s);

	// Non utilizzato
	@Override
	public Type typeCheck() {return null; } 

	// Non utilizzato
	@Override
	public String codeGeneration() { return null;	}

	// Non utilizzato
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) { return null; }
	
	

}
