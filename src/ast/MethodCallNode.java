package ast;

import java.util.ArrayList;
import java.util.HashMap;

import ast.FunNode;
import type.ArrowType;
import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class MethodCallNode implements Node {
	
	private String id;
	private ArrayList<Node> parList;
	private int nestLevel;
	private IdNode varNode;
	private String ownerClass;
	private STEntry entry; 

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
				+ "\n" + indent + "    from class " + ownerClass + /*methodEntry.toPrint(indent + "  ") +*/ parlstr;
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
		
			ClassNode ownerClassNode = env.getClassMap().get(ownerClass);
			//Non può succedere in realtà perché quando si va ad instanziare la classe, se non è stata
			// definita già è errore, quindi molto prima di questo controllo.
			if (ownerClassNode == null){
				res.add(new SemanticError("Class "+ ownerClass + " not defined."));
				return res;
			}
			
			// Verificare che il metodo 'id' esiste in classe 'ownerClass':
			boolean methodDeclared = false;
			for (Node fn : ownerClassNode.getMethodList()) {
				FunNode function = (FunNode) fn;
				if (function.getId().equals(this.id)) {
					methodDeclared = true;
					break;
				}
			}	
			// Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende 
			// una classe, bisogna controllare che il metodo sia della 'superClass'
			if (ownerClassNode.getSuperClass() != null && !methodDeclared) {
				for (Node fn : ownerClassNode.getSuperClass().getMethodList()) {
					FunNode function = (FunNode) fn;
					if (function.getId().equals(this.id)) {
						methodDeclared = true;
						break;
					}
				}
			}
			
			if (!methodDeclared) {
				res.add(new SemanticError("Method "+ id + " in class " + ownerClass + " is not defined."));
				return res;
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
		
		ClassType classType = null;
		 ErrorType error = new ErrorType();
		 // TODO: Per fare il type checking ci serve il tipo della classe, pero' in checksemantics
		 // non lo prendiamo perche' viene usato la classMap in environment e non la ST.
		 // Una soluzione e' trasformare la classMap in una Symbol Table solo delle classi dove
		 // conserviamo sia metodi e campi, sia il tipo della classe.
		 error.addErrorMessage("Type checking of MethodCallNode not implemented.");
		 return error;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
