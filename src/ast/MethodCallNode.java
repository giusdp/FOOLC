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

	private IdNode varNode;
	private String ownerClass;
	
	private STEntry ownerClassEntry; 
	private int dtOffset;
	private int nestingLevel;
	
	private Type methodType;


	public MethodCallNode(String m, ArrayList<Node> args, Node obj) {
		id = m;
		parList = args;
		varNode = (IdNode) obj;
	}

	@Override
	public String toPrint(String indent) {
		StringBuilder parlstr = new StringBuilder();

		for (Node par : parList)
			parlstr.append(par.toPrint(indent + "  "));

		// TODO: methodEntry is null at runtime. Causes NullPtrException.
		// methodEntry is never instantiated here.
		return indent + "Method Call:" + id + "\n" + indent + "    from class " + ownerClass + /*methodEntry.toPrint(indent + "  ") +*/ parlstr;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> res = new ArrayList<>();
				
		res.addAll(varNode.checkSemantics(env));
		if (!res.isEmpty()) return res;
		
		// Dopo i controlli preliminari sulla variabile usata. 
		// Si cerca la definizione del metodo nell'hashmap dei metodi
		// Siccome  metodo di una classe, la variabile dovrebbe essere una classe
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
					methodType = (ArrowType) function.getType();
					methodDeclared = true;
					break;
				}
			}	
			// Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende 
			// una classe, bisogna controllare che il metodo sia della 'superClass'
			if (!methodDeclared) {
				while (ownerClassNode.getSuperClass() != null) {
					for (Node fn : ownerClassNode.getMethodList()) {
						FunNode function = (FunNode) fn;
						if (function.getId().equals(this.id)) {
							// if method declared in subclass is polymorphic, store type for TypeChecking.
							methodType = (ArrowType) function.getType();
							methodDeclared = true;
							break;
						}
					}
					ownerClassNode = ownerClassNode.getSuperClass();
				}
			}
			
			if (!methodDeclared) {
				res.add(new SemanticError("Method "+ id + " in class " + ownerClass + " is not defined."));
				return res;
			}
			
			nestingLevel = env.getNestLevel(); // Otteniamo il nesting level "a tempo di invocazione"
			ownerClassEntry = ownerClassNode.stEntry;
			dtOffset = ownerClassNode.getMethodDTOffset(this.id);
		}
		catch (ClassCastException e) {
			// TODO: Però questo è un controllo di tipi, si dovrebbe fare nel type check non qui
			res.add(new SemanticError("Var " + varNode.getId() + " is not ClassType, instead it's " + varNode.getType().toPrint("")));
		}
		
		return res;
	}

	@Override
	public Type typeCheck() {
		/*
		 * To be achieved:
		 */

		ArrowType funType = null;
		ErrorType error = new ErrorType();
		
		// Siccome il polimorfismo e' stato gia' controllato in classnode, per tutti i metodi
		// Ora bisogna fare un semplice controllo sui parametri. Questo controllo ora e' identico a CallNode
		// TODO: classe padre per callnode, methodcallnode, constructornode per evitare questo codice ripetuto 3 volte???
		if (methodType instanceof ArrowType) {
			funType = (ArrowType) methodType; 
			ArrayList<Type> parTypes = funType.getParList();

			// si controllano numero parametri con quelli passati in input
			if ( parTypes.size() != parList.size() ) {
				error.addErrorMessage("Wrong number of parameters in the invocation of the method: "+varNode.getId()+"."+id +
									  "\nExpected " + parTypes.size() + " but found " + parList.size());
				return error;
			}
			// si controllano tipi parametri con quelli passati in input
			for (int i = 0; i < parList.size(); i++) 
				if ( !(FOOLlib.isSubtype( (parList.get(i)).typeCheck(), parTypes.get(i)) ) ) {
					error.addErrorMessage("Wrong type for the "+(i+1)+"-th parameter in the invocation of method: "+varNode.getId()+"."+id);
					return error;
				}
			return funType.getReturn();
		}

		// ALTRIMENTI se non e' ne' funzione, allora errore
		error.addErrorMessage("Invocation of a non-function " + id);
		return error;
	 
	}

	@Override
	public String codeGeneration() {
		
		 StringBuilder parametersCodeString = new StringBuilder();
		    for (int i = parList.size() - 1; i >= 0; i--) {
		    	parametersCodeString.append(parList.get(i).codeGeneration());
		    }
		    
		    StringBuilder getAR= new StringBuilder();
			for (int i=0; i< nestingLevel - ownerClassEntry.getNestLevel(); i++) {
				getAR.append("lw\n");
			}
		    
			return "lfp\n" + //CL
					parametersCodeString +
					"push " + ownerClassEntry.getOffset() + "\n" + //metto offset sullo stack
			       "lfp\n" + 
					getAR +
				   "add\n" + 
	               "lw\n"  + //carico sullo stack il valore all'indirizzo ottenuto (della classe all'heap)
				   "cts\n"+ // Duplicando ora il top, duplico l'indirizzo della classe che punta all'heap. 
				   			//cosi' sara' il top dello stack all'esecuzione del metodo
				   "lw\n" + 
				   "push " + dtOffset + "\n"+
				   "add\n"+
				   "lm\n" +
			       "js\n";
	}

}
