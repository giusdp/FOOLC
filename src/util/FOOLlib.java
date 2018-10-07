package util;

import type.BoolType;
import type.ClassType;
import type.IntType;
import type.Type;
import type.VoidType;

public class FOOLlib {
	
  private static Environment env;
  
  public static void setEnv(Environment e) { env=e; }

  public enum RuleName {
	  EXP,
	  STM
  }
  
  private static int labCount=0; 
  
  private static int funLabCount=0; 

  private static String funCode=""; 

  //valuta se il tipo "a" è <: al tipo "b"
  public static boolean isSubtype (Type a, Type b) {
	  if (env == null) {
		  System.err.println("Error: FOOLlib.env is null. Call FOOLlib.setEnv in CompilerLauncher after creating an env.");
		  System.exit(1);
	  }
	  
	  // Se a e b sono tipi primitivi (int, bool o void) si controlla solo se sono uguali.
	  if (a instanceof BoolType || a instanceof IntType || a instanceof VoidType) {
		  return a.getClass().equals(b.getClass()); 		  
	  }
	  // Se a e' un ClassType allora si controlla il subtyping fra classi
	  else if (a instanceof ClassType) {
		  
		  if (!(b instanceof ClassType)) return false; // se anche b non e' una classe, non puo' esserci subtyping
		  
		  ClassType typeA = (ClassType) a;
		  ClassType typeB = (ClassType) b;

		  if ((typeA).getId().equals(typeB.getId())) return true; // rappresentano la stessa classe (stesso ID)
		  else {
			  
			  // Altrimenti controlliamo la classe padre di a se ha lo stesso ID di b
			  // Per controllare la classe padre serve accedere alla classMap in env
			  
			  String parent = env.getClassMap().get(typeA.getId()).getSuperClassName();
			  
			  while (parent != null) { // sali di livello nell'albero della ereditarieta'
				  if ((parent).equals(typeB.getId())) return true; // se la classe parent e' la stessa classe di b, c'e' subtyping
				  else parent = env.getClassMap().get(parent).getSuperClassName(); // altrimenti accedi alla classe padre di a
			  }
		  }
	  }
	  
	  return false;
  } 
  
  public static String freshLabel() { 
	return "label"+(labCount++);
  } 

  public static String freshFunLabel() { 
	return "function"+(funLabCount++);
  } 
  
  public static void putCode(String c) { 
    funCode+="\n"+c; //aggiunge una linea vuota di separazione prima di funzione
  } 
  
  public static String getCode() { 
    return funCode;
  } 


}