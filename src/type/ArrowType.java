package type;

import java.util.ArrayList;

public class ArrowType implements Type {

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
	      parlstr+=par.toPrint(s+"  ");
		return s+"ArrowType\n" + parlstr + ret.toPrint(s+"  ->") ; 
	  }
	  
	  public Type getRet () { //
	    return ret;
	  }
	  
	  public ArrayList<Type> getParList () { //
	    return parlist;
	  }

}
