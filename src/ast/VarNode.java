package ast;

import java.util.ArrayList;
import java.util.HashMap;

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

		//env.offset = -2;
		HashMap<String,STEntry> hm = env.getST().get(env.getNestLevel());
		//separo introducendo "entry"
		STEntry entry = new STEntry(env.getNestLevel(),type, env.decOffset()); 

		if ( hm.put(id,entry) != null )
			res.add(new SemanticError("Var id "+id+" already declared"));

		res.addAll(exp.checkSemantics(env));

		return res;
	}

	public String toPrint(String s) {
		return s+"Var:" + id +"\n"
				+type.toPrint(s+"  ")  
				+exp.toPrint(s+"  "); 
	}

	//valore di ritorno non utilizzato
	public Type typeCheck () {
		if (! (FOOLlib.isSubtype(exp.typeCheck(),type)) ){      
			System.out.println("incompatible value for variable "+id);
			System.exit(0);
		}     
		return null;
	}

	public String codeGeneration() {
		return exp.codeGeneration();
	}  

}  