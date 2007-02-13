package mikera.parser.primitives;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;

public class Char extends Parser {
	private String options;
	
	public Char(String s) {
		super();
		options=s;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		if (rl.getData()!=null) return null;
		if (pos>=s.length()) return null;
		
		char c=s.charAt(pos);
		if (options.indexOf(c)>=0) {
			Result r=new Result(Character.toString(c),s,pos,1);
			rl.setData(r);
			return r;
		} else {
			return null;
		}
	}
}
