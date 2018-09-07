package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ArrowType;
import type.ClassType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class MethodCallNode implements Node {
	
	private String id;
	private STEntry methodEntry;
	private ArrayList<Node> parList;
	private int nestLevel;
	private IdNode varNode;
	private String ownerClass;

	public MethodCallNode(String m, ArrayList<Node> args, Node obj) {
		id = m;
		parList = args;
		varNode = (IdNode) obj;
	}

	@Override
	public String toPrint(String indent) {
		String parlstr = "";

		for (Node par : parList)
			parlstr += par.toPrint(indent + "  ");

		return indent + "Call:" + id + " at nestlev " + nestLevel 
				+ "\n" + methodEntry.toPrint(indent + "  ") + parlstr;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		
		res.addAll(varNode.checkSemantics(env));
		
		// Dopo i controlli preliminari sulla variabile usata. 
		// Si cerca la definizione del metodo nell'hashmap dei metodi
		ownerClass = ((ClassType) varNode.getType()).getId(); // Ottendo il nome/tipo della classe
		
		//Non può succedere in realtà perché quando si va ad instanziare la classe, se non è stata
		// definita già è errore, quindi molto prima di questo controllo.
		if (env.getClassMethods().get(ownerClass) == null){
			res.add(new SemanticError("Class "+ ownerClass + " not defined."));
			return res;
		}
		if (!env.getClassMethods().get(ownerClass).contains(id)) {
			res.add(new SemanticError("Method "+ id + " in class " + ownerClass + " is not defined."));
			return res;
		}
	
		return res;
	}

	@Override
	public Type typeCheck() {
		// TODO Auto-generated method stub
		
		if (!(varNode.getType() instanceof ClassType)) {
			// TODO: Method of detecting error in TypeCheck.
			// E.g. un ErrorType classe
			/* res.add(new SemanticError("Var id '" + varNode.getId()
			+ "' is not an object; cannot invoke method " + id + "."));
			return res; */
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
