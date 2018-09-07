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
	private ArrayList<Node> exps; // E' null se il corpo sono statements
	private ArrayList<Node> statements; // Questa è la lista di statements nel caso in cui
	// il corpo sono statements. 
	
	/* takes the list of declarations, the expressions and the statements*/
	public ProgLetInNode(ArrayList<Node> d, ArrayList<Node> exps, ArrayList<Node> stms) {
		this.declist = d;
		this.exps = exps;
		this.statements= stms;
	}

	public String toPrint(String indent) {
		String declString="";
		for (Node dec:declist)
			declString+=dec.toPrint(indent+"  ");
		
		String expString="";
		for (Node e : exps) {
			expString += e.toPrint(indent + "  ");
		}

			String stmString="";
			for (Node stm: statements)
				stmString+=stm.toPrint(indent+"  ");

		return indent+"ProgLetIn\n"
			   + declString
			   + "  "
			   + expString
			   + "  " 
			   + stmString; 
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel();
		HashMap<String,STEntry> hm = new HashMap<String,STEntry> ();
		env.getST().add(hm);
		//System.out.println("Lista di Hashmaps size entrando:" + env.getST().size());

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		env.setOffset(-2); // Code generation stuff


		//check semantics in the dec list
		//if there are children then check semantics for every child and save the results
		for(Node n : declist) {
			res.addAll(n.checkSemantics(env));
		}
		
		//check semantics in the exp body or stms body
		for (Node e : exps) {
			res.addAll(e.checkSemantics(env));
		}
		
		for(Node s : statements)
			res.addAll(s.checkSemantics(env));
		
		//clean the scope, we are leaving a let scope
		env.getST().remove(env.decNestLevel());

		//System.out.println("Lista di Hashmaps size uscendo:" + env.getST().size());


		//return the result
		return res;
	}

	public Type typeCheck () {
		for (Node dec:declist) {
			dec.typeCheck();
		}
		for (Node e : exps) {
			e.typeCheck();
		}
		for(Node s : statements) {
			s.typeCheck();
		}
		
		return new VoidType();
	}

	public String codeGeneration() {
		String declCode = "";
		for (Node dec:declist) {
			declCode += dec.codeGeneration();
		}
		
		String expsCode = "";
		for (Node e : exps) {
			expsCode += e.codeGeneration();
		}

		String stmsCode = "";
		for (Node s : statements) {
			stmsCode += s.codeGeneration();
		}
		return  "push 0\n"+ declCode + expsCode + stmsCode + "halt\n" + FOOLlib.getCode();
	} 



}  