package ast;

import java.util.ArrayList;

import type.Type;
import type.BoolType;
import util.Environment;
import util.SemanticError;
import util.FOOLlib;

public class IfNode implements Node {

  private Node cond;
  private Node th;
  private Node el;
  
  public IfNode (Node c, Node t, Node e) {
	
    cond=c;
    th=t;
    el=e;
  }
  
  public String toPrint(String indent) {
    return indent+"If\n" + cond.toPrint(indent+"  ") + th.toPrint(indent+"  ") + el.toPrint(indent+"  "); 
  }
  
  
  @Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
	  //create the result
	  ArrayList<SemanticError> res = new ArrayList<SemanticError>();
	  
	  //check semantics in the condition
	  res.addAll(cond.checkSemantics(env));
	 	  
	  //check semantics in then and in else exp
	  
	  res.addAll(th.checkSemantics(env));
	  res.addAll(el.checkSemantics(env));
	  
	  return res;
	}
  
  
  public Type typeCheck() {
    if (!(FOOLlib.isSubtype(cond.typeCheck(),new BoolType()))) {
      System.out.println("non boolean condition in if");
      System.exit(0);
    }
    Type t = th.typeCheck();
    Type e = el.typeCheck();
    if (FOOLlib.isSubtype(t,e)) 
      return e;
    if (FOOLlib.isSubtype(e,t))
      return t;
    System.out.println("Incompatible types in then else branches");
    System.exit(0);
    return null;
  }
  
  public String codeGeneration() {
	  String l1 = FOOLlib.freshLabel(); 
	  String l2 = FOOLlib.freshLabel();
	  return cond.codeGeneration()+"push 1\n"+"beq "+ l1 +"\n"+	el.codeGeneration()+
			 "b " + l2 + "\n" +l1 + ":\n"+th.codeGeneration()+l2 + ":\n"; 
  }
  
}  