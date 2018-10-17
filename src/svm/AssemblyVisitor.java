package svm;

import codegen.SVMBaseVisitor;
import codegen.SVMParser.AddContext;
import codegen.SVMParser.AssemblyContext;
import codegen.SVMParser.BranchContext;
import codegen.SVMParser.BranchEqualContext;
import codegen.SVMParser.BranchLessEqualContext;
import codegen.SVMParser.CopyFPContext;
import codegen.SVMParser.DivContext;
import codegen.SVMParser.HaltContext;
import codegen.SVMParser.JsContext;
import codegen.SVMParser.LabelContext;
import codegen.SVMParser.LoadFPContext;
import codegen.SVMParser.LoadHPContext;
import codegen.SVMParser.LoadRAContext;
import codegen.SVMParser.LoadRVContext;
import codegen.SVMParser.LoadWContext;
import codegen.SVMParser.MultContext;
import codegen.SVMParser.NewContext;
import codegen.SVMParser.PopContext;
import codegen.SVMParser.PrintContext;
import codegen.SVMParser.PushLabelContext;
import codegen.SVMParser.PushNumberContext;
import codegen.SVMParser.StoreFPContext;
import codegen.SVMParser.StoreHPContext;
import codegen.SVMParser.StoreRVContext;
import codegen.SVMParser.StoreRaContext;
import codegen.SVMParser.StoreWContext;
import codegen.SVMParser.SubContext;

public class AssemblyVisitor extends SVMBaseVisitor<AssemblyNode> {

	@Override
	public AssemblyNode visitPushNumber(PushNumberContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPushNumber(ctx);
	}

	@Override
	public AssemblyNode visitPushLabel(PushLabelContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPushLabel(ctx);
	}

	@Override
	public AssemblyNode visitPop(PopContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPop(ctx);
	}

	@Override
	public AssemblyNode visitAdd(AddContext ctx) {
		// TODO Auto-generated method stub
		return super.visitAdd(ctx);
	}

	@Override
	public AssemblyNode visitSub(SubContext ctx) {
		// TODO Auto-generated method stub
		return super.visitSub(ctx);
	}

	@Override
	public AssemblyNode visitMult(MultContext ctx) {
		// TODO Auto-generated method stub
		return super.visitMult(ctx);
	}

	@Override
	public AssemblyNode visitDiv(DivContext ctx) {
		// TODO Auto-generated method stub
		return super.visitDiv(ctx);
	}

	@Override
	public AssemblyNode visitStoreW(StoreWContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStoreW(ctx);
	}

	@Override
	public AssemblyNode visitLoadW(LoadWContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLoadW(ctx);
	}

	@Override
	public AssemblyNode visitLabel(LabelContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLabel(ctx);
	}

	@Override
	public AssemblyNode visitBranch(BranchContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBranch(ctx);
	}

	@Override
	public AssemblyNode visitBranchEqual(BranchEqualContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBranchEqual(ctx);
	}

	@Override
	public AssemblyNode visitBranchLessEqual(BranchLessEqualContext ctx) {
		// TODO Auto-generated method stub
		return super.visitBranchLessEqual(ctx);
	}

	@Override
	public AssemblyNode visitJs(JsContext ctx) {
		// TODO Auto-generated method stub
		return super.visitJs(ctx);
	}

	@Override
	public AssemblyNode visitLoadRA(LoadRAContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLoadRA(ctx);
	}

	@Override
	public AssemblyNode visitStoreRa(StoreRaContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStoreRa(ctx);
	}

	@Override
	public AssemblyNode visitLoadRV(LoadRVContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLoadRV(ctx);
	}

	@Override
	public AssemblyNode visitStoreRV(StoreRVContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStoreRV(ctx);
	}

	@Override
	public AssemblyNode visitLoadFP(LoadFPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLoadFP(ctx);
	}

	@Override
	public AssemblyNode visitStoreFP(StoreFPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStoreFP(ctx);
	}

	@Override
	public AssemblyNode visitCopyFP(CopyFPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitCopyFP(ctx);
	}

	@Override
	public AssemblyNode visitLoadHP(LoadHPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitLoadHP(ctx);
	}

	@Override
	public AssemblyNode visitStoreHP(StoreHPContext ctx) {
		// TODO Auto-generated method stub
		return super.visitStoreHP(ctx);
	}

	@Override
	public AssemblyNode visitPrint(PrintContext ctx) {
		// TODO Auto-generated method stub
		return super.visitPrint(ctx);
	}

	@Override
	public AssemblyNode visitHalt(HaltContext ctx) {
		// TODO Auto-generated method stub
		return super.visitHalt(ctx);
	}

	@Override
	public AssemblyNode visitNew(NewContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNew(ctx);
	}

	


	
	
}
