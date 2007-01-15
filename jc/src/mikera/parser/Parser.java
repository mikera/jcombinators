package mikera.parser;

import java.util.*;

public class Parser {
	private Map memo=new HashMap();
	
	public Parser() {
		
	}
	
	public Result parse(String s) {
		return parse(s,0);
	}
	
	public Result parse(String s, int i) {
		ResultList rl=parseList(s,i);
		Result r= rl.first();
		if ((r!=null)&&(r.getNext()!=null)) throw new Error("Ambiguous parse");
		return r;
	}
	
	public ResultList parseList(String s) {
		return parseList(s,0);
	}
	
	public ResultList parseList(String s, int pos) {
		ResultList[] results=(ResultList[])memo.get(s);
		ResultList r=null;
		
		if (results!=null) {
			r=results[pos];
			if (r!=null ) {
				return r;
			} 
		} else {
			results=new ResultList[s.length()+1];
			
		}
		
		r=new ResultList(this,s,pos);

		results[pos]=r;
		return r;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		return null;
	}
	
	public void action(Result r) {
	
	}
}
