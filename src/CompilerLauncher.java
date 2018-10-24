import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ast.FOOLNodeVisitor;
import ast.Node;
import codeexecution.DispatchTable;
import codeexecution.VirtualMachine;
import parser.FOOLLexer;
import parser.FOOLParser;
import svm.AssemblyNode;
import svm.AssemblyVisitor;
import svm.SVMLexer;
import svm.SVMParser;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;
import util.SyntaxErrorListener;
import type.ErrorType;
import type.Type;

/**
 * @author Giuseppe
 *
 */
public class CompilerLauncher {
	

	static boolean doCodeGen = true;

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
			System.err.println("\nThe program was not in the right format."
					+ " Exiting the compilation process now.");
			System.exit(1);
		}

		Environment env = new Environment();
		FOOLlib.setEnv(env);
		
		System.out.println("Perfoming Check Semantics...");
		ArrayList<SemanticError> err = ast.checkSemantics(env);
		
		if (err.size() > 0) {
			System.err.println("Check Semantics FAILED");
			System.err.println("You had: " + err.size() + " error(s):");
			for (SemanticError e : err) {
				System.err.println("\t" + e);				
			}
			System.exit(1);
		}
		System.out.println("Check Semantics ok!");

		System.out.println("Visualizing AST...");
		System.out.println(ast.toPrint(""));


		System.out.println("Perfoming Type Checking...");
		Type type = ast.typeCheck(); //type-checking bottom-up 


		if (type instanceof ErrorType) {
			System.err.println("Type Checking FAILED.");
			System.err.println(type.toPrint("  "));
			System.exit(2);
		} 
		else {
			System.out.println(type.toPrint("Type checking succesful! Type of the program is: "));
		}

		if (!doCodeGen) {
			System.err.println("Code generation disabled!");
		}
		else {

			try {
				// CODE GENERATION  prova.fool.asm
				String code = ast.codeGeneration();
	            code += "\n" + DispatchTable.codeGeneration();
				BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
				out.write(code);
				out.close();
			} catch (IOException e) {
				System.out.println(e.toString());
				System.exit(-1);
			}
			System.out.println("Code generated: " + fileName+".asm");
			System.out.println();
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//************ LETTURA ED ESECUZIONE CODICE SVM *****************
		
		FileInputStream inputSVMStream = null;
		try {
			inputSVMStream = new FileInputStream(fileName+".asm");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("\nERROR. No file found with the name " + fileName+".asm");
			System.exit(1);
		}

		ANTLRInputStream inputSVM = null;
		try {
			inputSVM = new ANTLRInputStream(inputSVMStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(2);
		}

        SVMLexer lexerSVM = new SVMLexer(inputSVM);
        CommonTokenStream tokensSVM = new CommonTokenStream(lexerSVM);
        SVMParser parserSVM = new SVMParser(tokensSVM);
        parserSVM.removeErrorListeners();
        parserSVM.addErrorListener(errorListener);
        
        // TODO Implement visitor pattern for svm
        AssemblyVisitor assemblyVisitor = new AssemblyVisitor();
		List<AssemblyNode> assembly = assemblyVisitor.buildCodeList(parserSVM.assembly()); //generazione lista delle istruzioni assemblu

//		System.out.println("Token identifiers");
//		for (AssemblyNode an : assembly) {
//			System.out.println(an.getInstruction());
//		}
		
        if (errorListener.getNumErrors() > 0) {
			System.err.println("\nThe SVM program was not in the right format."
					+ " Exiting the compilation process now.");
			System.exit(1);
		}

        VirtualMachine vm = new VirtualMachine(assembly);

        try {
			vm.cpu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Compiler Launcher: Errore in esecuzione virtual machine");
			e.printStackTrace();
		}

        try {
        	inputSVMStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
