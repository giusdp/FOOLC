package codegen;

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

        HeapBlock res = head; //deve essere restituita la testa della lista


        HeapBlock lastItem = head;
        for (int i = 1; i < size; i++) {
            lastItem = lastItem.next;
        }
        head = lastItem.next; // La testa della lista diventa il primo elemento dopo l'ultimo restituito


        lastItem.next = null; // L'ultimo elemento restituito deve puntare a null
        heapSize = heapSize - size;
        return res; 
    }


    void free(HeapBlock heapBlock) {
        int freedSpace = 1;
        HeapBlock curr = heapBlock;

        while (curr.next != null) {
        	freedSpace++;
            curr = curr.next;
        }
        curr.next = head;  // L'ultimo elemento della lista restituita va fatto puntare a head


        head = heapBlock;  // La testa della lista dei blocks sara' il primo elemento della lista deallocata
        heapSize = heapSize + freedSpace;
    }

    int getHeadIndex() {
        if (head != null) {
            return head.getPointedPosition();
        } else {
            return -1;
        }
    }

	
}
