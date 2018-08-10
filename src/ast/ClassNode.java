package ast;

import java.util.ArrayList;

import type.Type;
import util.Environment;
import util.SemanticError;

public class ClassNode implements Node {
	
	private String id;
	private ArrayList<Node> fieldList;
	private ArrayList<Node> methodList;

	
	public ClassNode(String name) {
		this.id = name;
	}

	@Override
	public String toPrint(String indent) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
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

	
}
