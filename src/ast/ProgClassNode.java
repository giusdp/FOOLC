package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ClassType;
import type.ErrorType;
import type.Type;
import type.VoidType;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class ProgClassNode implements Node {
	
	private ArrayList<ClassNode> classList;
	private ArrayList<Node> decList;
	private ArrayList<Node> expList;
	private ArrayList<Node> stmList;
	
	public ProgClassNode(ArrayList<ClassNode> classes,
						 ArrayList<Node> decs,
						 ArrayList<Node> exps,
						 ArrayList<Node> stms) {
		classList = classes;
		decList = decs;
		expList = exps;
		stmList = stms;
	}
	
	@Override
	public String toPrint(String indent) {
		String classesString = "", decString = "", expString = "", stmString = "";

		for (Node d : decList) decString += d.toPrint(indent + "  ");
		
		for (ClassNode c : classList) classesString += c.toPrint(indent + "  ");
		
		for (Node e : expList) expString += e.toPrint(indent + "  ");
		
		for (Node s : stmList) stmString += s.toPrint(indent + "  ");
		
		String let = "\n Let Declarations\n", in = " IN\n";
		if (decString.equals("")) {
			let = "";
			in = "";
		}
		
		String ex = " Expressions\n";
		if (expString.equals("")) ex = "";
		
		String st = " Statements\n";
		if (stmString.equals("")) st = "";
		
		return indent + "ProgClassNode\n Classes\n" + classesString + let + decString + in + ex + expString + st + stmString;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel(); // porto il nesting level a 0
		//env.setClassOffset(-2);

		// Creo una nuova hashmap e la aggiugno alla symbol table
		HashMap<String, STEntry> hm = new HashMap<String, STEntry>();
		env.getST().add(hm);// Nuovo scope (della classe)

		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//int initialClassOffset = env.getClassOffset();
		for (ClassNode cn : classList) {
			env.getClassMap().put(cn.getId(), cn);
		}
		
		// Controlla la semantica per ogni dichiarazione di classe 
		for (ClassNode n : classList) {
			res.addAll(n.checkSemantics(env));
			//initialClassOffset -= n.getMethodList().size() + 1;
			//env.setClassOffset(initialClassOffset);
		}

		// Se ci sono lets
		//env.setOffset(env.getClassOffset());
		for (Node n : decList)
			res.addAll(n.checkSemantics(env));

		// Controlla l'espressione fuori 
		for (Node e : expList) {
			res.addAll(e.checkSemantics(env));
		}

		for (Node s : stmList) {
			res.addAll(s.checkSemantics(env));
		}
		// Lascia lo scope
		env.getST().remove(env.decNestLevel());
		//env.decNestLevel();

		return res;
	}

	@Override
	public Type typeCheck() {
		
		// TODO: se c'è tempo, fare refactoring delle chiamate
		//       ripetute di typeCheck() e l'if.
		//       e.g. checkForErrors(ArrayList<Node>), e pure
		//            checkForErrors(ArrayList< ArrayList<Node> >).
		Type type;
		for (ClassNode cl : classList) {
			type = cl.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for (Node dec : decList) {
			type = dec.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for (Node e : expList) {
			type = e.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for(Node s : stmList) {
			type = s.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		
		return new VoidType();
	}
	@Override
	public String codeGeneration() {
		String classes = "";
		
		for (ClassNode c : classList) {
			classes += "## VTABLE\n" + c.codeGeneration();
		}
		
		if ( ! decList.isEmpty()) {
			String declCode = "";
			for (Node dec : decList)
				declCode += dec.codeGeneration();
			
			// TODO pain in the ass exps and stms codegen in right order
			return "push 0\n" + "## DATA\n" + classes + "\n## LET\n" + declCode + "\n## IN\n"  /* + exp.codeGeneration()*/
			+ "halt\n" + FOOLlib.getCode();
		}
		
		return "push 0\n" + "## DATA\n" + classes + "halt\n" + FOOLlib.getCode();
		
	}


}
