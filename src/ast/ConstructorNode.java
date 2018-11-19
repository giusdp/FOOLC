package ast;

import java.util.ArrayList;

import type.ClassType;
import type.ErrorType;
import type.Type;
import type.VoidType;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class ConstructorNode implements Node {

	protected String className;
	protected ArrayList<Node> parList; 
	private STEntry entry; 
	private int nestingLevel;

	private ClassNode classNode;


	public ConstructorNode(String id, ArrayList<Node> parList) {
		this.className = id;
		this.parList = parList;
	}

	@Override
	public String toPrint(String indent) {
		StringBuilder parlstr= new StringBuilder();
		for (Node par:parList)
			parlstr.append(par.toPrint(indent + "  "));
		return indent+"Constructor for Class: " + className + " at nestlev " + nestingLevel +"\n" 
		+entry.toPrint(indent + "  ") + parlstr; 
	}

	@Override
	public Type typeCheck() {

		ErrorType error = new ErrorType();
		Type entryType = entry.getType();

		// Se e' una invocazione del costruttore (new Class() ) allora il tipo sara' un classtype e si fa
		// type checking sul costruttore 
		if (entryType instanceof ClassType) {
			ClassType classType = (ClassType) entryType;

			// Bisogna controllare che i tipi degli argomenti (parList) siano subtype dei campi della classe
			// I campi della classe sono da considerarsi come parametri
			ArrayList<Node> fields = classNode.getFieldList();
			if ( !(fields.size() == parList.size()) ) {
				error.addErrorMessage("Wrong number of parameters in the invocation of the constructor for "+ className +
						". \n Expected " + fields.size() + " but found " + parList.size());
				return error;
			} 

			for (int i=0; i<parList.size(); i++) {
				Type parType = parList.get(i).typeCheck();
				Type fieldType = fields.get(i).typeCheck();
                if (parType instanceof VoidType ||
                        (parType instanceof ClassType && !((ClassType) parType).isInstantiated())) {
                    error.addErrorMessage("Cannot pass 'null' to constructor. Null at "+ (i + 1) +"-th parameter.");
                    return error;
                    //((ClassType) fieldType).setInstantiated(false);
                }

                if (parType instanceof ErrorType) return parType;
                if (fieldType instanceof ErrorType) return fieldType;
                if (!(FOOLlib.isSubtype(parType, fieldType))) {
                    error.addErrorMessage("Wrong type for the " + (i + 1) + "-th parameter in the invocation of the constructor for " + className);
                    return error;
                }
            }


			return classType;
		}

		// ALTRIMENTI se non e' costruttore, allora errore
		error.addErrorMessage("Invocation of 'new "+ className + "()' but "+className+" is not a constructor.");
		return error;

	}


	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//create the result
		ArrayList<SemanticError> res = new ArrayList<>();

		int j=env.getNestLevel();
		STEntry tmp=null; 

		while (j>=0 && tmp==null)
			tmp=(env.getST().get(j--)).get(className);

		if (tmp==null) {
			res.add(new SemanticError("Class " + className + " not declared"));
			return res;
		}
		else{
			this.entry = tmp;
			this.nestingLevel = env.getNestLevel();

			for(Node arg : parList) {
                res.addAll(arg.checkSemantics(env));
            }
            classNode = Environment.getClassMap().get(className);
            return res;
        }
	}

	
	@Override
	public String codeGeneration() {
		StringBuilder parCode = new StringBuilder();
		for (int i = parList.size() - 1; i >= 0; i--) {
			parCode.append(parList.get(i).codeGeneration());
		}
		
        return parCode
                + "push " + parList.size() + "\n"
                + "push " + className + "_class\n"
                + "new\n";
	}

}
