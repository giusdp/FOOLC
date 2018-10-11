package codegen;

import java.util.HashMap;
import java.util.List;

/**
 * @author Giuseppe
 *
 */

public class DispatchTable {

	// Dispatch Table. La stringa/chiave è la classe, la lista di DTEntry è l'array di metodi
	// come spiegato nella slide 72 della codegen. Si accede al metodo richiesto tramite
	// l'offset appropriato.
    private static HashMap<String, List<DTEntry>> dispatchTables = new HashMap<>();
 
	
}
