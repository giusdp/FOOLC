import ast.FOOLNodeVisitor;
import ast.Node;
import codeexecution.DispatchTable;
import codeexecution.VirtualMachine;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.FOOLLexer;
import parser.FOOLParser;
import svm.AssemblyNode;
import svm.AssemblyVisitor;
import svm.SVMLexer;
import svm.SVMParser;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;
import util.SyntaxErrorListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
            inputStream.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(2);
		}


        SyntaxErrorListener errorListener = new SyntaxErrorListener();

		FOOLLexer lexer = new FOOLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

		FOOLParser parser = new FOOLParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);

        // GENERAZIONE AST
        FOOLNodeVisitor visitor = new FOOLNodeVisitor();
        Node ast = visitor.visit(parser.prog()); //generazione AST



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
		System.out.println();

//		System.out.println("Visualizing AST...");
//		System.out.println(ast.toPrint(""));


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
				System.out.println("Code generated: " + fileName+".asm");
				System.out.println();
				
			} catch (IOException e) {
				System.err.println(e.toString());
				System.exit(-1);
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
			}
	        else {
	
		        VirtualMachine vm = new VirtualMachine(assembly);
		
		        try {
					vm.cpu();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println("Compiler Launcher: Errore in esecuzione virtual machine");
					System.err.println(e.getMessage());
				}
	        }
	        
	        try {
	        	inputSVMStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
