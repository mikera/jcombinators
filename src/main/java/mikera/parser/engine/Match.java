package mikera.parser.engine;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;

public class Match extends Parser {
	private String string;
	private int length;
	
	public Match(String s) {
		super();
		string=s;
		length=s.length();
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		if (rl.getData()!=null) return null;
		
		if (s.regionMatches(pos, string, 0, length)) {
			Result r=new Result(null,s,pos,length);
			r.object=r.matchedString();
			rl.setData(r);
			return r;
		} else {
			return null;
		}
	}
	

}
