package ast;
import java.util.ArrayList;

import type.ArrowType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.SemanticError;
import util.FOOLlib;
import util.STEntry;

public class CallNode implements Node {

  protected String id;
  protected STEntry entry; 
  protected ArrayList<Node> parList; 
  protected int nestingLevel;
  private boolean isConstructorCall = false;
  
  public CallNode(String text, ArrayList<Node> args, boolean isConstrCall) {
	id=text;
    parList = args;
    isConstructorCall = isConstrCall;
}
  
public String getId() {
	return id;
}

public String toPrint(String indent) {  //
    String parlstr="";
	for (Node par:parList)
	  parlstr+=par.toPrint(indent + "  ");		
	return indent+"Call:" + id + " at nestlev " + nestingLevel +"\n" 
           +entry.toPrint(indent + "  ")
           +parlstr;        
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
			 String errorSubject = (isConstructorCall) ? "Class " : "Function ";
			 res.add(new SemanticError(errorSubject + id + " not declared"));
		 }
		 else{
			 this.entry = tmp;
			 this.nestingLevel = env.getNestLevel();
			 
			 for(Node arg : parList)
				 res.addAll(arg.checkSemantics(env));
		 }
		 return res;
	}
  
  public Type typeCheck () {  //                           
	 ArrowType t=null;
	 ErrorType error = new ErrorType();
     if (entry.getType() instanceof ArrowType) {
    	 t=(ArrowType) entry.getType(); 
     }
     else {
    	 error.addErrorMessage("Invocation of a non-function "+id);
    	 return error;
     }
     ArrayList<Type> p = t.getParList();
     if ( !(p.size() == parList.size()) ) {
    	 error.addErrorMessage("Wrong number of parameters in the invocation of "+id);
    	 return error;
     } 
     for (int i=0; i<parList.size(); i++) 
       if ( !(FOOLlib.isSubtype( (parList.get(i)).typeCheck(), p.get(i)) ) ) {
    	   error.addErrorMessage("Wrong type for "+(i+1)+"-th parameter in the invocation of "+id);
    	   return error;
       } 
     return t.getRet();
  }
  
  public String codeGeneration() {
	    String parCode="";
	    for (int i=parList.size()-1; i>=0; i--)
	    	parCode+=parList.get(i).codeGeneration();
	    
	    String getAR="";
		  for (int i=0; i<nestingLevel - entry.getNestLevel(); i++) 
		    	 getAR+="lw\n";
	    
		return "lfp\n"+ //CL
               parCode+
               "lfp\n"+getAR+ //setto AL risalendo la catena statica
               // ora recupero l'indirizzo a cui saltare e lo metto sullo stack
               "push "+entry.getOffset()+"\n"+ //metto offset sullo stack
		       "lfp\n"+getAR+ //risalgo la catena statica
			   "add\n"+ 
               "lw\n"+ //carico sullo stack il valore all'indirizzo ottenuto
		       "js\n";
  }

    
}  