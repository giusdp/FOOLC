package ast;

import parser.FOOLBaseVisitor;
import parser.FOOLParser;
import parser.FOOLParser.AsmStmContext;
import parser.FOOLParser.BaseExpContext;
import parser.FOOLParser.BoolValContext;
import parser.FOOLParser.ClassExpContext;
import parser.FOOLParser.ClassdecContext;
import parser.FOOLParser.DecContext;
import parser.FOOLParser.ExpContext;
import parser.FOOLParser.FactorContext;
import parser.FOOLParser.FunContext;
import parser.FOOLParser.FunDeclarationContext;
import parser.FOOLParser.FunExpContext;
import parser.FOOLParser.IfExpContext;
import parser.FOOLParser.IfStmContext;
import parser.FOOLParser.IntValContext;
import parser.FOOLParser.LetContext;
import parser.FOOLParser.LetInExpContext;
import parser.FOOLParser.MethodExpContext;
import parser.FOOLParser.NewExpContext;
import parser.FOOLParser.NullExpContext;
import parser.FOOLParser.ProgContext;
import parser.FOOLParser.SingleExpContext;
import parser.FOOLParser.StmContext;
import parser.FOOLParser.StmsContext;
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
import util.FOOLlib.RuleName;

import static ast.IntOpsNode.IntOpsType.*;
import static ast.LogicOpsNode.LogicOpsType.*;

import java.util.ArrayList;

public class FOOLNodeVisitor extends FOOLBaseVisitor<Node> {

	public ArrayList<Node> getContextBody(ProgContext ctx) {
		ArrayList<Node> contextBody = new ArrayList<Node>();

		ctx.children.forEach(node ->
		{
			if (node instanceof FOOLParser.StmsContext) {
				for (StmContext stm : ((FOOLParser.StmsContext) node).stm()) {
					contextBody.add(visit(stm));
				}
			} else if (node instanceof FOOLParser.ExpContext) {
				contextBody.add(visit( (FOOLParser.ExpContext) node));
			}
		});

		return contextBody;
	}
	
	// Prog class
	@Override
	public Node visitClassExp(ClassExpContext ctx) {

		ArrayList<ClassNode> classes = new ArrayList<ClassNode>();
		ArrayList<Node> declarations = new ArrayList<Node>();
		ArrayList<Node> expressions = new ArrayList<Node>();
		ArrayList<Node> statements = new ArrayList<Node>();
		ArrayList<Node> contextBody = new ArrayList<>();
		
		// Visita tutte le classi
		for (ClassdecContext cc : ctx.classdec())
			classes.add((ClassNode) visit(cc));

		// Se c'è let
		
		if (ctx.let() != null) {
			for (DecContext dc : ctx.let().dec())
				declarations.add(visit(dc));
			
			for (ExpContext e : ctx.exp())
				expressions.add(visit(e));
			
			for (StmsContext stm : ctx.stms()) {
				//System.out.println(dc.toString());
				for (StmContext s : stm.stm()) {
					statements.add(visit(s));	
				}
			}
			contextBody = getContextBody(ctx);
		}

		return new ProgClassNode(classes, declarations, expressions, statements, contextBody);
	
	}
	
	

	@Override
	public Node visitClassdec(ClassdecContext ctx) {
				
		ArrayList<Node> fieldList = new ArrayList<>();
		ArrayList<Node> methodList = new ArrayList<>();
		
		
		// Visita i campi (le variabili dichiarate)
		for (VardecContext vc : ctx.vardec()) {
			Type type = (Type) visit(vc.type());
			fieldList.add(new ParNode(vc.ID().getText(), type));
		}

		// visit all class's methods
		for (FunContext fc : ctx.fun()) {
			FunNode f = (FunNode) visit(fc);
			methodList.add(f);
		}
		
		// ID(0) è il nome della classe. ID(1) è la superclasse. 
		// ID(n) serve per accedere ai due ID presenti nella regola
		ClassNode c = new ClassNode(ctx.ID(0).getText(), fieldList, methodList);
		
		if (ctx.ID(1) != null) {
			c.setSuperClassName(ctx.ID(1).getText());
		}
		
		return c;
	}
		
	@Override
	public Node visitMethodExp(MethodExpContext ctx) {
		ArrayList<Node> args = new ArrayList<Node>();

		for (ExpContext exp : ctx.exp())
			args.add(visit(exp));
		
		MethodCallNode m = new MethodCallNode(
				ctx.ID(1).getText(), 
				args, 
				new IdNode(ctx.ID(0).getText()));

		return m;
	}

	@Override
	public Node visitNewExp(NewExpContext ctx) {
		//System.out.println("VISIT NEW");
		ConstructorNode constr;

		ArrayList<Node> args = new ArrayList<Node>();

		for (ExpContext exp : ctx.exp())
			args.add(visit(exp));

		constr = new ConstructorNode(ctx.ID().getText(), args);

		return constr;
	}

	@Override
	public Node visitNullExp(NullExpContext ctx) {
		// TODO Auto-generated method stub
		//System.out.println("VISIT NULL");
		return new NullNode();
	}

	// PROG exp
	@Override
	public Node visitSingleExp(SingleExpContext ctx) {
		ProgExpNode prog = new ProgExpNode(visit(ctx.exp()));
		return prog;
	}

	// PROG let
	@Override
	public Node visitLetInExp(LetInExpContext ctx) {

		//resulting node of the right type
		ProgLetInNode res;

		//list of declarations, exps and stms in @res
		ArrayList<Node> declarations = new ArrayList<>();
		ArrayList<Node> expressions = new ArrayList<>();
		ArrayList<Node> statements = new ArrayList<>();
		ArrayList<Node> fullBody = new ArrayList<>();
		
		//visit all nodes corresponding to declarations inside the let
		//context and store them in @declarations
		//notice that the ctx.let().dec() method returns a list, this is
		//because of the use of * or + in the grammar
		//antlr detects this is a group and therefore returns a list
		for (DecContext dc : ctx.let().dec()) {
			//System.out.println(dc.toString());
			declarations.add(visit(dc));
		}

		//visit exp and stms context
		for (ExpContext exp : ctx.exp()) {
			expressions.add(visit(exp));
		}
		for (StmsContext stm : ctx.stms()) {
			for (StmContext s : stm.stm()) {
				statements.add(visit(s));	
			}
		}
		
		fullBody = getContextBody(ctx);
		
		res = new ProgLetInNode(declarations, expressions, statements, fullBody);
		//build @res accordingly with the result of the visits to its
		//content

		return res;
	}



	@Override
	public Node visitAsmStm(AsmStmContext ctx) {
		StmAsmNode res = new StmAsmNode(ctx.ID().getText(), visit(ctx.body));
		return res;
	}


	@Override
	public Node visitIfStm(IfStmContext ctx) {

		//System.out.println("VISIT IF STM");
		//create the resulting node
		IfNode ifnode = null;

		//visit the conditional, then the then branch, and then the else branch
		//notice once again the need of named terminals in the rule, this is because
		//we need to point to the right expression among the 3 possible ones in the rule

		Node condExp = visit(ctx.cond);
		
		Node thenExp = visit(ctx.thenBranch);
		
		Node elseExp = visit(ctx.elseBranch);

		//build the @res properly and return it
		
		ifnode = new IfNode(condExp, thenExp, elseExp);
		return ifnode;
	}

	@Override
	public Node visitVarasm(VarasmContext ctx) {
		//System.out.println("VISIT VAR ASM");
		//declare the result node
		VarNode result;

		//visit the type
		Type type = (Type) visit(ctx.vardec().type());

		//visit the exp
		Node expNode = visit(ctx.exp());

		//build the varNode
		
		return new VarNode(ctx.vardec().ID().getText(), type, expNode);
	}

	@Override
	public Node visitFun(FunContext ctx) {

		//add argument declarations
		//we are getting a shortcut here by constructing directly the ParNode
		//this could be done differently by visiting instead the VardecContext
		ArrayList<Node> parTypes = new ArrayList<>();
		for (VardecContext vc : ctx.vardec()) {
			parTypes.add(new ParNode(vc.ID().getText(), (Type) visit(vc.type())));
			
		}
		
		//initialize @res with the visits to the type and its ID
		Type returnType = (Type) visit(ctx.type());

		//add body
		//create a list for the nested declarations
		ArrayList<Node> innerDec = new ArrayList<Node>();

		//check whether there are actually nested decs
		if (ctx.let() != null) {
			//if there are visit each dec and add it to the @innerDec list
			for (DecContext dc : ctx.let().dec())
				innerDec.add(visit(dc));
		}

		ArrayList<Node> statements = new ArrayList<Node>();
		ArrayList<Node> expressions = new ArrayList<Node>();
		
		for (ExpContext exp : ctx.exp()) {
			expressions.add(visit(exp));
		}
		for (StmsContext stm : ctx.stms()) {
			for (StmContext s : stm.stm()) {
				statements.add(visit(s));	
			}
		}
		
		// if (ctx.lastexp != null) expressions.add(visit(ctx.lastexp)); lastexp viene gia' preso nel for su ctx.exp()
		if (ctx.laststm != null) statements.add(visit(ctx.laststm));
		
		// Final line of function is either a stm or exp.
		RuleName returnRule = (ctx.lastexp  == null) ? RuleName.STM : RuleName.EXP;
		FunNode res = new FunNode(ctx.ID().getText(), returnType, returnRule, parTypes, innerDec, expressions, statements);

		return res;
	}

	@Override
	public Node visitType(TypeContext ctx) {

		//System.out.println("VISIT TYPE");
		if (ctx.INT() != null) return new IntType();

		else if (ctx.BOOL() != null) return new BoolType();

		else if (ctx.VOID() != null) return new VoidType();

		else {
			//System.out.println("VISITTYPE: CLASSTYPE. IN FOOLNODEVISITOR AAAAAAAAAAAAAAAAAAAAAA");
			return new ClassType(ctx.getText());
		}

	}

	@Override
	public Node visitExp(ExpContext ctx) {

		//System.out.println("VISIT EXP");
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

		//System.out.println("VISIT TERM");
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

		//System.out.println("VISIT FACTOR");
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

		//System.out.println("VISIT INT VAL");
		// notice that this method is not actually a rule but a named production #intVal

		// ProgNode -> exp -> term -> factor -> IntNode
		// ProgNode -> IntNode

		//there is no need to perform a check here, the lexer ensures this text is an int
		return new IntNode(Integer.parseInt(ctx.INTEGER().getText()));
	}

	@Override
	public Node visitBoolVal(BoolValContext ctx) {

		//System.out.println("VISIT BOOL VAL");
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
		//this is actually nothing in the sense that for the ast the parenthesis are not relevant
		//the thing is that the structure of the ast will ensure the operational order by giving
		//a larger depth (closer to the leafs) to those expressions with higher importance

		//this is actually the default implementation for this method in the FOOLBaseVisitor class
		//therefore it can be safely removed here

		return visit(ctx.exp());
	}

	@Override
	public Node visitIfExp(IfExpContext ctx) {

		//System.out.println("VISIT IF EXP");
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

		//System.out.println("VISIT VAR EXP");
		return new IdNode(ctx.ID().getText());
	}

	@Override
	public Node visitFunExp(FunExpContext ctx) {

		//System.out.println("VISIT FUN EXP");
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
