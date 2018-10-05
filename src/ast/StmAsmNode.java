package ast;

import java.util.ArrayList;

import type.Type;
import type.VoidType;
import util.Environment;
import util.SemanticError;

public class StmAsmNode implements Node {

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent+"ASM STM NODE. To Be Implemented\n";
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
