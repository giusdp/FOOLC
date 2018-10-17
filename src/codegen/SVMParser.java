// Generated from SVM.g4 by ANTLR 4.6
package codegen;

import java.util.HashMap;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PUSH=1, POP=2, ADD=3, SUB=4, MULT=5, DIV=6, STOREW=7, LOADW=8, BRANCH=9, 
		BRANCHEQ=10, BRANCHLESSEQ=11, JS=12, LOADRA=13, STORERA=14, LOADRV=15, 
		STORERV=16, LOADFP=17, STOREFP=18, COPYFP=19, LOADHP=20, STOREHP=21, PRINT=22, 
		HALT=23, NEW=24, COL=25, LABEL=26, NUMBER=27, WHITESP=28, ERR=29;
	public static final int
		RULE_assembly = 0;
	public static final String[] ruleNames = {
		"assembly"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'push'", "'pop'", "'add'", "'sub'", "'mult'", "'div'", "'sw'", 
		"'lw'", "'b'", "'beq'", "'bleq'", "'js'", "'lra'", "'sra'", "'lrv'", "'srv'", 
		"'lfp'", "'sfp'", "'cfp'", "'lhp'", "'shp'", "'print'", "'halt'", "'new'", 
		"':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PUSH", "POP", "ADD", "SUB", "MULT", "DIV", "STOREW", "LOADW", "BRANCH", 
		"BRANCHEQ", "BRANCHLESSEQ", "JS", "LOADRA", "STORERA", "LOADRV", "STORERV", 
		"LOADFP", "STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", "HALT", "NEW", 
		"COL", "LABEL", "NUMBER", "WHITESP", "ERR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	      
	    public int[] code = new int[VirtualMachine.CODESIZE];    
	    private int i = 0;
	    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
	    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
	        

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AssemblyContext extends ParserRuleContext {
		public Token n;
		public Token l;
		public List<TerminalNode> PUSH() { return getTokens(SVMParser.PUSH); }
		public TerminalNode PUSH(int i) {
			return getToken(SVMParser.PUSH, i);
		}
		public List<TerminalNode> POP() { return getTokens(SVMParser.POP); }
		public TerminalNode POP(int i) {
			return getToken(SVMParser.POP, i);
		}
		public List<TerminalNode> ADD() { return getTokens(SVMParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(SVMParser.ADD, i);
		}
		public List<TerminalNode> SUB() { return getTokens(SVMParser.SUB); }
		public TerminalNode SUB(int i) {
			return getToken(SVMParser.SUB, i);
		}
		public List<TerminalNode> MULT() { return getTokens(SVMParser.MULT); }
		public TerminalNode MULT(int i) {
			return getToken(SVMParser.MULT, i);
		}
		public List<TerminalNode> DIV() { return getTokens(SVMParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(SVMParser.DIV, i);
		}
		public List<TerminalNode> STOREW() { return getTokens(SVMParser.STOREW); }
		public TerminalNode STOREW(int i) {
			return getToken(SVMParser.STOREW, i);
		}
		public List<TerminalNode> LOADW() { return getTokens(SVMParser.LOADW); }
		public TerminalNode LOADW(int i) {
			return getToken(SVMParser.LOADW, i);
		}
		public List<TerminalNode> COL() { return getTokens(SVMParser.COL); }
		public TerminalNode COL(int i) {
			return getToken(SVMParser.COL, i);
		}
		public List<TerminalNode> BRANCH() { return getTokens(SVMParser.BRANCH); }
		public TerminalNode BRANCH(int i) {
			return getToken(SVMParser.BRANCH, i);
		}
		public List<TerminalNode> BRANCHEQ() { return getTokens(SVMParser.BRANCHEQ); }
		public TerminalNode BRANCHEQ(int i) {
			return getToken(SVMParser.BRANCHEQ, i);
		}
		public List<TerminalNode> BRANCHLESSEQ() { return getTokens(SVMParser.BRANCHLESSEQ); }
		public TerminalNode BRANCHLESSEQ(int i) {
			return getToken(SVMParser.BRANCHLESSEQ, i);
		}
		public List<TerminalNode> JS() { return getTokens(SVMParser.JS); }
		public TerminalNode JS(int i) {
			return getToken(SVMParser.JS, i);
		}
		public List<TerminalNode> LOADRA() { return getTokens(SVMParser.LOADRA); }
		public TerminalNode LOADRA(int i) {
			return getToken(SVMParser.LOADRA, i);
		}
		public List<TerminalNode> STORERA() { return getTokens(SVMParser.STORERA); }
		public TerminalNode STORERA(int i) {
			return getToken(SVMParser.STORERA, i);
		}
		public List<TerminalNode> LOADRV() { return getTokens(SVMParser.LOADRV); }
		public TerminalNode LOADRV(int i) {
			return getToken(SVMParser.LOADRV, i);
		}
		public List<TerminalNode> STORERV() { return getTokens(SVMParser.STORERV); }
		public TerminalNode STORERV(int i) {
			return getToken(SVMParser.STORERV, i);
		}
		public List<TerminalNode> LOADFP() { return getTokens(SVMParser.LOADFP); }
		public TerminalNode LOADFP(int i) {
			return getToken(SVMParser.LOADFP, i);
		}
		public List<TerminalNode> STOREFP() { return getTokens(SVMParser.STOREFP); }
		public TerminalNode STOREFP(int i) {
			return getToken(SVMParser.STOREFP, i);
		}
		public List<TerminalNode> COPYFP() { return getTokens(SVMParser.COPYFP); }
		public TerminalNode COPYFP(int i) {
			return getToken(SVMParser.COPYFP, i);
		}
		public List<TerminalNode> LOADHP() { return getTokens(SVMParser.LOADHP); }
		public TerminalNode LOADHP(int i) {
			return getToken(SVMParser.LOADHP, i);
		}
		public List<TerminalNode> STOREHP() { return getTokens(SVMParser.STOREHP); }
		public TerminalNode STOREHP(int i) {
			return getToken(SVMParser.STOREHP, i);
		}
		public List<TerminalNode> PRINT() { return getTokens(SVMParser.PRINT); }
		public TerminalNode PRINT(int i) {
			return getToken(SVMParser.PRINT, i);
		}
		public List<TerminalNode> HALT() { return getTokens(SVMParser.HALT); }
		public TerminalNode HALT(int i) {
			return getToken(SVMParser.HALT, i);
		}
		public List<TerminalNode> NEW() { return getTokens(SVMParser.NEW); }
		public TerminalNode NEW(int i) {
			return getToken(SVMParser.NEW, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(SVMParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(SVMParser.NUMBER, i);
		}
		public List<TerminalNode> LABEL() { return getTokens(SVMParser.LABEL); }
		public TerminalNode LABEL(int i) {
			return getToken(SVMParser.LABEL, i);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << JS) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADRV) | (1L << STORERV) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << NEW) | (1L << LABEL))) != 0)) {
				{
				setState(34);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(2);
					match(PUSH);
					setState(3);
					((AssemblyContext)_localctx).n = match(NUMBER);
					}
					break;
				case 2:
					{
					setState(4);
					match(PUSH);
					setState(5);
					((AssemblyContext)_localctx).l = match(LABEL);
					}
					break;
				case 3:
					{
					setState(6);
					match(POP);
					}
					break;
				case 4:
					{
					setState(7);
					match(ADD);
					}
					break;
				case 5:
					{
					setState(8);
					match(SUB);
					}
					break;
				case 6:
					{
					setState(9);
					match(MULT);
					}
					break;
				case 7:
					{
					setState(10);
					match(DIV);
					}
					break;
				case 8:
					{
					setState(11);
					match(STOREW);
					}
					break;
				case 9:
					{
					setState(12);
					match(LOADW);
					}
					break;
				case 10:
					{
					setState(13);
					((AssemblyContext)_localctx).l = match(LABEL);
					setState(14);
					match(COL);
					}
					break;
				case 11:
					{
					setState(15);
					match(BRANCH);
					setState(16);
					((AssemblyContext)_localctx).l = match(LABEL);
					}
					break;
				case 12:
					{
					setState(17);
					match(BRANCHEQ);
					setState(18);
					((AssemblyContext)_localctx).l = match(LABEL);
					}
					break;
				case 13:
					{
					setState(19);
					match(BRANCHLESSEQ);
					setState(20);
					((AssemblyContext)_localctx).l = match(LABEL);
					}
					break;
				case 14:
					{
					setState(21);
					match(JS);
					}
					break;
				case 15:
					{
					setState(22);
					match(LOADRA);
					}
					break;
				case 16:
					{
					setState(23);
					match(STORERA);
					}
					break;
				case 17:
					{
					setState(24);
					match(LOADRV);
					}
					break;
				case 18:
					{
					setState(25);
					match(STORERV);
					}
					break;
				case 19:
					{
					setState(26);
					match(LOADFP);
					}
					break;
				case 20:
					{
					setState(27);
					match(STOREFP);
					}
					break;
				case 21:
					{
					setState(28);
					match(COPYFP);
					}
					break;
				case 22:
					{
					setState(29);
					match(LOADHP);
					}
					break;
				case 23:
					{
					setState(30);
					match(STOREHP);
					}
					break;
				case 24:
					{
					setState(31);
					match(PRINT);
					}
					break;
				case 25:
					{
					setState(32);
					match(HALT);
					}
					break;
				case 26:
					{
					setState(33);
					match(NEW);
					}
					break;
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\37*\4\2\t\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7\2%\n\2\f\2\16\2"+
		"(\13\2\3\2\2\2\3\2\2\2B\2&\3\2\2\2\4\5\7\3\2\2\5%\7\35\2\2\6\7\7\3\2\2"+
		"\7%\7\34\2\2\b%\7\4\2\2\t%\7\5\2\2\n%\7\6\2\2\13%\7\7\2\2\f%\7\b\2\2\r"+
		"%\7\t\2\2\16%\7\n\2\2\17\20\7\34\2\2\20%\7\33\2\2\21\22\7\13\2\2\22%\7"+
		"\34\2\2\23\24\7\f\2\2\24%\7\34\2\2\25\26\7\r\2\2\26%\7\34\2\2\27%\7\16"+
		"\2\2\30%\7\17\2\2\31%\7\20\2\2\32%\7\21\2\2\33%\7\22\2\2\34%\7\23\2\2"+
		"\35%\7\24\2\2\36%\7\25\2\2\37%\7\26\2\2 %\7\27\2\2!%\7\30\2\2\"%\7\31"+
		"\2\2#%\7\32\2\2$\4\3\2\2\2$\6\3\2\2\2$\b\3\2\2\2$\t\3\2\2\2$\n\3\2\2\2"+
		"$\13\3\2\2\2$\f\3\2\2\2$\r\3\2\2\2$\16\3\2\2\2$\17\3\2\2\2$\21\3\2\2\2"+
		"$\23\3\2\2\2$\25\3\2\2\2$\27\3\2\2\2$\30\3\2\2\2$\31\3\2\2\2$\32\3\2\2"+
		"\2$\33\3\2\2\2$\34\3\2\2\2$\35\3\2\2\2$\36\3\2\2\2$\37\3\2\2\2$ \3\2\2"+
		"\2$!\3\2\2\2$\"\3\2\2\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'\3\3"+
		"\2\2\2(&\3\2\2\2\4$&";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}