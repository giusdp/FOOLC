package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;

public class ProgExpNode implements Node {

  private Node exp;
  
  public ProgExpNode (Node e) {
    exp=e;
  }
  
  public String toPrint(String indent) {
    return "Prog\n" + exp.toPrint(indent + "  ") ;
  }
  
  @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		return exp.checkSemantics(env);
	}
  
  public Type typeCheck() {
    return exp.typeCheck();
  }  
  
  public String codeGeneration() {
		return exp.codeGeneration()+"halt\n";
  }  
  
}  