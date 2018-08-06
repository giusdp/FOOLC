package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

import type.Type;

public interface Node {
   
  String toPrint(String indent);

  //fa il type checking e ritorna: 
  //  per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
  //  per una dichiarazione, "null"
  Type typeCheck();
  
  String codeGeneration();
  
  ArrayList<SemanticError> checkSemantics(Environment env);
  
}  