package svm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import svm.SVMParser.AddContext;
import svm.SVMParser.AssemblyContext;
import svm.SVMParser.BranchAndContext;
import svm.SVMParser.BranchContext;
import svm.SVMParser.BranchEqualContext;
import svm.SVMParser.BranchGreaterContext;
import svm.SVMParser.BranchGreaterEqualContext;
import svm.SVMParser.BranchLessContext;
import svm.SVMParser.BranchLessEqualContext;
import svm.SVMParser.BranchOrContext;
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

	private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
    private int codeIndex = 0;
	
	public List<AssemblyNode> buildCodeList(AssemblyContext ctx) {
		List<AssemblyNode> assemblyNodes = new ArrayList<>();
		
		ctx.instruction().forEach(instrCtx -> {
			codeIndex++;
			assemblyNodes.add(visit(instrCtx));
		});
		
		// Link intruction that pushes label with instruction that defines label
		labelRef.keySet().forEach(labelPushLocation -> {
			String labelName = labelRef.get(labelPushLocation);
			Integer indexToLabelCall = labelAdd.get(labelName);
			assemblyNodes.get(labelPushLocation).setLabelAddress(indexToLabelCall);
		});
		
		return assemblyNodes;
	}

	@Override
	public AssemblyNode visitPushNumber(PushNumberContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.PUSH);
		an.setNumber(Integer.parseInt(ctx.n.getText())); // Qui si setta il numero che viene pushato
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitPushLabel(PushLabelContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.PUSH);
		labelRef.put(codeIndex, ctx.l.getText()); // La salvo nella label references map
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitPop(PopContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.POP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitAdd(AddContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.ADD);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitSub(SubContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.SUB);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitMult(MultContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.MULT);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitDiv(DivContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.DIV);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitStoreW(StoreWContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.STOREW);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLoadW(LoadWContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LOADW);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLabel(LabelContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LABEL);
		labelAdd.put(ctx.l.getText(), codeIndex);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranch(BranchContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCH);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchEqual(BranchEqualContext ctx) {
        AssemblyNode an = new AssemblyNode(SVMParser.BRANCHEQ);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchLessEqual(BranchLessEqualContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHLESSEQ);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}
	

	@Override
	public AssemblyNode visitBranchLess(BranchLessContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHLESS);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchGreaterEqual(BranchGreaterEqualContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHGREATEREQ);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchGreater(BranchGreaterContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHGREATER);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchAnd(BranchAndContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.AND);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitBranchOr(BranchOrContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.OR);
		labelRef.put(codeIndex, ctx.l.getText());
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitJs(JsContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.JS);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLoadRA(LoadRAContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LOADRA);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitStoreRa(StoreRaContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.STORERA);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLoadRV(LoadRVContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LOADRV);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitStoreRV(StoreRVContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.STORERV);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLoadFP(LoadFPContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LOADFP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitStoreFP(StoreFPContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.STOREFP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitCopyFP(CopyFPContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.COPYFP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitLoadHP(LoadHPContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LOADHP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitStoreHP(StoreHPContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.STOREHP);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitPrint(PrintContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.PRINT);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitHalt(HaltContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.HALT);
		an.setCodeIndex(codeIndex);
		return an;
	}

	@Override
	public AssemblyNode visitNew(NewContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.NEW);
		an.setCodeIndex(codeIndex);
		return an;
	}

	


	
	
}
