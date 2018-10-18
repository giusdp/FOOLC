package svm;

public class AssemblyNode {

	//TODO Un assembly node rappresenta una istruzione svm
	private int instruction; // int representing an instruction, from antlr
	
	private String label; // If it's a push or some instruction with labels we store it here
	private int number; // if it's a push number we store it here
	
	public AssemblyNode(int instr) {
		this.instruction = instr;
	}

	public int getInstruction() {
		return instruction;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
	
	
	
}
