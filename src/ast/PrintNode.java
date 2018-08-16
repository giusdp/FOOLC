package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;

public class PrintNode implements Node {

  private Node val;
  
  public PrintNode (Node v) {
    val=v;
  }
  
  public String toPrint(String indent) {
    return indent+"Print\n" + val.toPrint(indent+"  ") ;
  }
  
  public Type typeCheck() {
    return val.typeCheck();
  }  
  
  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

 	  return val.checkSemantics(env);
 	}
  
  public String codeGeneration() {
		return val.codeGeneration()+"print\n";
  }
    
}  