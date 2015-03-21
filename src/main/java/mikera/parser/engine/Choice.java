package mikera.parser.primitives;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;

public class Choice extends Parser {
	Parser[] choices;
	
	public Choice(Parser a, Parser b) {
		choices=new Parser[] {a,b};
		
	}
	
	public Choice(Parser a, Parser b, Parser c) {
		choices=new Parser[] {a,b,c};
		
	}
	
	public Choice(Parser a, Parser b, Parser c, Parser d) {
		choices=new Parser[] {a,b,c,d};
		
	}
	
	private class ListPosition {
		int choice;
		Result r;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		ListPosition last=(ListPosition)rl.getData();
		if (last==null) {
			last=new ListPosition();
			last.choice=0;
			rl.setData(last);
		}
		
		for (int i=last.choice; i<choices.length; i++) {
			if (last.r!=null) {
				last.r=last.r.getNext();
			} else {
				last.r=choices[i].parseList(s, pos).first();
			}
			if (last.r!=null) {
				return new Result(last.r.object,s,pos,last.r.length);
			}
			last.choice=i+1;
		}
		
		return null;
	}
	
	public void action(Result r) {
		
	}
}
