// Generated from FOOL.g4 by ANTLR 4.6
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FOOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEMIC=1, COLON=2, COMMA=3, EQ=4, ASM=5, PLUS=6, TIMES=7, TRUE=8, FALSE=9, 
		LPAR=10, RPAR=11, CLPAR=12, CRPAR=13, IF=14, THEN=15, ELSE=16, PRINT=17, 
		LET=18, IN=19, VAR=20, FUN=21, INT=22, BOOL=23, MINUS=24, DIVISION=25, 
		GREATER=26, LESS=27, GREATEREQUAL=28, LESSEQUAL=29, OR=30, AND=31, NOT=32, 
		END=33, VOID=34, CLASS=35, INTEGER=36, ID=37, WS=38, LINECOMMENTS=39, 
		BLOCKCOMMENTS=40;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"SEMIC", "COLON", "COMMA", "EQ", "ASM", "PLUS", "TIMES", "TRUE", "FALSE", 
		"LPAR", "RPAR", "CLPAR", "CRPAR", "IF", "THEN", "ELSE", "PRINT", "LET", 
		"IN", "VAR", "FUN", "INT", "BOOL", "MINUS", "DIVISION", "GREATER", "LESS", 
		"GREATEREQUAL", "LESSEQUAL", "OR", "AND", "NOT", "END", "VOID", "CLASS", 
		"DIGIT", "INTEGER", "CHAR", "ID", "WS", "LINECOMMENTS", "BLOCKCOMMENTS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "','", "'=='", "'='", "'+'", "'*'", "'true'", "'false'", 
		"'('", "')'", "'{'", "'}'", "'if'", "'then'", "'else'", "'print'", "'let'", 
		"'in'", "'var'", "'fun'", "'int'", "'bool'", "'-'", "'/'", "'>'", "'<'", 
		"'>='", "'<='", "'||'", "'&&'", "'not'", "'end'", "'void'", "'class'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SEMIC", "COLON", "COMMA", "EQ", "ASM", "PLUS", "TIMES", "TRUE", 
		"FALSE", "LPAR", "RPAR", "CLPAR", "CRPAR", "IF", "THEN", "ELSE", "PRINT", 
		"LET", "IN", "VAR", "FUN", "INT", "BOOL", "MINUS", "DIVISION", "GREATER", 
		"LESS", "GREATEREQUAL", "LESSEQUAL", "OR", "AND", "NOT", "END", "VOID", 
		"CLASS", "INTEGER", "ID", "WS", "LINECOMMENTS", "BLOCKCOMMENTS"
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


	public FOOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FOOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2*\u0101\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3"+
		"\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3"+
		"\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3"+
		"\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3"+
		"#\3$\3$\3$\3$\3$\3$\3%\3%\3&\5&\u00cf\n&\3&\6&\u00d2\n&\r&\16&\u00d3\3"+
		"\'\3\'\3(\3(\3(\7(\u00db\n(\f(\16(\u00de\13(\3)\3)\3)\3)\3*\3*\3*\3*\7"+
		"*\u00e8\n*\f*\16*\u00eb\13*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\7+\u00f8"+
		"\n+\f+\16+\u00fb\13+\3+\3+\3+\3+\3+\2\2,\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I\2K&M\2O\'Q(S)U"+
		"*\3\2\b\4\2C\\c|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4\2,,\61\61\3\2,,\3"+
		"\2\61\61\u0107\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\2"+
		"9\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3"+
		"\2\2\2\2G\3\2\2\2\2K\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2"+
		"\2\3W\3\2\2\2\5Y\3\2\2\2\7[\3\2\2\2\t]\3\2\2\2\13`\3\2\2\2\rb\3\2\2\2"+
		"\17d\3\2\2\2\21f\3\2\2\2\23k\3\2\2\2\25q\3\2\2\2\27s\3\2\2\2\31u\3\2\2"+
		"\2\33w\3\2\2\2\35y\3\2\2\2\37|\3\2\2\2!\u0081\3\2\2\2#\u0086\3\2\2\2%"+
		"\u008c\3\2\2\2\'\u0090\3\2\2\2)\u0093\3\2\2\2+\u0097\3\2\2\2-\u009b\3"+
		"\2\2\2/\u009f\3\2\2\2\61\u00a4\3\2\2\2\63\u00a6\3\2\2\2\65\u00a8\3\2\2"+
		"\2\67\u00aa\3\2\2\29\u00ac\3\2\2\2;\u00af\3\2\2\2=\u00b2\3\2\2\2?\u00b5"+
		"\3\2\2\2A\u00b8\3\2\2\2C\u00bc\3\2\2\2E\u00c0\3\2\2\2G\u00c5\3\2\2\2I"+
		"\u00cb\3\2\2\2K\u00ce\3\2\2\2M\u00d5\3\2\2\2O\u00d7\3\2\2\2Q\u00df\3\2"+
		"\2\2S\u00e3\3\2\2\2U\u00ee\3\2\2\2WX\7=\2\2X\4\3\2\2\2YZ\7<\2\2Z\6\3\2"+
		"\2\2[\\\7.\2\2\\\b\3\2\2\2]^\7?\2\2^_\7?\2\2_\n\3\2\2\2`a\7?\2\2a\f\3"+
		"\2\2\2bc\7-\2\2c\16\3\2\2\2de\7,\2\2e\20\3\2\2\2fg\7v\2\2gh\7t\2\2hi\7"+
		"w\2\2ij\7g\2\2j\22\3\2\2\2kl\7h\2\2lm\7c\2\2mn\7n\2\2no\7u\2\2op\7g\2"+
		"\2p\24\3\2\2\2qr\7*\2\2r\26\3\2\2\2st\7+\2\2t\30\3\2\2\2uv\7}\2\2v\32"+
		"\3\2\2\2wx\7\177\2\2x\34\3\2\2\2yz\7k\2\2z{\7h\2\2{\36\3\2\2\2|}\7v\2"+
		"\2}~\7j\2\2~\177\7g\2\2\177\u0080\7p\2\2\u0080 \3\2\2\2\u0081\u0082\7"+
		"g\2\2\u0082\u0083\7n\2\2\u0083\u0084\7u\2\2\u0084\u0085\7g\2\2\u0085\""+
		"\3\2\2\2\u0086\u0087\7r\2\2\u0087\u0088\7t\2\2\u0088\u0089\7k\2\2\u0089"+
		"\u008a\7p\2\2\u008a\u008b\7v\2\2\u008b$\3\2\2\2\u008c\u008d\7n\2\2\u008d"+
		"\u008e\7g\2\2\u008e\u008f\7v\2\2\u008f&\3\2\2\2\u0090\u0091\7k\2\2\u0091"+
		"\u0092\7p\2\2\u0092(\3\2\2\2\u0093\u0094\7x\2\2\u0094\u0095\7c\2\2\u0095"+
		"\u0096\7t\2\2\u0096*\3\2\2\2\u0097\u0098\7h\2\2\u0098\u0099\7w\2\2\u0099"+
		"\u009a\7p\2\2\u009a,\3\2\2\2\u009b\u009c\7k\2\2\u009c\u009d\7p\2\2\u009d"+
		"\u009e\7v\2\2\u009e.\3\2\2\2\u009f\u00a0\7d\2\2\u00a0\u00a1\7q\2\2\u00a1"+
		"\u00a2\7q\2\2\u00a2\u00a3\7n\2\2\u00a3\60\3\2\2\2\u00a4\u00a5\7/\2\2\u00a5"+
		"\62\3\2\2\2\u00a6\u00a7\7\61\2\2\u00a7\64\3\2\2\2\u00a8\u00a9\7@\2\2\u00a9"+
		"\66\3\2\2\2\u00aa\u00ab\7>\2\2\u00ab8\3\2\2\2\u00ac\u00ad\7@\2\2\u00ad"+
		"\u00ae\7?\2\2\u00ae:\3\2\2\2\u00af\u00b0\7>\2\2\u00b0\u00b1\7?\2\2\u00b1"+
		"<\3\2\2\2\u00b2\u00b3\7~\2\2\u00b3\u00b4\7~\2\2\u00b4>\3\2\2\2\u00b5\u00b6"+
		"\7(\2\2\u00b6\u00b7\7(\2\2\u00b7@\3\2\2\2\u00b8\u00b9\7p\2\2\u00b9\u00ba"+
		"\7q\2\2\u00ba\u00bb\7v\2\2\u00bbB\3\2\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be"+
		"\7p\2\2\u00be\u00bf\7f\2\2\u00bfD\3\2\2\2\u00c0\u00c1\7x\2\2\u00c1\u00c2"+
		"\7q\2\2\u00c2\u00c3\7k\2\2\u00c3\u00c4\7f\2\2\u00c4F\3\2\2\2\u00c5\u00c6"+
		"\7e\2\2\u00c6\u00c7\7n\2\2\u00c7\u00c8\7c\2\2\u00c8\u00c9\7u\2\2\u00c9"+
		"\u00ca\7u\2\2\u00caH\3\2\2\2\u00cb\u00cc\4\62;\2\u00ccJ\3\2\2\2\u00cd"+
		"\u00cf\5\61\31\2\u00ce\u00cd\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d1\3"+
		"\2\2\2\u00d0\u00d2\5I%\2\u00d1\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3"+
		"\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4L\3\2\2\2\u00d5\u00d6\t\2\2\2"+
		"\u00d6N\3\2\2\2\u00d7\u00dc\5M\'\2\u00d8\u00db\5M\'\2\u00d9\u00db\5I%"+
		"\2\u00da\u00d8\3\2\2\2\u00da\u00d9\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da"+
		"\3\2\2\2\u00dc\u00dd\3\2\2\2\u00ddP\3\2\2\2\u00de\u00dc\3\2\2\2\u00df"+
		"\u00e0\t\3\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\b)\2\2\u00e2R\3\2\2\2\u00e3"+
		"\u00e4\7\61\2\2\u00e4\u00e5\7\61\2\2\u00e5\u00e9\3\2\2\2\u00e6\u00e8\n"+
		"\4\2\2\u00e7\u00e6\3\2\2\2\u00e8\u00eb\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9"+
		"\u00ea\3\2\2\2\u00ea\u00ec\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00ed\b*"+
		"\2\2\u00edT\3\2\2\2\u00ee\u00ef\7\61\2\2\u00ef\u00f0\7,\2\2\u00f0\u00f9"+
		"\3\2\2\2\u00f1\u00f8\n\5\2\2\u00f2\u00f3\7\61\2\2\u00f3\u00f8\n\6\2\2"+
		"\u00f4\u00f5\7,\2\2\u00f5\u00f8\n\7\2\2\u00f6\u00f8\5U+\2\u00f7\u00f1"+
		"\3\2\2\2\u00f7\u00f2\3\2\2\2\u00f7\u00f4\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8"+
		"\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fc\3\2"+
		"\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00fd\7,\2\2\u00fd\u00fe\7\61\2\2\u00fe"+
		"\u00ff\3\2\2\2\u00ff\u0100\b+\2\2\u0100V\3\2\2\2\n\2\u00ce\u00d3\u00da"+
		"\u00dc\u00e9\u00f7\u00f9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}