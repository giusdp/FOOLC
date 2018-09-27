package ast;

import java.util.ArrayList;
import java.util.HashMap;

import ast.FunNode;
import type.ArrowType;
import type.ClassType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class MethodCallNode implements Node {
	
	private String id;
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

		// TODO: methodEntry is null at runtime. Causes NullPtrException.
		// methodEntry is never instantiated here.
		return indent + "Method Call:" + id + " at nesting level " + nestLevel 
				+ "\n" + /*methodEntry.toPrint(indent + "  ") +*/ parlstr;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		
		res.addAll(varNode.checkSemantics(env));
		
		// Dopo i controlli preliminari sulla variabile usata. 
		// Si cerca la definizione del metodo nell'hashmap dei metodi
		// Siccome la sintassi è un metodo di una classe, la variabile dovrebbe essere una classe
		// Se non lo è si intercetta l'eccezione e si da un Semantic Error
		try {
			ownerClass = ((ClassType) varNode.getType()).getId(); // Ottendo il nome/tipo della classe
		
			//Non può succedere in realtà perché quando si va ad instanziare la classe, se non è stata
			// definita già è errore, quindi molto prima di questo controllo.
			if (env.getClassMap().get(ownerClass) == null){
				res.add(new SemanticError("Class "+ ownerClass + " not defined."));
				return res;
			}
			
			// Verificare che il metodo 'id' esiste in classe 'ownerClass':
			boolean methodDeclared = false;
			ClassNode owner = env.getClassMap().get(ownerClass);
			for (Node fn : owner.getMethodList()) {
				FunNode function = (FunNode) fn;
				if (function.getId().equals(this.id)) {
					methodDeclared = true;
					break;
				}
			}	
			// Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende 
			// una classe, bisogna controllare che il metodo sia della 'superClass'
			if (owner.getSuperClassName() != null && !methodDeclared) {
				for (Node fn : env.getClassMap().get(owner.getSuperClassName()).getMethodList()) {
					FunNode function = (FunNode) fn;
					if (function.getId().equals(this.id)) {
						methodDeclared = true;
						break;
					}
				}
				if (!methodDeclared) {
					res.add(new SemanticError("Method "+ id + " in class " + ownerClass + " is not defined."));
					return res;
				}
			}
		}
		catch (ClassCastException e) {
			// TODO: Però questo è un controllo di tipi, si dovrebbe fare nel type check non qui
			res.add(new SemanticError("Var " + varNode.getId() + " is not ClassType, instead it's " + varNode.getType().toPrint("")));
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
