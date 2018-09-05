package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;
import type.VoidType;


public class NullNode implements Node {

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent + "NULL";
	}

	@Override
	public Type typeCheck() {
		// TODO Auto-generated method stub
		return new VoidType();
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}
