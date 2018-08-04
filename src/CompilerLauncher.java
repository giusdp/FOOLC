import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ast.FoolVisitorImpl;
import ast.Node;
import ast.types.BottomTypeNode;
import ast.types.ClassTypeNode;
import parser.FOOLLexer;
import parser.FOOLParser;
import util.Environment;
import util.SemanticError;
import util.SyntaxErrorListener;

/**
 * 
 */

/**
 * @author Giuseppe
 *
 */
public class CompilerLauncher {

	static boolean makeCodeGen = false;

	public static void main(String[] args) {

		String fileName = "prova.fool";

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("\nERROR. No file found with the given name.\n");
			System.exit(1);
		}

		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.toString());
			System.exit(2);
		}

		FOOLLexer lexer = new FOOLLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		SyntaxErrorListener errorListener = new SyntaxErrorListener();
		FOOLParser parser = new FOOLParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);


		// GENERAZIONE AST
		FoolVisitorImpl visitor = new FoolVisitorImpl();
		Node ast = visitor.visit(parser.prog()); //generazione AST 
		if (errorListener.getNumErrors() > 0) {
			System.out.println("The program was not in the right format."
					+ " Exiting the compilation process now");
			System.exit(3);
		}

		Environment env = new Environment();
		
		ArrayList<SemanticError> err = ast.checkSemantics(env);
		if (err.size() > 0) {
			System.out.println("You had: " + err.size() + " error(s):");
			for (SemanticError e : err) {
				System.out.println("\t" + e);				
			}
			System.exit(1);
		}


		System.out.println("Visualizing AST...");
		System.out.println(ast.toPrint(""));


		Node type = ast.typeCheck(env); //type-checking bottom-up 


		if (type instanceof BottomTypeNode) {
			System.out.println("Type checking of the program not successful.");
			System.exit(2);
		}

		if (makeCodeGen) {

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
