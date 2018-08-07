package util;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SyntaxErrorListener extends BaseErrorListener {
	
	private int numErrors = 0;
	
	public int getNumErrors() {
		return numErrors;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
			int line, int charPositionInLine,
			String msg, RecognitionException e) {
		
		List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        System.err.println("Rule stack: " + stack);
        System.err.println("Line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg);
        numErrors++;
	}

	static class SyntaxErrorException extends Exception {
        // Parameterless Constructor
        public SyntaxErrorException() {
        }

        // Constructor that accepts a message
        public SyntaxErrorException(String message) {
            super(message);
        }
    }
	
}
