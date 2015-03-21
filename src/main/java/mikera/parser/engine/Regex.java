package mikera.parser.engine;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;
import java.util.regex.*;

public class Regex extends Parser {
	private Pattern pattern;
	public Regex(String s) {
		super();
		pattern=Pattern.compile(s);
		
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		if (rl.getData()!=null) return null;
		
		CharSequence cs=s.subSequence(pos, s.length());
		Matcher m=pattern.matcher(cs);
		boolean b=m.lookingAt();
		
		if (b) {
			int len=m.end();
			Result r=new Result(null,s,pos,len);
			// TODO
			r.object=s.substring(pos, pos+len);
			rl.setData(r);
			
			return r;
		} else {
			return null;
		}
	}
	

}
