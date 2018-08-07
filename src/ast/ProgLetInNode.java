package ast;
import java.util.ArrayList;
import java.util.HashMap;

import type.Type;
import type.VoidType;
import util.FOOLlib;
import util.Environment;
import util.STEntry;
import util.SemanticError;

/* Class representing a Let in instruction node */
public class ProgLetInNode implements Node {

	private ArrayList<Node> declist;
	private Node exp; // E' null se il corpo sono statements
	private ArrayList<Node> statements; // Questa è la lista di statements nel caso in cui
	// il corpo sono statements. 

	/* takes the list of declarations and the final expression */
	public ProgLetInNode (ArrayList<Node> d, Node e) {
		declist=d;
		exp=e;
		statements = null;
	}
	
	public ProgLetInNode (ArrayList<Node> d, ArrayList<Node> stms) {
		declist=d;
		exp=null;
		statements = stms;
	}

	public String toPrint(String s) {
		String declstr="";
		for (Node dec:declist)
			declstr+=dec.toPrint(s+"  ");
		
		String stmsstr="";
		for (Node stm: statements)
			stmsstr+=stm.toPrint(s+"  ");
		
		if (exp != null) return s+"ProgLetIn\n" + declstr + exp.toPrint(s+"  ") ; 
		else return s+"ProgLetIn\n" + declstr + " " + stmsstr ; 
		
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel();
		HashMap<String,STEntry> hm = new HashMap<String,STEntry> ();
		env.getST().add(hm);

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//check semantics in the dec list
		if(declist.size() > 0){
			env.setOffset(-2);
			//if there are children then check semantics for every child and save the results
			for(Node n : declist)
				res.addAll(n.checkSemantics(env));
		}

		//check semantics in the exp body or stms body
		if (exp != null) res.addAll(exp.checkSemantics(env));
		else {
			for(Node s : statements)
				res.addAll(s.checkSemantics(env));
		}
		//clean the scope, we are leaving a let scope
		env.getST().remove(env.decNestLevel());

		//return the result
		return res;
	}

	public Type typeCheck () {
		for (Node dec:declist) {
			dec.typeCheck();
		}
		if (exp != null) return exp.typeCheck();
		else {
			for(Node s : statements) {
				s.typeCheck();
			}
			return new VoidType();
		}
	}

	public String codeGeneration() {
		String declCode = "";
		for (Node dec:declist) {
			declCode += dec.codeGeneration();
		}
		return  "push 0\n"+ declCode + exp.codeGeneration() + "halt\n" + FOOLlib.getCode();
	} 



}  