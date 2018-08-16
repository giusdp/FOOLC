package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;

public class ParNode implements Node {

  private String id;
  private Type type;
  
  public ParNode (String i, Type t) {
   id=i;
   type=t;
  }
  
  public String getId(){
	  return id;
  }
  
  public Type getType(){
	  return type;
  }
  
  @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {

	  return new ArrayList<SemanticError>();
	}
  
  public String toPrint(String indent) {
	  return indent + "Par:" + id +"\n" + type.toPrint(indent+"  ") ; 
  }
  
  //non utilizzato
  public Type typeCheck () {
     return type;
  }
  
  //non utilizzato
  public String codeGeneration() {
		return "";
  }
    
}  