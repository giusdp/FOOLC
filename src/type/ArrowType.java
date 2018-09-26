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
		String parlstr="";
	    for (Type par:parlist)
	      parlstr+=par.toPrint(" ");
	    if (parlist.isEmpty()) {
	    	parlstr= " Void Type ";
	    }
		return s+"ArrowType: " + parlstr + " "+ ret.toPrint("-> ") ; 
	}
	  
	public Type getRet () { //
	    return ret;
	}
	  
	public ArrayList<Type> getParList () { //
	    return parlist;
	}

}
