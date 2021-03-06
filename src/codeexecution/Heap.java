package codeexecution;

public class Heap {

	private HeapBlock head;
    private int heapSize;

    Heap(int size) {
        HeapBlock[] heapBlockArray = new HeapBlock[size];
        heapSize = size;

        heapBlockArray[size - 1] = new HeapBlock(size - 1, null); //l'ultimo elemento punta a null

        // Gli altri campi puntano a quello dopo
        for (int i = size - 2; i >= 0; i--) {
        	heapBlockArray[i] = new HeapBlock(i, heapBlockArray[i + 1]);
        }

        // Il primo elemento e' la testa della lista
        head = heapBlockArray[0];
    }
    
    HeapBlock alloc(int size) throws Exception {

        if (heapSize < size) throw new Exception("HeapError: Heap Overflow");

        HeapBlock allocatedHead = this.head; //testa del blocco da allocare

        HeapBlock allocatedLastBlock = head;
        for (int i = 1; i < size; i++) {
            allocatedLastBlock = allocatedLastBlock.next;
        }
        this.head = allocatedLastBlock.next; // sposta opportunamente la testa del heap libero


        allocatedLastBlock.next = null; // L'ultimo elemento restituito deve puntare a null
        heapSize = heapSize - size;
        return allocatedHead;
    }

    int getHeadIndex() {
        if (head != null) {
            return head.getPointedPosition();
        } else {
            return -1;
        }
    }

	
}
