package mikera.parser;

import java.util.*;


public class Parser implements Action {
	private WeakHashMap<String,WeakHashMap<Integer,ResultList>> memo=new WeakHashMap<String,WeakHashMap<Integer,ResultList>>();
	protected Action action;

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
	
	public Result parseFirst(String s) {
		return parseList(s,0).first();
	}
	
	public int parseCount(String s) {
		return parseList(s,0).count();
	}
	
	protected void clearMemo() {
		memo.clear();
	}
	
	
	public ResultList parseList(String s) {
		return parseList(s,0);
	}
	
	public ResultList parseList(String s, int pos) {
		WeakHashMap<Integer,ResultList> results=memo.get(s);
		
		ResultList r=null;
		Integer i=new Integer(pos);
		
		if (results!=null) {
			r=results.get(i);
			if (r!=null ) {
				return r;
			} 
		} else {
			results=new WeakHashMap<Integer,ResultList>();
			memo.put(s,results);
		}
		
		r=new ResultList(this,s,pos);

		results.put(i, r);
		return r;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		return null;
	}
	
	public void setAction(Action a) {
		action=a;
	}

	public void action(Result r) {
		if (action!=null) {
			action.action(r);
		}
	}
}
