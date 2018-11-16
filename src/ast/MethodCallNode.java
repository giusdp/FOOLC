package ast;

import java.util.ArrayList;

import type.*;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;

public class MethodCallNode implements Node {

	private String id;
	private ArrayList<Node> parList;

	private IdNode variableIdNode;
	private String ownerClass;
	private ClassNode ownerClassNode;

	private int dtOffset;
	private int nestingLevel;

	protected Type methodType;

	private boolean isVarInitialized = false;

    public MethodCallNode() {
    }

    public MethodCallNode(String m, ArrayList<Node> args, Node obj) {
		id = m;
		parList = args;
		variableIdNode = (IdNode) obj;
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

        ArrayList<SemanticError> res = new ArrayList<>(variableIdNode.checkSemantics(env));

		if (!res.isEmpty()) return res;
		
		// Dopo i controlli preliminari sulla variabile usata. 
		// Si cerca la definizione del metodo nell'hashmap dei metodi
		// Siccome  metodo di una classe, la variabile dovrebbe essere una classe
		// Se non lo è si intercetta l'eccezione e si da un Semantic Error
		try {
			ownerClass = ((ClassType) variableIdNode.getType()).getId(); // Ottendo il nome/tipo della classe

			ownerClassNode = env.getClassMap().get(ownerClass);
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
					methodType = function.getType();
					methodDeclared = true;
					break;
				}
			}	
			// Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende 
			// una classe, bisogna controllare che il metodo sia della 'superClass'
			if (!methodDeclared) {
				while (ownerClassNode.getSuperClass() != null && !methodDeclared) {
					for (Node fn : ownerClassNode.getSuperClass().getMethodList()) {
						FunNode function = (FunNode) fn;
                        if (function.getId().equals(this.id)) {
							// if method declared in subclass is polymorphic, store type for TypeChecking.
							methodType = function.getType();
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
			dtOffset = ownerClassNode.getMethodDTOffset(this.id);

            for (Node arg : parList)
                res.addAll(arg.checkSemantics(env));
		}
		catch (ClassCastException e) {
			// TODO: Però questo è un controllo di tipi, si dovrebbe fare nel type check non qui
			res.add(new SemanticError("Var " + variableIdNode.getId() + " is not ClassType, instead it's " + variableIdNode.getType().toPrint("")));
		}

        return res;
	}

	@Override
	public Type typeCheck() {

        ErrorType error = new ErrorType();

        if (! ((ClassType) variableIdNode.getEntry().getType()).isInstantiated()){
            error.addErrorMessage("Invocation of method "+id+ " on non-initialized class "+ownerClass+".");
            return error;
        }

		// Siccome il polimorfismo e' stato gia' controllato in classnode, per tutti i metodi
		// Ora bisogna fare un semplice controllo sui parametri. Questo controllo ora e' identico a CallNode
		// TODO: classe padre per callnode, methodcallnode, constructornode per evitare questo codice ripetuto 3 volte???
		if (methodType instanceof ArrowType) {
            ArrowType funType = (ArrowType) methodType;
			ArrayList<Type> parTypes = funType.getParList();

			// si controllano numero parametri con quelli passati in input
			if ( parTypes.size() != parList.size() ) {
				error.addErrorMessage("Wrong number of parameters in the invocation of the method: "+ownerClass+"."+id +
									  "\nExpected " + parTypes.size() + " but found " + parList.size());
				return error;
			}
			// si controllano tipi parametri con quelli passati in input
			for (int i = 0; i < parList.size(); i++) 
				if ( !(FOOLlib.isSubtype( (parList.get(i)).typeCheck(), parTypes.get(i)) ) ) {
					error.addErrorMessage("Wrong type for the "+(i+1)+"-th parameter in the invocation of method: "+ownerClass+"."+id);
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
			for (int i = 0; i< nestingLevel - variableIdNode.getEntry().getNestLevel(); i++) {
				getAR.append("lw\n");
			}
		    
			return "lfp\n" + //CL
					parametersCodeString +
					"push " + variableIdNode.getEntry().getOffset() + "\n" + //metto offset sullo stack
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

    public Type getMethodType() {
        return methodType;
    }
}
