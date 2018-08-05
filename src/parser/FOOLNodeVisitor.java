package parser;

import ast.Node;
import ast.ProgNode;
import ast.IntOpsNode;
import ast.LogicOpsNode;

import parser.FOOLParser.BaseExpContext;
import parser.FOOLParser.BoolValContext;
import parser.FOOLParser.ExpContext;
import parser.FOOLParser.FactorContext;
import parser.FOOLParser.FunContext;
import parser.FOOLParser.FunDeclarationContext;
import parser.FOOLParser.FunExpContext;
import parser.FOOLParser.IfExpContext;
import parser.FOOLParser.IntValContext;
import parser.FOOLParser.LetContext;
import parser.FOOLParser.LetInExpContext;
import parser.FOOLParser.SingleExpContext;
import parser.FOOLParser.TermContext;
import parser.FOOLParser.TypeContext;
import parser.FOOLParser.VarAssignmentContext;
import parser.FOOLParser.VarExpContext;
import parser.FOOLParser.VarasmContext;
import parser.FOOLParser.VardecContext;

import static ast.IntOpsNode.IntOpsType.*;
import static ast.LogicOpsNode.LogicOpsType.*;

import ast.BoolNode;
import ast.IntNode;

public class FOOLNodeVisitor extends FOOLBaseVisitor<Node> {

	@Override
	public Node visitSingleExp(SingleExpContext ctx) {
		ProgNode prog = new ProgNode(visit(ctx.exp()));
		return prog;
	}

	@Override
	public Node visitLetInExp(LetInExpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLetInExp(ctx);
	}

	@Override
	public Node visitLet(LetContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLet(ctx);
	}

	@Override
	public Node visitVardec(VardecContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVardec(ctx);
	}

	@Override
	public Node visitVarasm(VarasmContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarasm(ctx);
	}

	@Override
	public Node visitFun(FunContext ctx) {
		// TODO Auto-generated method stub
		return super.visitFun(ctx);
	}

	@Override
	public Node visitVarAssignment(VarAssignmentContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarAssignment(ctx);
	}

	@Override
	public Node visitFunDeclaration(FunDeclarationContext ctx) {
		// TODO Auto-generated method stub
		return super.visitFunDeclaration(ctx);
	}

	@Override
	public Node visitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		return super.visitType(ctx);
	}

	@Override
	public Node visitExp(ExpContext ctx) {
		//check whether this is a simple or binary expression
		//notice here the necessity of having named elements in the grammar
		if (ctx.right == null) {
			//it is a simple expression
			return visit(ctx.left);
		} else if (ctx.MINUS() == null) {
			//it is a binary expression, you should visit left and right
			return new IntOpsNode(PLUS, visit(ctx.left), visit(ctx.right));
		} else {
			return new IntOpsNode(MINUS, visit(ctx.left), visit(ctx.right));
		}
	}

	@Override
	public Node visitTerm(TermContext ctx) {
		//check whether this is a simple or binary expression
		//notice here the necessity of having named elements in the grammar
		if (ctx.right == null) {
			//it is a simple expression
			return visit(ctx.left);
		} else if (ctx.DIVISION() == null) {
			//it is a binary expression, you should visit left and right
			return new IntOpsNode(MULT, visit(ctx.left), visit(ctx.right));
		} else {
			return new IntOpsNode(DIVISION, visit(ctx.left), visit(ctx.right));
		}
	}

	@Override
	public Node visitFactor(FactorContext ctx) {
		//check whether this is a simple or binary expression
		//notice here the necessity of having named elements in the grammar
		if (ctx.right == null) {
			//it is a simple expression
			return visit(ctx.left);
		}
		else {
			//it is a binary expression, you should visit left and right
			if (ctx.GREATER() != null) {
				return new LogicOpsNode(GREATER, visit(ctx.left), visit(ctx.right));
			} else if (ctx.LESS() != null) {
				return new LogicOpsNode(LESS, visit(ctx.left), visit(ctx.right));
			} else if (ctx.GREATEREQUAL() != null) {
				return new LogicOpsNode(GREATEREQUAL, visit(ctx.left), visit(ctx.right));
			} else if (ctx.LESSEQUAL() != null) {
				return new LogicOpsNode(LESSEQUAL, visit(ctx.left), visit(ctx.right));
			} else if (ctx.OR() != null) {
				return new LogicOpsNode(OR, visit(ctx.left), visit(ctx.right));
			} else if (ctx.AND() != null) {
				return new LogicOpsNode(AND, visit(ctx.left), visit(ctx.right));
			}
			else {
				return new LogicOpsNode(EQUAL, visit(ctx.left), visit(ctx.right));
			}
		}
	}

	@Override
	public Node visitIntVal(IntValContext ctx) {
		// notice that this method is not actually a rule but a named production #intVal

		//there is no need to perform a check here, the lexer ensures this text is an int
		return new IntNode(Integer.parseInt(ctx.INTEGER().getText()));
	}

	@Override
	public Node visitBoolVal(BoolValContext ctx) {
		//there is no need to perform a check here, the lexer ensures this text is a boolean
		return new BoolNode(Boolean.parseBoolean(ctx.getText()));
	}

	@Override
	public Node visitBaseExp(BaseExpContext ctx) {

		//this is actually nothing in the sense that for the ast the parenthesis are not relevant
		//the thing is that the structure of the ast will ensure the operational order by giving
		//a larger depth (closer to the leafs) to those expressions with higher importance

		//this is actually the default implementation for this method in the FOOLBaseVisitor class
		//therefore it can be safely removed here

		return visit(ctx.exp());
	}

	@Override
	public Node visitIfExp(IfExpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitIfExp(ctx);
	}

	@Override
	public Node visitVarExp(VarExpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitVarExp(ctx);
	}

	@Override
	public Node visitFunExp(FunExpContext ctx) {
		// TODO Auto-generated method stub
		return super.visitFunExp(ctx);
	}


}
