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
		VOID=33, CLASS=34, INTEGER=35, ID=36, WS=37, LINECOMMENTS=38, BLOCKCOMMENTS=39;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"SEMIC", "COLON", "COMMA", "EQ", "ASM", "PLUS", "TIMES", "TRUE", "FALSE", 
		"LPAR", "RPAR", "CLPAR", "CRPAR", "IF", "THEN", "ELSE", "PRINT", "LET", 
		"IN", "VAR", "FUN", "INT", "BOOL", "MINUS", "DIVISION", "GREATER", "LESS", 
		"GREATEREQUAL", "LESSEQUAL", "OR", "AND", "NOT", "VOID", "CLASS", "DIGIT", 
		"INTEGER", "CHAR", "ID", "WS", "LINECOMMENTS", "BLOCKCOMMENTS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':'", "','", "'=='", "'='", "'+'", "'*'", "'true'", "'false'", 
		"'('", "')'", "'{'", "'}'", "'if'", "'then'", "'else'", "'print'", "'let'", 
		"'in'", "'var'", "'fun'", "'int'", "'bool'", "'-'", "'/'", "'>'", "'<'", 
		"'>='", "'<='", "'||'", "'&&'", "'not'", "'void'", "'class'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SEMIC", "COLON", "COMMA", "EQ", "ASM", "PLUS", "TIMES", "TRUE", 
		"FALSE", "LPAR", "RPAR", "CLPAR", "CRPAR", "IF", "THEN", "ELSE", "PRINT", 
		"LET", "IN", "VAR", "FUN", "INT", "BOOL", "MINUS", "DIVISION", "GREATER", 
		"LESS", "GREATEREQUAL", "LESSEQUAL", "OR", "AND", "NOT", "VOID", "CLASS", 
		"INTEGER", "ID", "WS", "LINECOMMENTS", "BLOCKCOMMENTS"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2)\u00fb\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3"+
		"\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#"+
		"\3#\3$\3$\3%\5%\u00c9\n%\3%\6%\u00cc\n%\r%\16%\u00cd\3&\3&\3\'\3\'\3\'"+
		"\7\'\u00d5\n\'\f\'\16\'\u00d8\13\'\3(\3(\3(\3(\3)\3)\3)\3)\7)\u00e2\n"+
		")\f)\16)\u00e5\13)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3*\3*\7*\u00f2\n*\f*\16"+
		"*\u00f5\13*\3*\3*\3*\3*\3*\2\2+\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G\2I%K\2M&O\'Q(S)\3\2\b\4\2"+
		"C\\c|\5\2\13\f\17\17\"\"\4\2\f\f\17\17\4\2,,\61\61\3\2,,\3\2\61\61\u0101"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2I\3\2\2"+
		"\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\3U\3\2\2\2\5W\3\2\2\2\7"+
		"Y\3\2\2\2\t[\3\2\2\2\13^\3\2\2\2\r`\3\2\2\2\17b\3\2\2\2\21d\3\2\2\2\23"+
		"i\3\2\2\2\25o\3\2\2\2\27q\3\2\2\2\31s\3\2\2\2\33u\3\2\2\2\35w\3\2\2\2"+
		"\37z\3\2\2\2!\177\3\2\2\2#\u0084\3\2\2\2%\u008a\3\2\2\2\'\u008e\3\2\2"+
		"\2)\u0091\3\2\2\2+\u0095\3\2\2\2-\u0099\3\2\2\2/\u009d\3\2\2\2\61\u00a2"+
		"\3\2\2\2\63\u00a4\3\2\2\2\65\u00a6\3\2\2\2\67\u00a8\3\2\2\29\u00aa\3\2"+
		"\2\2;\u00ad\3\2\2\2=\u00b0\3\2\2\2?\u00b3\3\2\2\2A\u00b6\3\2\2\2C\u00ba"+
		"\3\2\2\2E\u00bf\3\2\2\2G\u00c5\3\2\2\2I\u00c8\3\2\2\2K\u00cf\3\2\2\2M"+
		"\u00d1\3\2\2\2O\u00d9\3\2\2\2Q\u00dd\3\2\2\2S\u00e8\3\2\2\2UV\7=\2\2V"+
		"\4\3\2\2\2WX\7<\2\2X\6\3\2\2\2YZ\7.\2\2Z\b\3\2\2\2[\\\7?\2\2\\]\7?\2\2"+
		"]\n\3\2\2\2^_\7?\2\2_\f\3\2\2\2`a\7-\2\2a\16\3\2\2\2bc\7,\2\2c\20\3\2"+
		"\2\2de\7v\2\2ef\7t\2\2fg\7w\2\2gh\7g\2\2h\22\3\2\2\2ij\7h\2\2jk\7c\2\2"+
		"kl\7n\2\2lm\7u\2\2mn\7g\2\2n\24\3\2\2\2op\7*\2\2p\26\3\2\2\2qr\7+\2\2"+
		"r\30\3\2\2\2st\7}\2\2t\32\3\2\2\2uv\7\177\2\2v\34\3\2\2\2wx\7k\2\2xy\7"+
		"h\2\2y\36\3\2\2\2z{\7v\2\2{|\7j\2\2|}\7g\2\2}~\7p\2\2~ \3\2\2\2\177\u0080"+
		"\7g\2\2\u0080\u0081\7n\2\2\u0081\u0082\7u\2\2\u0082\u0083\7g\2\2\u0083"+
		"\"\3\2\2\2\u0084\u0085\7r\2\2\u0085\u0086\7t\2\2\u0086\u0087\7k\2\2\u0087"+
		"\u0088\7p\2\2\u0088\u0089\7v\2\2\u0089$\3\2\2\2\u008a\u008b\7n\2\2\u008b"+
		"\u008c\7g\2\2\u008c\u008d\7v\2\2\u008d&\3\2\2\2\u008e\u008f\7k\2\2\u008f"+
		"\u0090\7p\2\2\u0090(\3\2\2\2\u0091\u0092\7x\2\2\u0092\u0093\7c\2\2\u0093"+
		"\u0094\7t\2\2\u0094*\3\2\2\2\u0095\u0096\7h\2\2\u0096\u0097\7w\2\2\u0097"+
		"\u0098\7p\2\2\u0098,\3\2\2\2\u0099\u009a\7k\2\2\u009a\u009b\7p\2\2\u009b"+
		"\u009c\7v\2\2\u009c.\3\2\2\2\u009d\u009e\7d\2\2\u009e\u009f\7q\2\2\u009f"+
		"\u00a0\7q\2\2\u00a0\u00a1\7n\2\2\u00a1\60\3\2\2\2\u00a2\u00a3\7/\2\2\u00a3"+
		"\62\3\2\2\2\u00a4\u00a5\7\61\2\2\u00a5\64\3\2\2\2\u00a6\u00a7\7@\2\2\u00a7"+
		"\66\3\2\2\2\u00a8\u00a9\7>\2\2\u00a98\3\2\2\2\u00aa\u00ab\7@\2\2\u00ab"+
		"\u00ac\7?\2\2\u00ac:\3\2\2\2\u00ad\u00ae\7>\2\2\u00ae\u00af\7?\2\2\u00af"+
		"<\3\2\2\2\u00b0\u00b1\7~\2\2\u00b1\u00b2\7~\2\2\u00b2>\3\2\2\2\u00b3\u00b4"+
		"\7(\2\2\u00b4\u00b5\7(\2\2\u00b5@\3\2\2\2\u00b6\u00b7\7p\2\2\u00b7\u00b8"+
		"\7q\2\2\u00b8\u00b9\7v\2\2\u00b9B\3\2\2\2\u00ba\u00bb\7x\2\2\u00bb\u00bc"+
		"\7q\2\2\u00bc\u00bd\7k\2\2\u00bd\u00be\7f\2\2\u00beD\3\2\2\2\u00bf\u00c0"+
		"\7e\2\2\u00c0\u00c1\7n\2\2\u00c1\u00c2\7c\2\2\u00c2\u00c3\7u\2\2\u00c3"+
		"\u00c4\7u\2\2\u00c4F\3\2\2\2\u00c5\u00c6\4\62;\2\u00c6H\3\2\2\2\u00c7"+
		"\u00c9\5\61\31\2\u00c8\u00c7\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cb\3"+
		"\2\2\2\u00ca\u00cc\5G$\2\u00cb\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ceJ\3\2\2\2\u00cf\u00d0\t\2\2\2"+
		"\u00d0L\3\2\2\2\u00d1\u00d6\5K&\2\u00d2\u00d5\5K&\2\u00d3\u00d5\5G$\2"+
		"\u00d4\u00d2\3\2\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4"+
		"\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7N\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9"+
		"\u00da\t\3\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\b(\2\2\u00dcP\3\2\2\2\u00dd"+
		"\u00de\7\61\2\2\u00de\u00df\7\61\2\2\u00df\u00e3\3\2\2\2\u00e0\u00e2\n"+
		"\4\2\2\u00e1\u00e0\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3"+
		"\u00e4\3\2\2\2\u00e4\u00e6\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00e7\b)"+
		"\2\2\u00e7R\3\2\2\2\u00e8\u00e9\7\61\2\2\u00e9\u00ea\7,\2\2\u00ea\u00f3"+
		"\3\2\2\2\u00eb\u00f2\n\5\2\2\u00ec\u00ed\7\61\2\2\u00ed\u00f2\n\6\2\2"+
		"\u00ee\u00ef\7,\2\2\u00ef\u00f2\n\7\2\2\u00f0\u00f2\5S*\2\u00f1\u00eb"+
		"\3\2\2\2\u00f1\u00ec\3\2\2\2\u00f1\u00ee\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2"+
		"\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6\3\2"+
		"\2\2\u00f5\u00f3\3\2\2\2\u00f6\u00f7\7,\2\2\u00f7\u00f8\7\61\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9\u00fa\b*\2\2\u00faT\3\2\2\2\n\2\u00c8\u00cd\u00d4"+
		"\u00d6\u00e3\u00f1\u00f3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}