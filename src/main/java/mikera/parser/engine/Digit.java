package mikera.parser.engine;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;



public class Digit extends Parser {
	public Digit() {

	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		if (rl.getData()!=null) return null;
		
		if (pos>=s.length()) return null;
		Result r=null;
		char c=s.charAt(pos);
		if (Character.isDigit(c)) {
			r=new Result(new Integer((int)c-(int)'0'),s,pos,1);
			rl.setData(new Integer(1));
		}
		return r;
	}
}
