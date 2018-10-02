package ast;

import java.util.ArrayList;

import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class ConstructorNode implements Node {
	
	protected String id;
	protected ArrayList<Node> parList; 
	private STEntry entry; 
	private int nestingLevel;
	
	
	public ConstructorNode(String id, ArrayList<Node> parList) {
		this.id = id;
		this.parList = parList;
	}

	@Override
	public String toPrint(String indent) {
		String parlstr="";
		for (Node par:parList)
		  parlstr+=par.toPrint(indent + "  ");		
		return indent+"Constructor for Class: " + id + " at nestlev " + nestingLevel +"\n" 
        +entry.toPrint(indent + "  ") + parlstr; 
	}

	@Override
	public Type typeCheck() {
		
		//TODO: ripetizione di codice con il call node (e methodcallnode)
		// Possiamo creare una classe astratta che implementa questo codice (con tipi generici) e viene ereditato
		
		 ClassType classType = null;
		 ErrorType error = new ErrorType();
		 Type entryType = entry.getType();
		 
	     // Se e' una invocazione del costruttore (new Class() ) allora il tipo sara' un classtype e si fa
	     // type checking sul costruttore 
		 if (entryType instanceof ClassType) {
			 classType = (ClassType) entryType;
			 
			 // Bisogna controllare che i tipi degli argomenti (parList) siano subtype dei campi della classe
			 // I campi della classe sono da considerarsi come parametri
			 
			 for (int i=0; i<classType.getFieldTypeList().size(); i++) 
				 System.out.println(classType.getFieldTypeList().get(i).toPrint(""));
		        	 
			 
	    	 ArrayList<Type> parTypes = classType.getFieldTypeList();
	         if ( !(parTypes.size() == parList.size()) ) {
	        	 error.addErrorMessage("Wrong number of parameters in the invocation of the constructor for "+id +
	        			 ". \n Expected " + parTypes.size() + " but found " + parList.size());
	        	 return error;
	         } 
	         
	         for (int i=0; i<parList.size(); i++) 
	           if ( !(FOOLlib.isSubtype( (parList.get(i)).typeCheck(), parTypes.get(i)) ) ) {
	        	   error.addErrorMessage("Wrong type for the "+(i+1)+"-th parameter in the invocation of the constructor for "+id);
	        	   return error;
	           } 
	         
	         return classType;
		 }

		 // ALTRIMENTI se non e' ne' funzione ne' costruttore, allora errore
		 error.addErrorMessage("Invocation of 'new "+id + "()' but it's not a constructor.");
		 return error;
	 
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//create the result
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		
		 int j=env.getNestLevel();
		 STEntry tmp=null; 
		 
		 while (j>=0 && tmp==null)
		     tmp=(env.getST().get(j--)).get(id);
		 
		 if (tmp==null) {
			 res.add(new SemanticError("Class " + id + " not declared"));
		 }
		 else{
			 this.entry = tmp;
			 this.nestingLevel = env.getNestLevel();
			 
			 for(Node arg : parList)
				 res.addAll(arg.checkSemantics(env));
		 }
		 return res;
	}

}
