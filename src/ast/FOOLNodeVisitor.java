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
import parser.FOOLParser.TermContext;
import parser.FOOLParser.TypeContext;
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
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

public class FOOLNodeVisitor extends FOOLBaseVisitor<Node> {

	private ArrayList<Node> getContextBody(ParserRuleContext ctx) {
		ArrayList<Node> contextBody = new ArrayList<>();

		ctx.children.forEach(node ->
		{
			if (node instanceof FOOLParser.StmsContext) {
				for (StmContext stm : ((FOOLParser.StmsContext) node).stm()) {
					contextBody.add(visit(stm));
				}
			} else if (node instanceof FOOLParser.ExpContext) {
				contextBody.add(visit(node));
			}
		});

		return contextBody;
	}
	
	// Prog class
	@Override
	public Node visitClassExp(ClassExpContext ctx) {

		ArrayList<ClassNode> classes = new ArrayList<>();
		ArrayList<Node> declarations = new ArrayList<>();
		ArrayList<Node> fullBody = new ArrayList<>();
		
		// Visita tutte le classi
		for (ClassdecContext cc : ctx.classdec())
			classes.add((ClassNode) visit(cc));

		// Se c'è let
		
		if (ctx.let() != null) {
			for (DecContext dc : ctx.let().dec())
				declarations.add(visit(dc));

            fullBody = getContextBody(ctx);
		}

		return new ProgClassNode(classes, declarations, fullBody);
	
	}


    // PROG let
    @Override
    public Node visitLetInExp(LetInExpContext ctx) {

        //resulting node of the right type
        ProgLetInNode res;

        //list of declarations, exps and stms in @res
        ArrayList<Node> declarations = new ArrayList<>();
        ArrayList<Node> fullBody;

        for (DecContext dc : ctx.let().dec()) {
            declarations.add(visit(dc));
        }

        fullBody = getContextBody(ctx);

        res = new ProgLetInNode(declarations, fullBody);
        //build @res accordingly with the result of the visits to its content

        return res;
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
			f.setMethod(true); // In questo caso la funzione è un metodo
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

        return new MethodCallNode(
                ctx.ID(1).getText(),
                args,
                new IdNode(ctx.ID(0).getText()));
	}

	@Override
	public Node visitNewExp(NewExpContext ctx) {
		ConstructorNode constr;

		ArrayList<Node> args = new ArrayList<Node>();

		for (ExpContext exp : ctx.exp())
			args.add(visit(exp));

		constr = new ConstructorNode(ctx.ID().getText(), args);

		return constr;
	}

	@Override
	public Node visitNullExp(NullExpContext ctx) {
		return new NullNode();
	}

	// PROG exp
	@Override
	public Node visitSingleExp(SingleExpContext ctx) {
		ProgExpNode prog = new ProgExpNode(visit(ctx.exp()));
		return prog;
	}




	@Override
	public Node visitAsmStm(AsmStmContext ctx) {
		return new StmAsmNode(ctx.ID().getText(), visit(ctx.body));
	}


	@Override
	public Node visitIfStm(IfStmContext ctx) {

		Node condExp = visit(ctx.cond);

        List<Node> thenStms = new ArrayList<>();
        List<Node> elseStms = new ArrayList<>();

        ctx.thenBranch.stm().forEach(stm -> thenStms.add(visit(stm)));
        ctx.elseBranch.stm().forEach(stm -> elseStms.add(visit(stm)));
		
		return new IfStmsNode(condExp, thenStms, elseStms);
	}


    @Override
	public Node visitVarasm(VarasmContext ctx) {
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
		ArrayList<Node> innerDec = new ArrayList<>();

		//check whether there are actually nested decs
		if (ctx.letVar() != null) {
			//if there are visit each dec and add it to the @innerDec list
			for (VarasmContext dc : ctx.letVar().varasm())
				innerDec.add(visit(dc));
		}

		ArrayList<Node> fullBody;
		fullBody = getContextBody(ctx);

		FunNode res = new FunNode(ctx.ID().getText(),
				returnType,
				parTypes,
				innerDec,
				fullBody);

		return res;
	}

	@Override
	public Node visitType(TypeContext ctx) {

		if (ctx.INT() != null) return new IntType();

		else if (ctx.BOOL() != null) return new BoolType();

		else if (ctx.VOID() != null) return new VoidType();

		else {
			return new ClassType(ctx.getText());
		}

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

		// ProgNode -> exp -> term -> factor -> IntNode
		// ProgNode -> IntNode

		//there is no need to perform a check here, the lexer ensures this text is an int
		int val = Integer.parseInt(ctx.INTEGER().getText());
		return new IntNode(ctx.MINUS() == null ? val : -val);
	}

	@Override
	public Node visitBoolVal(BoolValContext ctx) {

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

		return new IdNode(ctx.ID().getText(), ctx.MINUS() !=null, ctx.NOT() != null);
	}

	@Override
	public Node visitFunExp(FunExpContext ctx) {

		//this corresponds to a function invocation

		ParserRuleContext prc = ctx;
        boolean isMethod = false;
        String className = "";

        while (prc.getParent() != null) {
            if (prc.getText().contains("class")) {
                isMethod = true;
                className = ((ClassdecContext) prc).ID(0).getText();
                break;
            }


            prc = prc.getParent();
        }

		//declare the result
		Node res;

		//get the invocation arguments
		ArrayList<Node> args = new ArrayList<>();

		for (ExpContext exp : ctx.exp())
			args.add(visit(exp));

		//instantiate the invocation3
        if (isMethod) res = new NestedMethodCallNode(className, ctx.ID().getText(), args);
        else		  res = new CallNode(ctx.ID().getText(), args);

		return res;
	}


}
