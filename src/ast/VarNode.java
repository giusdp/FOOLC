package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;
import util.FOOLlib;

public class VarNode implements Node {

	private String id;
	private Type type;
	private Node exp;

	public VarNode (String i, Type t, Node v) {
		id=i;
		type=t;
		exp=v;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//create result list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		// Il nesting level viene incrementato ogni volta in cui si entra in uno scope,
		// quindi non sarà mai -1, perché il let in, le funzioni e le classi creano nuovi scope
		// e le variabili non possono essere dichiarate al di fuori di quei comandi.
		HashMap<String,STEntry> hm = env.getST().get(env.getNestLevel());
		//separo introducendo "entry"
		STEntry entry = new STEntry(env.getNestLevel(), type, env.decOffset()); 
		
		//System.out.println("VAR: " + id + " STENTRY: " + entry.getNestLevel() + 
			//	"\ntype: " + entry.getType().toPrint("") + " offset: " + entry.getOffset());
		boolean varClass = false;
		if (type instanceof ClassType) {
			varClass = true;
			
			int j = env.getNestLevel();
			STEntry tmp = null;

			while (j >= 0 && tmp == null) {
				tmp = (env.getST().get(j--)).get(((ClassType) type).getId());
				// Il nome della classe equivale al tipo della variabile
			}
			
			if (tmp == null) {
				res.add(new SemanticError("Class " + ((ClassType) type).getId() + " has not been defined."));
				return res;
			} 
		}
		if ( hm.put(id, entry) != null )
		{
			if(!varClass)res.add(new SemanticError("Id for var "+ id +" is already declared."));
			else res.add(new SemanticError("Id "+ id +" already used as class name. Can't use exact name for var."));
			return res;
		}
		res.addAll(exp.checkSemantics(env));
		return res;
	}

	public String toPrint(String indent) {
		return indent+"Var:" + id +"\n" + type.toPrint(indent+"  ")  + exp.toPrint(indent+"  "); 
	}

	//valore di ritorno non utilizzato
	public Type typeCheck () {
		if (! (FOOLlib.isSubtype(exp.typeCheck(),type)) ){ 
			ErrorType error = new ErrorType();
			error.addErrorMessage("Incompatible value for variable" + id);
			return error;
		}     
		return type;
	}

	public String codeGeneration() {
		return exp.codeGeneration();
	}  

}  