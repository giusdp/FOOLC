package ast;

import java.util.ArrayList;

import type.IntType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;

public class IntOpsNode implements Node {

	public static enum IntOpsType {PLUS, MINUS, MULT, DIVISION};

	private Node left, right;
	
	private String opToPrint, opToCode;

	public IntOpsNode(IntOpsType arithmeticOpsType , Node l, Node r) {
		left = l;
		right = r;

		switch(arithmeticOpsType) {
		case PLUS:
			opToPrint = "Plus";
			opToCode = "add";
			break;
		case MINUS:
			opToPrint = "Minus";
			opToCode = "sub";
			break;
		case MULT:
			opToPrint = "Mult";
			opToCode = "mult";
			break;
		case DIVISION:
			opToPrint = "Div";
			opToCode = "div";
			break;
		default:
			opToPrint = "Unhandled Int Operation Type";
			opToCode = "Unhandled Operation";
			break;
		}
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
	public String toPrint(String s) {
		return s + opToPrint + "\n" + left.toPrint(s + "  ") + right.toPrint(s + "  ");
	}

	@Override
	public Type typeCheck() {
        if (! ( FOOLlib.isSubtype(left.typeCheck(),new IntType()) &&
                FOOLlib.isSubtype(right.typeCheck(),new IntType()) ) ) {
          System.out.println("ERROR: Non integers in "+opToPrint+"\n");
          System.exit(1);
        }
        return new IntType();
	}

	@Override
	public String codeGeneration() {
		return left.codeGeneration() + right.codeGeneration() + opToCode + "\n";
	}

}
