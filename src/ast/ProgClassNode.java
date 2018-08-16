package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ClassType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class ProgClassNode implements Node {
	
	private ArrayList<ClassNode> classList;
	private ArrayList<Node> decList;
	private Node exp;
	
	public ProgClassNode(ArrayList<ClassNode> l, ArrayList<Node> d, Node e) {
		classList = l;
		decList = d;
		exp = e;
	}
	
	@Override
	public String toPrint(String indent) {
		String fieldstr = "", decstr = "";

		if (decList.size() > 0) {
			for (Node d : decList)
				decstr += d.toPrint(indent + "  ");
		}

		for (ClassNode c : classList)
			fieldstr += c.toPrint(indent + "  ");

		return indent + "ProgClassNode\n" + fieldstr + decstr + exp.toPrint(indent + "  ");
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
		
		// Controlla la semantica per ogni dichiarazione nella classe 
		for (ClassNode n : classList) {
			res.addAll(n.checkSemantics(env));
			//initialClassOffset -= n.getMethodList().size() + 1;
			//env.setClassOffset(initialClassOffset);
		}

		// Se ci sono lets
		if (decList.size() > 0) {
			//env.setOffset(env.getClassOffset());

			for (Node n : decList)
				res.addAll(n.checkSemantics(env));
		}

		if (res.size() > 0)
			return res; // Se ci sono errori ci possiamo già fermare

		// Controlla l'espressione fuori 
		res.addAll(exp.checkSemantics(env));

		// Lascia lo scope
		env.getST().remove(env.decNestLevel());
		//env.decNestLevel();

		return res;
	}

	@Override
	public Type typeCheck() {
		// TODO da fare
		return new ClassType("A");
	}
	@Override
	public String codeGeneration() {
		// TODO Auto-generated method stub
		return null;
	}


}
