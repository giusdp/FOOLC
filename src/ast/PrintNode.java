package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;

public class PrintNode implements Node {

  private Node value;
  
  public PrintNode (Node v) {
    this.value = v;
  }
  
  public String toPrint(String indent) {
    return indent+"Print\n" + this.value.toPrint(indent+"  ") ;
  }
  
  public Type typeCheck() {
    return this.value.typeCheck();
  }  
  
  @Override
 	public ArrayList<SemanticError> checkSemantics(Environment env) {

 	  return this.value.checkSemantics(env);
 	}
  
  public String codeGeneration() {
		return value.codeGeneration()+"print\n";
  }
    
}  