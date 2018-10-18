package svm;

import java.util.ArrayList;
import java.util.List;

import svm.SVMParser.AddContext;
import svm.SVMParser.AssemblyContext;
import svm.SVMParser.BranchContext;
import svm.SVMParser.BranchEqualContext;
import svm.SVMParser.BranchLessEqualContext;
import svm.SVMParser.CopyFPContext;
import svm.SVMParser.DivContext;
import svm.SVMParser.HaltContext;
import svm.SVMParser.InstructionContext;
import svm.SVMParser.JsContext;
import svm.SVMParser.LabelContext;
import svm.SVMParser.LoadFPContext;
import svm.SVMParser.LoadHPContext;
import svm.SVMParser.LoadRAContext;
import svm.SVMParser.LoadRVContext;
import svm.SVMParser.LoadWContext;
import svm.SVMParser.MultContext;
import svm.SVMParser.NewContext;
import svm.SVMParser.PopContext;
import svm.SVMParser.PrintContext;
import svm.SVMParser.PushLabelContext;
import svm.SVMParser.PushNumberContext;
import svm.SVMParser.StoreFPContext;
import svm.SVMParser.StoreHPContext;
import svm.SVMParser.StoreRVContext;
import svm.SVMParser.StoreRaContext;
import svm.SVMParser.StoreWContext;
import svm.SVMParser.SubContext;

public class AssemblyVisitor extends SVMBaseVisitor<AssemblyNode> {

	
	public List<AssemblyNode> buildCodeList(AssemblyContext ctx) {
		List<AssemblyNode> assemblyNodes = new ArrayList<>();
		for (InstructionContext instrCtx : ctx.instruction()) {
			assemblyNodes.add(visit(instrCtx));
		}
		return assemblyNodes;
	}

	@Override
	public AssemblyNode visitPushNumber(PushNumberContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.PUSH);
		an.setNumber(Integer.parseInt(ctx.n.getText())); // Qui si setta il numero che viene pushato
		return an;
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
		return new AssemblyNode(SVMParser.HALT);
	}

	@Override
	public AssemblyNode visitNew(NewContext ctx) {
		// TODO Auto-generated method stub
		return super.visitNew(ctx);
	}

	


	
	
}
