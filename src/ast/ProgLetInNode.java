package ast;
import java.util.ArrayList;
import java.util.HashMap;

import type.ErrorType;
import type.Type;
import type.VoidType;
import util.FOOLlib;
import util.Environment;
import util.STEntry;
import util.SemanticError;

/* Class representing a Let in instruction node */
public class ProgLetInNode implements Node {

	private ArrayList<Node> declist;
	private ArrayList<Node> contextBody;
	
	/* takes the list of declarations, the expressions and the statements*/
	public ProgLetInNode(ArrayList<Node> d, ArrayList<Node> fullBody) {
		this.declist = d;
		this.contextBody = fullBody;
	}

	public String toPrint(String indent) {

		return FOOLlib.printProgNode(indent, new ArrayList<ClassNode>(), declist, contextBody);
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel();
		HashMap<String,STEntry> hm = new HashMap<String,STEntry> ();
		env.getST().add(hm);

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		env.setOffset(-2); // Code generation stuff


		//check semantics in the dec list
		//if there are children then check semantics for every child and save the results
		for(Node n : this.declist) {
			res.addAll(n.checkSemantics(env));
		}
		
		//check semantics in the exp body or stms body
		for (Node stm : this.contextBody) {
			res.addAll(stm.checkSemantics(env));
		}

		//clean the scope, we are leaving a let scope
		env.getST().remove(env.decNestLevel());

		//return the result
		return res;
	}

	public Type typeCheck () {
		Type type;
		
		for (Node dec : this.declist) {
			type = dec.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for (Node stm : this.contextBody) {
			type = stm.typeCheck();
			if (type instanceof ErrorType) return type;			
		}
		
		return new VoidType();
	}

	public String codeGeneration() {
		
		// TODO: more rigorous testing needed to ensure codeGen works.
		
		String declCode = "";
		for (Node dec : this.declist) {
			declCode += dec.codeGeneration();
		}
		
		String bodyCode = "";
		for (Node stm : this.contextBody) {
			bodyCode += stm.codeGeneration();
		}
		return  declCode + bodyCode + "halt\n" + FOOLlib.getCode();
	} 



}  