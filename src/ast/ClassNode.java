package ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import type.ArrowType;
import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class ClassNode implements Node {

	private String id;
	private ArrayList<Node> fieldList = new ArrayList<>();
	private ArrayList<Node> methodList = new ArrayList<>();

	private ClassType type;
	
	// EREDITARIETA
	private String superClassName;
	private ClassNode superClass;

	// COSTRUTTORE
	public ClassNode(String name, ArrayList<Node> fieldList, ArrayList<Node> methodList) {
		this.id = name;
		this.fieldList = fieldList;
		this.methodList = methodList;
		
		type = new ClassType(id);
		
		ArrayList<Type> fieldTypeList = new ArrayList<>();
		for (Node par : fieldList) {
			ParNode p = (ParNode) par;
			fieldTypeList.add(p.getType());
		}
		type.setFieldTypeList(fieldTypeList);
		
		ArrayList<ArrowType> methodTypeList = new ArrayList<>();
		for (Node m : methodList) {
			FunNode f = (FunNode) m;
			methodTypeList.add((ArrowType) f.getType());
		}
		type.setMethodTypeList(methodTypeList);
		
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
			superstr += indent + "  " + "Extends: " + superClassName + "\n";

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
		HashMap<String,STEntry> currentScope = env.getST().get(env.getNestLevel());

		STEntry entry = new STEntry(env.getNestLevel(), type, env.decOffset()); 

		if ( currentScope.put(id, entry) != null ) {
			res.add(new SemanticError("Class "+ id +" is already declared"));
			return res; // Se la classe è già stata dichiarata allora possiamo fermarci
		}
		// ****************
		
		// Controllo super classe dichiarata
		
		if (superClassName != null) {
			if (currentScope.get(superClassName) == null) {
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
		// Ci sarebbe da controllare il tipo di tutti i campi e di tutti i metodi e della superclasse, se c'e',
		// per vedere se ci sono errori, e poi se tutto va bene si ritorna il ClassType.
		// Pero' e' inutile controllare il tipo di Par perche' non dara' mai errori (vedi ParNode per il perche')
		// La superclasse e' anche inutile controllare perche' gia' ProgClassNode si occupa
		// del type checking di tutte le classi, quindi direi di controllare solo i tipi dei metodi.
		
		// TODO: controllare tipi dei campi (istanceof NullNode?)
		Type methodType; 
		for (Node m : methodList) {
			FunNode method = (FunNode) m;
			methodType = method.typeCheck();
			if (methodType instanceof ErrorType) return methodType; // Return ErrorType se c'e' un errore nei metodi
		}
		
		// Una volta controllato che i metodi vadano bene, bisogna controllare l'overriding dei metodi!
		// Se c'e' una super classe e ci sono overriding dei metodi, bisogna controllare che sia stato fatto
		// bene, usando la regola del type checking sull'overriding nelle slides
		
		/* METTERE QUI CODICE TYPE CHECKING OVERRIDING */
		
		return type;
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

	public ClassNode getSuperClass() {
		return superClass;
	}
	
	public ClassType getClassType() {
		return type;
	}

}
