package parser;

import ast.Node;
import ast.ParNode;
import ast.ProgLetInNode;
import ast.ProgNode;
import ast.VarNode;
import ast.IntOpsNode;
import ast.LogicOpsNode;

import parser.FOOLParser.BaseExpContext;
import parser.FOOLParser.BoolValContext;
import parser.FOOLParser.DecContext;
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

import type.BoolType;
import type.ClassType;
import type.IntType;
import type.Type;
import type.VoidType;

import static ast.IntOpsNode.IntOpsType.*;
import static ast.LogicOpsNode.LogicOpsType.*;

import java.util.ArrayList;

import ast.BoolNode;
import ast.CallNode;
import ast.FunNode;
import ast.IdNode;
import ast.IfNode;
import ast.IntNode;

public class FOOLNodeVisitor extends FOOLBaseVisitor<Node> {

	@Override
	public Node visitSingleExp(SingleExpContext ctx) {
		System.out.println("visitSingleExp");
		ProgNode prog = new ProgNode(visit(ctx.exp()));
		return prog;
	}

	@Override
	public Node visitLetInExp(LetInExpContext ctx) {
		System.out.println("visitLetInExp");

		//resulting node of the right type
		ProgLetInNode res;

		//list of declarations in @res
		ArrayList<Node> declarations = new ArrayList<Node>();

		//visit all nodes corresponding to declarations inside the let
		//context and store them in @declarations
		//notice that the ctx.let().dec() method returns a list, this is
		//because of the use of * or + in the grammar
		//antlr detects this is a group and therefore returns a list
		for (DecContext dc : ctx.let().dec()) {
			//System.out.println(dc.toString());
			declarations.add(visit(dc));
		}

		//visit exp context
		Node exp = visit(ctx.exp());

		//build @res accordingly with the result of the visits to its
		//content
		res = new ProgLetInNode(declarations, exp);

		return res;
	}

	@Override
	public Node visitVarasm(VarasmContext ctx) {
		System.out.println("visitVarasm");
		//declare the result node
		VarNode result;

		//visit the type
		Type type = (Type) visit(ctx.vardec().type());
		System.out.println(type.toPrint(""));

		//visit the exp
		Node expNode = visit(ctx.exp());

		//build the varNode
		return new VarNode(ctx.vardec().ID().getText(), type, expNode);
	}

	@Override
	public Node visitFun(FunContext ctx) {
		System.out.println("visitFun");
		//initialize @res with the visits to the type and its ID
		FunNode res = new FunNode(ctx.ID().getText(), (Type) visit(ctx.type()));

		//add argument declarations
		//we are getting a shortcut here by constructing directly the ParNode
		//this could be done differently by visiting instead the VardecContext
		for (VardecContext vc : ctx.vardec())
			res.addPar(new ParNode(vc.ID().getText(), (Type) visit(vc.type())));

		//add body
		//create a list for the nested declarations
		ArrayList<Node> innerDec = new ArrayList<Node>();

		//check whether there are actually nested decs
		if (ctx.let() != null) {
			//if there are visit each dec and add it to the @innerDec list
			for (DecContext dc : ctx.let().dec())
				innerDec.add(visit(dc));
		}

		//get the exp body
		Node exp = visit(ctx.exp());

		//add the body and the inner declarations to the function
		res.addDecBody(innerDec, exp);

		return res;
	}

	@Override
	public Node visitType(TypeContext ctx) {
		System.out.println("visitType");

		if (ctx.INT() != null) return new IntType();

		else if (ctx.BOOL() != null) return new BoolType();

		else if (ctx.VOID() != null) return new VoidType();

		else return new ClassType();

	}

	@Override
	public Node visitExp(ExpContext ctx) {
		System.out.println("visitExp");
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
		System.out.println("visitTerm");
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
		System.out.println("visitFactor");
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
		System.out.println("visitIntVal");
		// notice that this method is not actually a rule but a named production #intVal

		//there is no need to perform a check here, the lexer ensures this text is an int
		return new IntNode(Integer.parseInt(ctx.INTEGER().getText()));
	}

	@Override
	public Node visitBoolVal(BoolValContext ctx) {
		System.out.println("visitBoolVal");
		//there is no need to perform a check here, the lexer ensures this text is a boolean
		boolean res = false;
		if (ctx.getText().startsWith("not")) {
			res = ! Boolean.parseBoolean(ctx.getText().substring(3, ctx.getText().length()));
			return new BoolNode(res);
		}
		else {
			return new BoolNode(Boolean.parseBoolean(ctx.getText()));
		}
	}

	@Override
	public Node visitBaseExp(BaseExpContext ctx) {

		System.out.println("visitBaseExp");
		//this is actually nothing in the sense that for the ast the parenthesis are not relevant
		//the thing is that the structure of the ast will ensure the operational order by giving
		//a larger depth (closer to the leafs) to those expressions with higher importance

		//this is actually the default implementation for this method in the FOOLBaseVisitor class
		//therefore it can be safely removed here

		return visit(ctx.exp());
	}

	@Override
	public Node visitIfExp(IfExpContext ctx) {
		System.out.println("visitIfExp");
		//create the resulting node
		IfNode res;

		//visit the conditional, then the then branch, and then the else branch
		//notice once again the need of named terminals in the rule, this is because
		//we need to point to the right expression among the 3 possible ones in the rule

		Node condExp = visit(ctx.cond);

		Node thenExp = visit(ctx.thenBranch);

		Node elseExp = visit(ctx.elseBranch);

		//build the @res properly and return it
		res = new IfNode(condExp, thenExp, elseExp);

		return res;
	}

	@Override
	public Node visitVarExp(VarExpContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("visitVarExp");
		return new IdNode(ctx.ID().getText());
	}

	@Override
	public Node visitFunExp(FunExpContext ctx) {
		System.out.println("visitFunExp");
		//this corresponds to a function invocation

		//declare the result
		Node res;

		//get the invocation arguments
		ArrayList<Node> args = new ArrayList<Node>();

		for (ExpContext exp : ctx.exp())
			args.add(visit(exp));

		//instantiate the invocation
		res = new CallNode(ctx.ID().getText(), args);

		return res;
	}


}
