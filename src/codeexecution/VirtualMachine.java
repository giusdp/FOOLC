package codeexecution;

import java.util.*;

import svm.AssemblyNode;
import svm.SVMParser;
/**
 * @author Giuseppe
 *
 */
public class VirtualMachine {

	public static final int CODESIZE = 10000;
	public static final int MEMSIZE = 10000;

	private List<AssemblyNode> code;
	// private int[] code;
	private int[] memory = new int[MEMSIZE];

	private int ip = 0;
	private int sp = MEMSIZE;

	private int hp = 0;
	private int fp = MEMSIZE;
	private int ra;
	private int rv;


	private Heap heap = new Heap(MEMSIZE);

	public VirtualMachine(List<AssemblyNode> code) {
		this.code = code;
	}

	public void cpu() throws Exception {
		System.out.println("CODE SIZE " + code.size());
		while (true) {
			printStack();
			AssemblyNode instruction = code.get(ip++); // fetch next instruction identifier
			int v1, v2;
			int address;
			switch (instruction.getInstructionID()) {
			case SVMParser.PUSH:
				if (instruction.isWithLabel()) {
					System.out.println("push label address: " + instruction.getLabelAddress());
					push(instruction.getLabelAddress());
				}
				else {
					System.out.println("push " + instruction.getNumber());
					push(instruction.getNumber());
				}
				break;
			case SVMParser.POP:
				System.out.println("pop");
				pop();
				break;
			case SVMParser.ADD:
				v1 = pop();
				v2 = pop();
				System.out.println("add " + v1 + " " + v2);
				push(v2 + v1);
				break;
			case SVMParser.MULT:
				v1 = pop();
				v2 = pop();
				System.out.println("mult " + v1 + " " + v2);
				push(v2 * v1);
				break;
			case SVMParser.DIV:
				v1 = pop();
				v2 = pop();
				System.out.println("div " + v1 + " " + v2);
				push(v2 / v1);
				break;
			case SVMParser.SUB:
				v1 = pop();
				v2 = pop();
				System.out.println("sub " + v1 + " " + v2);
				push(v2 - v1);
				break;
			case SVMParser.STOREW: //
				address = pop();
				System.out.println("sw " + address);
                setMemory(address, pop());
                break;
			case SVMParser.LOADW: //
				address = pop();
				System.out.println("lw " + address);
				push(getMemory(address));
                break;
			case SVMParser.LABEL:
				System.out.println("Label instruction, nothing to do");
				break;
			case SVMParser.BRANCH:
				address = instruction.getLabelAddress();
				System.out.println("Branch to " + address);
				ip = address;
				break;
			case SVMParser.BRANCHEQ: //
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("BEQ " + v1 + " " + v2 + ", label to instruction " + address);
				if (v2 == v1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.BRANCHLESSEQ:
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("BLEQ " + v2 + " " + v1 + ", label to instruction " + address);
				if (v2 <= v1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.BRANCHLESS:
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("BL " + v2 + " " + v1 + ", label to instruction " + address);
				if (v2 < v1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.BRANCHGREATEREQ:
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("BGEQ " + v2 + " " + v1 + ", label to instruction " + address);
				if (v2 >= v1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.BRANCHGREATER:
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("BG " + v2 + " " + v1 + ", label to instruction " + address);
				if (v2 > v1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.AND: 
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("AND " + v2 + " " + v1 + ", label to instruction " + address);
				if (v1 == 1 && v2 == 1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.OR: 
				address = instruction.getLabelAddress(); 
				v1 = pop();
				v2 = pop();
				System.out.println("OR " + v2 + " " + v1 + ", label to instruction " + address);
				if (v1 == 1 || v2 == 1)
					ip = address;
				else
					System.out.println("False, not jumping there");
				break;
			case SVMParser.JS:
				address = pop();
				ra = ip;
				ip = address;
				System.out.println("js " + address);
				break;
			case SVMParser.STORERA:
				ra = pop();
				System.out.println("sra");
				break;
			case SVMParser.LOADRA:
				push(ra);
				System.out.println("lra");
				break;
			case SVMParser.STORERV:
				rv = pop();
				System.out.println("srv");
				break;
			case SVMParser.LOADRV:
				push(rv);
				System.out.println("lrv");
				break;
			case SVMParser.LOADFP:
				push(fp);
				System.out.println("lfp");
				break;
			case SVMParser.STOREFP:
				fp = pop();
				System.out.println("sfp");
				break;
			case SVMParser.COPYFP:
				System.out.println("cfp");
				fp = sp;
				break;
			case SVMParser.STOREHP:
				hp = pop();
				System.out.println("shp");
				break;
			case SVMParser.LOADHP:
				push(hp);
				System.out.println("lhp");
				break;
			case SVMParser.PRINT:
				System.out.println((sp < MEMSIZE) ? memory[sp] : "Empty stack!");
				break;

			case SVMParser.NEW:
				// Si assume che nel top dello stack c'è la classe in question (per la dispatch
				// table), il numero di campi e i loro valori
				// dall'ultimo al primo.

				System.out.println("new");
				
				int dispatchTableAddress = pop();
				int fieldNumber = pop();
				int[] fieldValues = new int[fieldNumber];
				for (int i = 0; i < fieldNumber; ++i) {
					fieldValues[i] = pop();
				}

				HeapBlock allocatedMemory; // Primo blocco della lista di heap blocks "allocati", con next si accede al
											// blocco successivo

				// Bisogna allocare memoria per i campi e l'indirizzo alla dispatch table
				allocatedMemory = heap.alloc(fieldNumber + 1);

				// Si prende la prima posizione per l'array memory della lista di heap blocks
				// allocati
				int heapMemoryStart = allocatedMemory.getPointedPosition();

				// Si inserisce nell'array memory l'indirizzo alla dispatch table alla posizione
				// getPointedPosition() dell'heapblock attuale (il primo)
				// ed avanzo al prossimo heap block
				setMemory(allocatedMemory.getPointedPosition(), dispatchTableAddress);
				allocatedMemory = allocatedMemory.next;

				// Si inseriscono uno alla volta i campi passati in input, spostandoci uno ad
				// uno all'heap block successivo
				for (int i = 0; i < fieldNumber; i++) {
					setMemory(allocatedMemory.getPointedPosition(), fieldValues[i]);
					allocatedMemory = allocatedMemory.next;
				}
				// Si fa un push sullo stack della prima posizione puntata all'array memory (da
				// dove parte la classe nell'heap)
				push(heapMemoryStart);

				// Caso in cui l'heap superi lo stack
				if (heap.getHeadIndex() > hp) {
					hp = heap.getHeadIndex();
				}

				// TODO E se la memoria terminasse? Errore e il programma esce. Oppure garbage collector se lo facciamo

				break;
				
			case SVMParser.LOADMETHOD:
				int methodAddress = pop();
                push(code.get(methodAddress).getLabelAddress()); // primo metodo della classe
                System.out.println("lm " + methodAddress);
				break;
				
			case SVMParser.DUPLICATETOP:
				push(getMemory(sp));
				System.out.println("cts " + getMemory(sp));
				break;
			case SVMParser.HALT:
				System.out.println("HALT!");
				return;
			}
		}
	}

	/** Inserisce il valore value in memoria all'indirizzo address. */
	private void setMemory(int address, int value) throws Exception {
		if (address < 0 || address >= MEMSIZE) {
			throw new Exception("Segmentation Fault Error");
		}
		memory[address] = value;
	}

	/** Legge e restituisce il valore contenuto all'indirizzo address, non toglie il valore dalla memoria. */
    private int getMemory(int address) throws Exception {
        if (address < 0 || address >= MEMSIZE) {
            throw new Exception("Segmentation Fault Error");
        }
        int m =	memory[address];
        return m;
    }
	
	private int pop() {
		return memory[sp++];
	}

	private void push(int v) {
		memory[--sp] = v;
	}

	private void printStack() {
		System.out.println("******** STACK STATE ********");
		if (!(sp < MEMSIZE)) {
			System.out.println("Empty stack!");
			System.out.println();
			return;
		}
		for (int i = sp; i < MEMSIZE; i++) {
			System.out.println(memory[i]);
		}
		System.out.println();
	}

}
