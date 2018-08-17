import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ast.FOOLNodeVisitor;
import ast.Node;
import parser.FOOLLexer;
import parser.FOOLParser;
import util.Environment;
import util.SemanticError;
import util.SyntaxErrorListener;

import type.Type;

/**
 * 
 */

/**
 * @author Giuseppe
 *
 */
public class CompilerLauncher {
	

	static boolean doCodeGen = false;

	public static void main(String[] args) {

		String fileName = "tests/prova.fool";

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("\nERROR. No file found with the given name.\n");
			System.exit(1);
		}

		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(2);
		}

		FOOLLexer lexer = new FOOLLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		SyntaxErrorListener errorListener = new SyntaxErrorListener();
		FOOLParser parser = new FOOLParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);


		// GENERAZIONE AST
		FOOLNodeVisitor visitor = new FOOLNodeVisitor();
		Node ast = visitor.visit(parser.prog()); //generazione AST 
		
		if (errorListener.getNumErrors() > 0) {
			System.out.println("The program was not in the right format."
					+ " Exiting the compilation process now");
			System.exit(1);
		}

		Environment env = new Environment();
		
		System.out.println("Perfoming Check Semantics...");
		ArrayList<SemanticError> err = ast.checkSemantics(env);
		if (err.size() > 0) {
			System.out.println("You had: " + err.size() + " error(s):");
			for (SemanticError e : err) {
				System.out.println("\t" + e);				
			}
			System.exit(1);
		}
		System.out.println("Check Semantics ok!");

		//System.out.println("Visualizing AST...");
		//System.out.println(ast.toPrint(""));


		// TODO da passare l'env al typechecking 
		System.out.println("Perfoming Type Checking...");
		Type type = ast.typeCheck(); //type-checking bottom-up 

		System.out.println(type.toPrint("Type checking succesful! Type of the program is: "));

		/*if (type instanceof BottomTypeNode) {
			System.out.println("Type checking of the program not successful.");
			System.exit(2);
		}*/

		if (!doCodeGen) {
			System.out.println("Code generation disabled!");
		}
		else {

			try {
				// CODE GENERATION  prova.fool.asm
				String code = ast.codeGeneration();
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
				out.write(code);
				out.close();
			} catch (IOException e) {
				System.out.println(e.toString());
				System.exit(-1);
			}
			System.out.println("Code generated: " + fileName+".asm");

		}

		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
