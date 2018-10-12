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
	
}
