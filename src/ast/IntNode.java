package ast;

import java.util.ArrayList;

import type.IntType;
import type.Type;
import util.Environment;
import util.SemanticError;

public class IntNode implements Node {

	private Integer val;

	public IntNode (Integer n) {
		val = n;
	}

	public String toPrint(String indent) {
		return indent+"Int:" + Integer.toString(val) +"\n";  
	}

	public Type typeCheck() {
		return new IntType();
	} 

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {

		return new ArrayList<SemanticError>();
	}

	public String codeGeneration() {
		return "push "+val+"\n";
	}

}  