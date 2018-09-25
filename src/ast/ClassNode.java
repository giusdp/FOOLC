package ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import type.ClassType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class ClassNode implements Node {

	private String id;
	private ArrayList<Node> fieldList = new ArrayList<>();
	private ArrayList<Node> methodList = new ArrayList<>();

	// EREDITARIETA
	private String superClassName;
	private ClassNode superClass;

	public ClassNode(String name) {
		this.id = name;
	}

	@Override
	public String toPrint(String indent) {
		String fieldstr = "", methodstr = "", superstr = "";

		for (Node n : fieldList) {
			fieldstr += n.toPrint(indent + "  ");
		}

		for (Node method : methodList) {
			methodstr += method.toPrint(indent + "  ");
		}

		if (superClass != null)
			superstr += indent + "  " + "Implements:" + superClass + "\n";

		return indent + "Class:" + id + "\n" + superstr + fieldstr + methodstr;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		/**
		 * Da verificare:
		 * - superclasse esiste (se ci sia)
		 * - classe non è stata ridichiarata
		 * - chiamare checkSemantics ai campi e alle funzioni
		 * NON necessari:
		 * - controllare che funzioni/campi sono già dichiarati (fatto da IdNode etc.)
		 */
		
		//create result list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//env.offset = -2;
		
		// Controllo classe già dichiarata
		HashMap<String,STEntry> outerScope = env.getST().get(env.getNestLevel());

		STEntry entry = new STEntry(env.getNestLevel(), new ClassType(id), env.decOffset()); 

		if ( outerScope.put(id, entry) != null ) {
			res.add(new SemanticError("Class "+ id +" is already declared"));
			return res; // Se la classe è già stata dichiarata allora possiamo fermarci
		}
		// ****************
		
		// Controllo super classe dichiarata
		
		if (superClassName != null) {
			if (outerScope.get(superClassName) == null) {
				res.add(new SemanticError("Super class "+ superClassName +" is not declared"));
			} else {
				setSuperClass(env.getClassMap().get(superClassName));
			}
		}
		
		// Altrimenti proseguiamo con la creazione di un nuovo scope
		// Dove inserire i campi e i metodi e fare i controlli dovuti
		env.incNestLevel();
		HashMap<String,STEntry> newScope = new HashMap<String,STEntry> ();
		env.getST().add(newScope);

		int paroffset=1;
		
		for (Node field : fieldList) {
			ParNode arg = (ParNode) field;
			STEntry argSTEntry = new STEntry(env.getNestLevel(),arg.getType(),paroffset++);
			if (newScope.put(arg.getId(), argSTEntry) != null) {
				res.add(new SemanticError("Parameter "+arg.getId()+" already declared"));
			}
		}

		for (Node method : methodList) {
			FunNode fun = (FunNode) method;
			res.addAll(fun.checkSemantics(env));
		}

		env.getST().remove(env.decNestLevel());
		
		return res;
	}

	@Override
	public Type typeCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		return id;
	}

	public ArrayList<Node> getFieldList() {
		return fieldList;
	}

	public void addField(Node node) {
		fieldList.add(node);
	}
	public void addMethod(Node node) {
		methodList.add(node);
	}

	public ArrayList<Node> getMethodList() {
		return methodList;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}
	
	public void setSuperClass(ClassNode parent) {
		this.superClass = parent;
	}


}
