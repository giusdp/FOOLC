// Generated from SVM.g4 by ANTLR 4.6
package svm;
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
		BRANCHEQ=10, BRANCHLESSEQ=11, BRANCHLESS=12, BRANCHGREATEREQ=13, BRANCHGREATER=14, 
		AND=15, OR=16, JS=17, LOADRA=18, STORERA=19, LOADRV=20, STORERV=21, LOADFP=22, 
		STOREFP=23, COPYFP=24, LOADHP=25, STOREHP=26, PRINT=27, HALT=28, NEW=29, 
		COL=30, LABEL=31, NUMBER=32, WHITESP=33, ERR=34;
	public static final int
		RULE_assembly = 0, RULE_instruction = 1;
	public static final String[] ruleNames = {
		"assembly", "instruction"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'push'", "'pop'", "'add'", "'sub'", "'mult'", "'div'", "'sw'", 
		"'lw'", "'b'", "'beq'", "'bleq'", "'bl'", "'bgeq'", "'bg'", "'and'", "'or'", 
		"'js'", "'lra'", "'sra'", "'lrv'", "'srv'", "'lfp'", "'sfp'", "'cfp'", 
		"'lhp'", "'shp'", "'print'", "'halt'", "'new'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PUSH", "POP", "ADD", "SUB", "MULT", "DIV", "STOREW", "LOADW", "BRANCH", 
		"BRANCHEQ", "BRANCHLESSEQ", "BRANCHLESS", "BRANCHGREATEREQ", "BRANCHGREATER", 
		"AND", "OR", "JS", "LOADRA", "STORERA", "LOADRV", "STORERV", "LOADFP", 
		"STOREFP", "COPYFP", "LOADHP", "STOREHP", "PRINT", "HALT", "NEW", "COL", 
		"LABEL", "NUMBER", "WHITESP", "ERR"
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

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AssemblyContext extends ParserRuleContext {
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
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
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << BRANCHLESS) | (1L << BRANCHGREATEREQ) | (1L << BRANCHGREATER) | (1L << AND) | (1L << OR) | (1L << JS) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADRV) | (1L << STORERV) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << NEW) | (1L << LABEL))) != 0)) {
				{
				{
				setState(4);
				instruction();
				}
				}
				setState(9);
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

	public static class InstructionContext extends ParserRuleContext {
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
	 
		public InstructionContext() { }
		public void copyFrom(InstructionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BranchEqualContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCHEQ() { return getToken(SVMParser.BRANCHEQ, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchEqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadRAContext extends InstructionContext {
		public TerminalNode LOADRA() { return getToken(SVMParser.LOADRA, 0); }
		public LoadRAContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadRA(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreRaContext extends InstructionContext {
		public TerminalNode STORERA() { return getToken(SVMParser.STORERA, 0); }
		public StoreRaContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreRa(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubContext extends InstructionContext {
		public TerminalNode SUB() { return getToken(SVMParser.SUB, 0); }
		public SubContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreWContext extends InstructionContext {
		public TerminalNode STOREW() { return getToken(SVMParser.STOREW, 0); }
		public StoreWContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreW(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultContext extends InstructionContext {
		public TerminalNode MULT() { return getToken(SVMParser.MULT, 0); }
		public MultContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JsContext extends InstructionContext {
		public TerminalNode JS() { return getToken(SVMParser.JS, 0); }
		public JsContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitJs(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCH() { return getToken(SVMParser.BRANCH, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranch(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchLessEqualContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCHLESSEQ() { return getToken(SVMParser.BRANCHLESSEQ, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchLessEqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchLessEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PopContext extends InstructionContext {
		public TerminalNode POP() { return getToken(SVMParser.POP, 0); }
		public PopContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPop(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivContext extends InstructionContext {
		public TerminalNode DIV() { return getToken(SVMParser.DIV, 0); }
		public DivContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadWContext extends InstructionContext {
		public TerminalNode LOADW() { return getToken(SVMParser.LOADW, 0); }
		public LoadWContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadW(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreRVContext extends InstructionContext {
		public TerminalNode STORERV() { return getToken(SVMParser.STORERV, 0); }
		public StoreRVContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreRV(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PushNumberContext extends InstructionContext {
		public Token n;
		public TerminalNode PUSH() { return getToken(SVMParser.PUSH, 0); }
		public TerminalNode NUMBER() { return getToken(SVMParser.NUMBER, 0); }
		public PushNumberContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPushNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchAndContext extends InstructionContext {
		public TerminalNode AND() { return getToken(SVMParser.AND, 0); }
		public BranchAndContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchOrContext extends InstructionContext {
		public TerminalNode OR() { return getToken(SVMParser.OR, 0); }
		public BranchOrContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreHPContext extends InstructionContext {
		public TerminalNode STOREHP() { return getToken(SVMParser.STOREHP, 0); }
		public StoreHPContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreHP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StoreFPContext extends InstructionContext {
		public TerminalNode STOREFP() { return getToken(SVMParser.STOREFP, 0); }
		public StoreFPContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitStoreFP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddContext extends InstructionContext {
		public TerminalNode ADD() { return getToken(SVMParser.ADD, 0); }
		public AddContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PushLabelContext extends InstructionContext {
		public Token l;
		public TerminalNode PUSH() { return getToken(SVMParser.PUSH, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public PushLabelContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPushLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewContext extends InstructionContext {
		public TerminalNode NEW() { return getToken(SVMParser.NEW, 0); }
		public NewContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitNew(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadFPContext extends InstructionContext {
		public TerminalNode LOADFP() { return getToken(SVMParser.LOADFP, 0); }
		public LoadFPContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadFP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchGreaterEqualContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCHGREATEREQ() { return getToken(SVMParser.BRANCHGREATEREQ, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchGreaterEqualContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchGreaterEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LabelContext extends InstructionContext {
		public Token l;
		public TerminalNode COL() { return getToken(SVMParser.COL, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public LabelContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HaltContext extends InstructionContext {
		public TerminalNode HALT() { return getToken(SVMParser.HALT, 0); }
		public HaltContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitHalt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends InstructionContext {
		public TerminalNode PRINT() { return getToken(SVMParser.PRINT, 0); }
		public PrintContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchGreaterContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCHGREATER() { return getToken(SVMParser.BRANCHGREATER, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchGreaterContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchGreater(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BranchLessContext extends InstructionContext {
		public Token l;
		public TerminalNode BRANCHLESS() { return getToken(SVMParser.BRANCHLESS, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public BranchLessContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitBranchLess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadHPContext extends InstructionContext {
		public TerminalNode LOADHP() { return getToken(SVMParser.LOADHP, 0); }
		public LoadHPContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadHP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LoadRVContext extends InstructionContext {
		public TerminalNode LOADRV() { return getToken(SVMParser.LOADRV, 0); }
		public LoadRVContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitLoadRV(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CopyFPContext extends InstructionContext {
		public TerminalNode COPYFP() { return getToken(SVMParser.COPYFP, 0); }
		public CopyFPContext(InstructionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitCopyFP(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			setState(50);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new PushNumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				match(PUSH);
				setState(11);
				((PushNumberContext)_localctx).n = match(NUMBER);
				}
				break;
			case 2:
				_localctx = new PushLabelContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(12);
				match(PUSH);
				setState(13);
				((PushLabelContext)_localctx).l = match(LABEL);
				}
				break;
			case 3:
				_localctx = new PopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(14);
				match(POP);
				}
				break;
			case 4:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(15);
				match(ADD);
				}
				break;
			case 5:
				_localctx = new SubContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(16);
				match(SUB);
				}
				break;
			case 6:
				_localctx = new MultContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(17);
				match(MULT);
				}
				break;
			case 7:
				_localctx = new DivContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(18);
				match(DIV);
				}
				break;
			case 8:
				_localctx = new StoreWContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(19);
				match(STOREW);
				}
				break;
			case 9:
				_localctx = new LoadWContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(20);
				match(LOADW);
				}
				break;
			case 10:
				_localctx = new LabelContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(21);
				((LabelContext)_localctx).l = match(LABEL);
				setState(22);
				match(COL);
				}
				break;
			case 11:
				_localctx = new BranchContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(23);
				match(BRANCH);
				setState(24);
				((BranchContext)_localctx).l = match(LABEL);
				}
				break;
			case 12:
				_localctx = new BranchEqualContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(25);
				match(BRANCHEQ);
				setState(26);
				((BranchEqualContext)_localctx).l = match(LABEL);
				}
				break;
			case 13:
				_localctx = new BranchLessEqualContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(27);
				match(BRANCHLESSEQ);
				setState(28);
				((BranchLessEqualContext)_localctx).l = match(LABEL);
				}
				break;
			case 14:
				_localctx = new BranchLessContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(29);
				match(BRANCHLESS);
				setState(30);
				((BranchLessContext)_localctx).l = match(LABEL);
				}
				break;
			case 15:
				_localctx = new BranchGreaterEqualContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(31);
				match(BRANCHGREATEREQ);
				setState(32);
				((BranchGreaterEqualContext)_localctx).l = match(LABEL);
				}
				break;
			case 16:
				_localctx = new BranchGreaterContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(33);
				match(BRANCHGREATER);
				setState(34);
				((BranchGreaterContext)_localctx).l = match(LABEL);
				}
				break;
			case 17:
				_localctx = new BranchAndContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(35);
				match(AND);
				}
				break;
			case 18:
				_localctx = new BranchOrContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(36);
				match(OR);
				}
				break;
			case 19:
				_localctx = new JsContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(37);
				match(JS);
				}
				break;
			case 20:
				_localctx = new LoadRAContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(38);
				match(LOADRA);
				}
				break;
			case 21:
				_localctx = new StoreRaContext(_localctx);
				enterOuterAlt(_localctx, 21);
				{
				setState(39);
				match(STORERA);
				}
				break;
			case 22:
				_localctx = new LoadRVContext(_localctx);
				enterOuterAlt(_localctx, 22);
				{
				setState(40);
				match(LOADRV);
				}
				break;
			case 23:
				_localctx = new StoreRVContext(_localctx);
				enterOuterAlt(_localctx, 23);
				{
				setState(41);
				match(STORERV);
				}
				break;
			case 24:
				_localctx = new LoadFPContext(_localctx);
				enterOuterAlt(_localctx, 24);
				{
				setState(42);
				match(LOADFP);
				}
				break;
			case 25:
				_localctx = new StoreFPContext(_localctx);
				enterOuterAlt(_localctx, 25);
				{
				setState(43);
				match(STOREFP);
				}
				break;
			case 26:
				_localctx = new CopyFPContext(_localctx);
				enterOuterAlt(_localctx, 26);
				{
				setState(44);
				match(COPYFP);
				}
				break;
			case 27:
				_localctx = new LoadHPContext(_localctx);
				enterOuterAlt(_localctx, 27);
				{
				setState(45);
				match(LOADHP);
				}
				break;
			case 28:
				_localctx = new StoreHPContext(_localctx);
				enterOuterAlt(_localctx, 28);
				{
				setState(46);
				match(STOREHP);
				}
				break;
			case 29:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 29);
				{
				setState(47);
				match(PRINT);
				}
				break;
			case 30:
				_localctx = new HaltContext(_localctx);
				enterOuterAlt(_localctx, 30);
				{
				setState(48);
				match(HALT);
				}
				break;
			case 31:
				_localctx = new NewContext(_localctx);
				enterOuterAlt(_localctx, 31);
				{
				setState(49);
				match(NEW);
				}
				break;
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\67\4\2\t\2\4\3\t"+
		"\3\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\65\n\3\3\3"+
		"\2\2\4\2\4\2\2S\2\t\3\2\2\2\4\64\3\2\2\2\6\b\5\4\3\2\7\6\3\2\2\2\b\13"+
		"\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\3\3\2\2\2\13\t\3\2\2\2\f\r\7\3\2\2"+
		"\r\65\7\"\2\2\16\17\7\3\2\2\17\65\7!\2\2\20\65\7\4\2\2\21\65\7\5\2\2\22"+
		"\65\7\6\2\2\23\65\7\7\2\2\24\65\7\b\2\2\25\65\7\t\2\2\26\65\7\n\2\2\27"+
		"\30\7!\2\2\30\65\7 \2\2\31\32\7\13\2\2\32\65\7!\2\2\33\34\7\f\2\2\34\65"+
		"\7!\2\2\35\36\7\r\2\2\36\65\7!\2\2\37 \7\16\2\2 \65\7!\2\2!\"\7\17\2\2"+
		"\"\65\7!\2\2#$\7\20\2\2$\65\7!\2\2%\65\7\21\2\2&\65\7\22\2\2\'\65\7\23"+
		"\2\2(\65\7\24\2\2)\65\7\25\2\2*\65\7\26\2\2+\65\7\27\2\2,\65\7\30\2\2"+
		"-\65\7\31\2\2.\65\7\32\2\2/\65\7\33\2\2\60\65\7\34\2\2\61\65\7\35\2\2"+
		"\62\65\7\36\2\2\63\65\7\37\2\2\64\f\3\2\2\2\64\16\3\2\2\2\64\20\3\2\2"+
		"\2\64\21\3\2\2\2\64\22\3\2\2\2\64\23\3\2\2\2\64\24\3\2\2\2\64\25\3\2\2"+
		"\2\64\26\3\2\2\2\64\27\3\2\2\2\64\31\3\2\2\2\64\33\3\2\2\2\64\35\3\2\2"+
		"\2\64\37\3\2\2\2\64!\3\2\2\2\64#\3\2\2\2\64%\3\2\2\2\64&\3\2\2\2\64\'"+
		"\3\2\2\2\64(\3\2\2\2\64)\3\2\2\2\64*\3\2\2\2\64+\3\2\2\2\64,\3\2\2\2\64"+
		"-\3\2\2\2\64.\3\2\2\2\64/\3\2\2\2\64\60\3\2\2\2\64\61\3\2\2\2\64\62\3"+
		"\2\2\2\64\63\3\2\2\2\65\5\3\2\2\2\4\t\64";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}