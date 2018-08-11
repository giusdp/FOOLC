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

		// Il nesting level viene incrementato ogni volta in cui si entra in uno scope,
		// quindi non sarà mai -1, perché il let in, le funzioni e le classi creano nuovi scope
		// e le variabili non possono essere dichiarate al di fuori di quei comandi.
		HashMap<String,STEntry> hm = env.getST().get(env.getNestLevel());
		//separo introducendo "entry"
		STEntry entry = new STEntry(env.getNestLevel(), type, env.decOffset()); 
		//System.out.println("Current nest level: " + env.getNestLevel() + " last offset: " + env.getOffset());

		System.out.println("VAR: " + id + " STENTRY: " + entry.getNestLevel() + 
				" type: " + entry.getType().toPrint("") + " offset: " + entry.getOffset());
		if ( hm.put(id, entry) != null )
			res.add(new SemanticError("Var with id "+ id +" is already declared"));

		res.addAll(exp.checkSemantics(env));

		return res;
	}

	public String toPrint(String s) {
		return s+"Var:" + id +"\n" + type.toPrint(s+"  ")  + exp.toPrint(s+"  "); 
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