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
import svm.SVMParser.DispatchLabelContext;
import svm.SVMParser.DivContext;
import svm.SVMParser.DuplicateTopContext;
import svm.SVMParser.HaltContext;
import svm.SVMParser.InstructionContext;
import svm.SVMParser.JsContext;
import svm.SVMParser.LabelContext;
import svm.SVMParser.LoadFPContext;
import svm.SVMParser.LoadHPContext;
import svm.SVMParser.LoadMethodContext;
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

	private HashMap<String,Integer> labelAdd = new HashMap<>();
    private HashMap<Integer,String> labelRef = new HashMap<>();
    private int codeIndex = 0;
    private List<AssemblyNode> assemblyNodes = new ArrayList<>();

	public List<AssemblyNode> buildCodeList(AssemblyContext ctx) {
		
		ctx.instruction().forEach(instrCtx -> {
			assemblyNodes.add(visit(instrCtx));
			codeIndex++;
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
		AssemblyNode an = new AssemblyNode(SVMParser.PUSH, codeIndex);
		an.setNumber(Integer.parseInt(ctx.n.getText())); // Qui si setta il numero che viene pushato
		return an;
	}

	@Override
	public AssemblyNode visitPushLabel(PushLabelContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.PUSH, codeIndex);
		labelRef.put(codeIndex, ctx.l.getText()); // La salvo nella label references map
		return an;
	}

	@Override
	public AssemblyNode visitPop(PopContext ctx) {
		return new AssemblyNode(SVMParser.POP, codeIndex);
	}

	@Override
	public AssemblyNode visitAdd(AddContext ctx) {
		return new AssemblyNode(SVMParser.ADD, codeIndex);
		
	}

	@Override
	public AssemblyNode visitSub(SubContext ctx) {
		return new AssemblyNode(SVMParser.SUB, codeIndex);
	}

	@Override
	public AssemblyNode visitMult(MultContext ctx) {
		return new AssemblyNode(SVMParser.MULT, codeIndex);
	}

	@Override
	public AssemblyNode visitDiv(DivContext ctx) {
		return new AssemblyNode(SVMParser.DIV, codeIndex);
	}

	@Override
	public AssemblyNode visitStoreW(StoreWContext ctx) {
		return new AssemblyNode(SVMParser.STOREW, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadW(LoadWContext ctx) {
		return new AssemblyNode(SVMParser.LOADW, codeIndex);
	}

	
	// Perchè assemblyNodes.size() in Label e nei branch?
	// Perchè le istruzioni partono da 1 e non da 0, assemblyNodes.size() è equivalente a fare codeIndex+1
	
	@Override
	public AssemblyNode visitLabel(LabelContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LABEL, codeIndex);
		labelAdd.put(ctx.l.getText(), assemblyNodes.size());
		return an;
	}
	
	

	@Override
	public AssemblyNode visitDispatchLabel(DispatchLabelContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.LABEL, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranch(BranchContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCH, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchEqual(BranchEqualContext ctx) {
        AssemblyNode an = new AssemblyNode(SVMParser.BRANCHEQ, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchLessEqual(BranchLessEqualContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHLESSEQ, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}
	

	@Override
	public AssemblyNode visitBranchLess(BranchLessContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHLESS, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchGreaterEqual(BranchGreaterEqualContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHGREATEREQ, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchGreater(BranchGreaterContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.BRANCHGREATER, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchAnd(BranchAndContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.AND, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitBranchOr(BranchOrContext ctx) {
		AssemblyNode an = new AssemblyNode(SVMParser.OR, codeIndex);
		labelRef.put(assemblyNodes.size(), ctx.l.getText());
		return an;
	}

	@Override
	public AssemblyNode visitJs(JsContext ctx) {
		return new AssemblyNode(SVMParser.JS, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadRA(LoadRAContext ctx) {
		return new AssemblyNode(SVMParser.LOADRA, codeIndex);
	}

	@Override
	public AssemblyNode visitStoreRa(StoreRaContext ctx) {
		return new AssemblyNode(SVMParser.STORERA, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadRV(LoadRVContext ctx) {
		return new AssemblyNode(SVMParser.LOADRV, codeIndex);
	}

	@Override
	public AssemblyNode visitStoreRV(StoreRVContext ctx) {
		return new AssemblyNode(SVMParser.STORERV, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadFP(LoadFPContext ctx) {
		return new AssemblyNode(SVMParser.LOADFP, codeIndex);
	}

	@Override
	public AssemblyNode visitStoreFP(StoreFPContext ctx) {
		return new AssemblyNode(SVMParser.STOREFP, codeIndex);
	}

	@Override
	public AssemblyNode visitCopyFP(CopyFPContext ctx) {
		return new AssemblyNode(SVMParser.COPYFP, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadHP(LoadHPContext ctx) {
		return new AssemblyNode(SVMParser.LOADHP, codeIndex);
	}

	@Override
	public AssemblyNode visitStoreHP(StoreHPContext ctx) {
		return new AssemblyNode(SVMParser.STOREHP, codeIndex);
	}

	@Override
	public AssemblyNode visitPrint(PrintContext ctx) {
		return new AssemblyNode(SVMParser.PRINT, codeIndex);
	}

	@Override
	public AssemblyNode visitHalt(HaltContext ctx) {
		return new AssemblyNode(SVMParser.HALT, codeIndex);
	}

	@Override
	public AssemblyNode visitNew(NewContext ctx) {
		return new AssemblyNode(SVMParser.NEW, codeIndex);
	}

	@Override
	public AssemblyNode visitLoadMethod(LoadMethodContext ctx) {
		return new AssemblyNode(SVMParser.LOADMETHOD, codeIndex);
	}

	@Override
	public AssemblyNode visitDuplicateTop(DuplicateTopContext ctx) {
		return new AssemblyNode(SVMParser.DUPLICATETOP, codeIndex);
	}

	
	
	
}
