package ast;

import java.util.ArrayList;

import type.BoolType;
import type.Type;
import util.Environment;
import util.SemanticError;


//Node representing a boolean value: False or True
public class BoolNode implements Node {

  //The boolean value
  private boolean val;

  public BoolNode(boolean n) {
      val = n;
  }

  public String toPrint(String indent) {
      if (val)
          return indent + "Bool:true\n";
      else
          return indent + "Bool:false\n";
  }

  //A bool node is of type Bool, always
  public Type typeCheck() {
      return new BoolType();
  }

  @Override
  public ArrayList<SemanticError> checkSemantics(Environment env) {
      return new ArrayList<SemanticError>();
  }

  public String codeGeneration() {
      return "push " + (val ? 1 : 0) + "\n";
  }

}