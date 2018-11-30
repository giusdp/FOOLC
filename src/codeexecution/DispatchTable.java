package codeexecution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Giuseppe
 *
 */

public class DispatchTable {

	// Dispatch Tables delle classi. La stringa/chiave è la classe, la lista di DTEntry è l'array di metodi
	// come spiegato nella slide 72 della codegen. Si accede al metodo richiesto tramite
	// l'offset appropriato.
    private static HashMap<String, List<DTEntry>> dispatchTables = new HashMap<>();
 
    public static void addDispatchTable(String classID, List<DTEntry> dtEntryList) {
        dispatchTables.put(classID, dtEntryList);
    }


    public static List<DTEntry> getDispatchTableOfClass(String classID) {
        List<DTEntry> dtList = dispatchTables.get(classID);
        return new ArrayList<>(dtList);
    }

    public static String codeGeneration() {
    	
    	StringBuilder stringBuilder = new StringBuilder();

    	// Per ogni classe genera una label della classe 'nomeClasse_class' e sotto riga per riga una label per ogni suo metodo.
    	dispatchTables.forEach((className, entryList) -> {
    		stringBuilder.append(className).append("_class:\n");
    		entryList.forEach(entry -> stringBuilder.append(entry.getMethodLabel()));
    	});
    	
        return stringBuilder.toString();
    }

}
