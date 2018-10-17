// Generated from SVM.g4 by ANTLR 4.6
package codegen;

import java.util.HashMap;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SVMParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SVMVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SVMParser#assembly}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssembly(SVMParser.AssemblyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pushNumber}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPushNumber(SVMParser.PushNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pushLabel}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPushLabel(SVMParser.PushLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pop}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPop(SVMParser.PopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code add}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(SVMParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sub}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub(SVMParser.SubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(SVMParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code div}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiv(SVMParser.DivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storeW}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreW(SVMParser.StoreWContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loadW}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadW(SVMParser.LoadWContext ctx);
	/**
	 * Visit a parse tree produced by the {@code label}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(SVMParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code branch}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch(SVMParser.BranchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code branchEqual}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchEqual(SVMParser.BranchEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code branchLessEqual}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchLessEqual(SVMParser.BranchLessEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code js}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJs(SVMParser.JsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loadRA}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadRA(SVMParser.LoadRAContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storeRa}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreRa(SVMParser.StoreRaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loadRV}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadRV(SVMParser.LoadRVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storeRV}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreRV(SVMParser.StoreRVContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loadFP}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadFP(SVMParser.LoadFPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storeFP}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreFP(SVMParser.StoreFPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code copyFP}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCopyFP(SVMParser.CopyFPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loadHP}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadHP(SVMParser.LoadHPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code storeHP}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStoreHP(SVMParser.StoreHPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(SVMParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code halt}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHalt(SVMParser.HaltContext ctx);
	/**
	 * Visit a parse tree produced by the {@code new}
	 * labeled alternative in {@link SVMParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew(SVMParser.NewContext ctx);
}