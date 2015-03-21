package mikera.parser.engine;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;



public class End extends Parser {
	public End() {
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		if (rl.getData()!=null) return null;
		
		if (pos==s.length()) {
			Result r=new Result(null,s,pos,0);
			rl.setData(r);
			return r;
		} else {
			return null;
		}
	}	
}
