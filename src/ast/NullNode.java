package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;
import type.VoidType;


public class NullNode implements Node {

	private String id = null;

	/** This is would be like a class constructor but it doesnt set isInitialized to true.*/

    @Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return indent + "NULL\n";
	}

	@Override
	public Type typeCheck() {
		// TODO Auto-generated method stub
		return new VoidType();
	}

	// Non utilizzato
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
        if (id == null) return "";
		return "push 0\n"
                + "push " + id + "_class\n"
                + "new\n";
	}

    // Non utilizzato
    @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

