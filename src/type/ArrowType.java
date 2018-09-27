package type;

import java.util.ArrayList;

public class ArrowType extends Type {
	
	private ArrayList<Type> parlist; 
	private Type ret;
	  
	public ArrowType(ArrayList<Type> p, Type r) {
		parlist=p;
	    ret=r;
	  }
	    
	@Override
	public String toPrint(String s) { //
		String parListString="";
	    for (Type par:parlist) {
	    	String parString = par.toPrint(" ");
	    	parListString+= parString.substring(0, parString.length()-1);
	      }
	    if (parlist.isEmpty()) {
	    	parListString= " Void Type ";
	    }
		return s+"ArrowType: " + parListString + " "+ ret.toPrint("-> ") ; 
	}
	  
	public Type getRet () { //
	    return ret;
	}
	  
	public ArrayList<Type> getParList () { //
	    return parlist;
	}

}
