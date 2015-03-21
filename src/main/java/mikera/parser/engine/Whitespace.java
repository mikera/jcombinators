package mikera.parser.primitives;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;

/**
 * Whitespace parses 1 or more whitespace characters
 * 
 * @author Mike Anderson
 *
 */

public class Whitespace extends Parser {
	public Whitespace() {

	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		Integer num=(Integer)(rl.getData());
		
		int left=s.length()-pos;
		if (left==0) return null;
		if (num==null) {
			for (int i=0; i<left; i++) {
				char c=s.charAt(pos+i);
				if (Character.isWhitespace(c)) {
					continue;
				} else {
					// non-whitespace, so finish
					if (i==0) {
						rl.setData(new Integer(0));
						return null;
					}
					Result r= new Result(null,s,pos,i);
					rl.setData(new Integer(i));
					return r;
				}
			}
			// all of remaining string is whitespace
			Result r= new Result(null,s,pos,left);
			rl.setData(new Integer(left));
			return r;
		} else {
			int lastNum=num.intValue();
			if (lastNum<=1) return null;
			rl.setData(new Integer(lastNum-1));
			Result r= new Result(null,s,pos,lastNum-1);
			return r;
		}
		
	}
}
