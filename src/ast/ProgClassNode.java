package ast;

import java.util.ArrayList;
import java.util.HashMap;

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
		env.incNestLevel(); // nestingLevel is now 0
		env.setClassOffset(-2);

		// create a new hashmap and add it to the symbol table
		HashMap<String, STEntry> hm = new HashMap<String, STEntry>();
		env.getST().add(hm);

		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		int initialClassOffset = env.getClassOffset();
		// check semantics for every class declaration
		for (ClassNode n : classList) {
			res.addAll(n.checkSemantics(env));
			initialClassOffset -= n.getMethodList().size() + 1;
			env.setClassOffset(initialClassOffset);
		}

		// if there are lets
		if (decList.size() > 0) {
			env.setOffset(env.getClassOffset());

			for (Node n : decList)
				res.addAll(n.checkSemantics(env));
		}

		if (res.size() > 0)
			return res;

		//check semantics in the exp node
		res.addAll(exp.checkSemantics(env));

		// leave the class scope
		//env.getST().remove(env.decNestLevel());
		env.decNestLevel();

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


}
