package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ArrowType;
import type.ClassType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;

public class MethodCallNode implements Node {
	
	private String id;
	private STEntry methodEntry;
	private ArrayList<Node> parList;
	private int nestLevel;
	private IdNode varNode;
	private String ownerClass;

	public MethodCallNode(String text, ArrayList<Node> args, Node sn) {
		id = text;
		parList = args;
		varNode = (IdNode) sn;
	}

	@Override
	public String toPrint(String indent) {
		String parlstr = "";

		for (Node par : parList)
			parlstr += par.toPrint(indent + "  ");

		return indent + "Call:" + id + " at nestlev " + nestLevel 
				+ "\n" + methodEntry.toPrint(indent + "  ") + parlstr;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();
		
		//TODO

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
