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
	
	// Fields for polymorphic type-checking.
	private ArrayList<Type> overwrittenParamTypes;
	private ArrowType derivedType;
	private ArrowType baseType;

	public MethodCallNode(String m, ArrayList<Node> args, Node obj) {
		id = m;
		parList = args;
		varNode = (IdNode) obj;
		
		overwrittenParamTypes = new ArrayList<Type>();
		derivedType = null;
		baseType = null;
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
					derivedType = (ArrowType) function.getType();
					break;
				}
			}	
			// Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende 
			// una classe, bisogna controllare che il metodo sia della 'superClass'
			while (ownerClassNode.getSuperClass() != null) {
				ownerClassNode = ownerClassNode.getSuperClass();
				for (Node fn : ownerClassNode.getMethodList()) {
					FunNode function = (FunNode) fn;
					if (function.getId().equals(this.id)) {
						// if method declared in subclass is polymorphic, store type for TypeChecking.
						if (methodDeclared = true) {
							baseType = (ArrowType) function.getType();
						}
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
		ErrorType error = new ErrorType();
		/*
		 * To be achieved:
		 * Check PASSED PARAMETER types match DECLARED parameter types.
		 * check we're calling it on the correct classType X-> done in CheckSemantics.
		 * check the return type == return type of declaration
		 * case: f is polymorphic
		 * 	new return type T' <: old return type T
		 *  old par types Ti <: new par types Ti'
		 */

		// Case: method is polymorphic.
		if (baseType != null) {
			// Verify that T' <: T.
			Type derivedReturnType = derivedType.getReturn();
			Type baseReturnType = baseType.getReturn();
			
			if (!FOOLlib.isSubtype(baseReturnType,derivedReturnType)) {
				error.addErrorMessage("Derived method " + ownerClass + "." + id + "() must return same type " +
									  "or subtype of overridden method: " + baseReturnType.toPrint(""));
				return error;
			}
			
			// Verify old parameters are subtypes of new parameters.
		}
		
		ClassType classType = null;
		return new ClassType(id);
		 
		 // TODO: Per fare il type checking ci serve il tipo della classe, pero' in checksemantics
		 // non lo prendiamo perche' viene usato la classMap in environment e non la ST.
		 // Una soluzione e' trasformare la classMap in una Symbol Table solo delle classi dove
		 // conserviamo sia metodi e campi, sia il tipo della classe.
		 //error.addErrorMessage("Type checking of MethodCallNode not implemented.");
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

}
