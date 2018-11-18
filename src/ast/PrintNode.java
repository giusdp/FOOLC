package ast;

import java.util.ArrayList;

import type.Type;
import type.ClassType;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class PrintNode implements Node {

  private Node expNode;
  private String variableID;
  private Type type;
  
  public PrintNode (Node v, String id) {
    this.expNode = v;
    this.variableID = id;
    // Set during CheckSemantics:
    this.type = null;
  }
  
  public String toPrint(String indent) {
    return indent+"Print\n" + this.expNode.toPrint(indent+"  ") ;
  }
  
  public Type typeCheck() {

    if (this.expNode != null) {
    	return this.expNode.typeCheck();
    } else {
    	return this.type;
    }
  }  
  
  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

	  ArrayList<SemanticError> res = new ArrayList<SemanticError>();

	  if (this.expNode instanceof NullNode) {
		String nodetype = "null";
		res.add(new SemanticError("Cannot pass a  " + nodetype + " object to a Print() statement."));
		return res;
	  }
	  
	  // Scan symbol table for declaration of associated ID, if variable used
	  if (this.expNode == null) {
		  int j = env.getNestLevel();
		  STEntry tmp=null; 
		  while (j >= 0 && tmp==null) {
			  tmp = (env.getST().get(j)).get(this.variableID);
			  j = j - 1;
		  }
	
		  if (tmp == null) {
			  res.add(new SemanticError("Unable to print undeclared variable, " + this.variableID));
		  } else {
			  this.type = tmp.getType();
			  if (this.type instanceof ClassType) {
				  res.add(new SemanticError("Unable to print object, " + this.variableID));
			  }
		  }
	  }
	
 	  if (this.expNode != null)
 		  res.addAll(this.expNode.checkSemantics(env));
 	  return res;
 	}
  
  public String codeGeneration() {
	  if (this.expNode != null) {
		return expNode.codeGeneration()+"print\n";
	  }
  }
    
}  