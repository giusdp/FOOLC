package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ErrorType;
import type.Type;
import type.VoidType;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class StmAsmNode implements Node {

	private Node AsmBody;
	private Type AsmType;
	private String variableID;
	
	StmAsmNode(String variableID, Node AsmBody) {
		this.variableID = variableID;
		this.AsmBody = AsmBody;
		// settata in CheckSemantics():
		this.AsmType = null;
	}
	
	@Override
	public String toPrint(String indent) {

		// TODO: Double check formatting of statement print.
		String bodyString = this.AsmBody.toPrint("");
		return indent + "Assign:" + this.variableID + " = " + bodyString + "\n";
	}

	@Override
	public Type typeCheck() {
		
		Type bodyType = AsmBody.typeCheck();
		
		if (bodyType instanceof ErrorType) {
			return bodyType;
		}

		// Verifica: type(body) <: type(var).
		if (! FOOLlib.isSubtype(bodyType, this.AsmType)) {
			ErrorType errorMessage = new ErrorType();
			errorMessage.addErrorMessage("Type mismatch in assignment to " + this.variableID + ".");
			return errorMessage;
		}
		
		return this.AsmType;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {

		ArrayList<SemanticError> res =  new ArrayList<>();
		
		HashMap<String,STEntry> currentScope = env.getST().get(env.getNestLevel());

		STEntry variableEntry = currentScope.get(this.variableID);
		
		// Verifica: la variabile e` dichiarata.
		if (variableEntry == null) {
			res.add(new SemanticError("Variable " + this.variableID + " not declared."));
		} else {
			this.AsmType = variableEntry.getType();
		}
		
		return res;
	}

}
