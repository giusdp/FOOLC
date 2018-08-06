package ast;

import java.util.ArrayList;

import type.BoolType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;

public class LogicOpsNode implements Node {

	public static enum LogicOpsType {EQUAL, GREATER, LESS, GREATEREQUAL, LESSEQUAL, OR, AND};

	private Node left, right;

	private String opToPrint, opToCode;
	private LogicOpsType opType;

	public LogicOpsNode(LogicOpsType operatorType, Node l, Node r) {
		left = l;
		right = r;
		opType = operatorType;

		switch(operatorType) {
		case EQUAL:
			opToPrint = "Equal";
			opToCode = "beq";
			break;
		case GREATER:
			opToPrint = "Greater";
			opToCode = "bg";
			break;
		case LESS:
			opToPrint = "Less";
			opToCode = "bl";
			break;
		case GREATEREQUAL:
			opToPrint = "GreaterEqual";
			opToCode = "bgeq";
			break;
		case LESSEQUAL:
			opToPrint = "LessEqual";
			opToCode = "bleq";
			break;
		case OR:
			opToPrint = "Or";
			opToCode = "or";
			break;
		case AND:
			opToPrint = "And";
			opToCode = "and";
			break;
		default:
			opToPrint = "Unhandled Int Operation Type";
			opToCode = "Unhandled Operation";
			break;
		}
	}

	@Override
	public String toPrint(String s) {
		return s + opToPrint + "\n" + left.toPrint(s + "  ") + right.toPrint(s + "  ");
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		//create the result
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//check semantics in the left and in the right exp

		res.addAll(left.checkSemantics(env));
		res.addAll(right.checkSemantics(env));

		return res;
	}

	@Override
	public Type typeCheck() {
		Type l = left.typeCheck();
		Type r = right.typeCheck();
		if (! ( FOOLlib.isSubtype(l,r) || FOOLlib.isSubtype(r,l) ) ) {
			System.out.println("ERROR: Incompatible types in " + opToPrint + "\n");
			System.exit(1);
		}

		// Se sono dello stesso tipo e l'operazione è and oppure or, allora bisogna
		// controllare che left e right siano di tipo bool. Se left non lo è (quindi manco right)
		// allora è errore!
		if (opType == LogicOpsType.AND || opType == LogicOpsType.OR) {
			if (! FOOLlib.isSubtype(l, new BoolType())) {
				System.out.println("ERROR: Non-Bool types used in" + opToPrint + "\n");
				System.exit(1);
			}
		}
		return new BoolType();
	}

	@Override
	public String codeGeneration() {
		String l1 = FOOLlib.freshLabel();
		String l2 = FOOLlib.freshLabel();
		return left.codeGeneration() + right.codeGeneration() + opToCode + " " 
		+ l1 + "\n" + "push 0\n" + "b " + l2 + "\n" + l1
		+ ":\n" + "push 1\n" + l2 + ":\n";
	}


}
