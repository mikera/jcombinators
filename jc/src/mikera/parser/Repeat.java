package mikera.parser;

import java.util.*;

public class Repeat extends Parser {
	private Parser parser;
	private int min=1;
	private int max=Integer.MAX_VALUE;
	
	public Repeat() {
		super();
	}
	
	public Repeat(Parser p) {
		parser=p;
	
	}
	
	public Repeat(Parser p, int minimum) {
		this(p);
		min=minimum;
	}
	
	public Repeat(Parser p, int minimum, int maximum) {
		this(p);
		min=minimum;
		max=maximum;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		ArrayList matches=(ArrayList)rl.getData();
		if (matches==null) {
			matches=new ArrayList();
			rl.setData(matches);
		} else if (matches.size()==0) {
			// already returned all matches
			return null;
		}
		int currentpos=pos;
		
		// complete array
		for (int i=matches.size(); i<=max; i++) {
			if (i<0) return null;

			boolean backup=false;
			Result r=null;
			// advance if not at end of matches
			if (i<max) {
				// get right position if not at start
				if (i>0) {
					Result tr=(Result)(matches.get(i-1));
					currentpos=tr.position+tr.length;
				}
				
				r=parser.parseList(s, currentpos).first();
				if (r!=null) {
					matches.add(r);
					currentpos=r.position+r.length;
					if ((i+1)>=min) {
						break;
					}
				} else {
					backup=true;
				}
			} else {
				// we are at the end
				r=(Result)(matches.get(i-1));
				r=r.getNext();
				if (r!=null) {
					matches.set(i-1, r);
					currentpos=r.position+r.length;
					break;
				} else {
					matches.remove(i-1);
					i-=1;
					backup=true;
				}
			}
				
			if (backup) {
				while (true) {
					if (i<=0) return null;
					r=(Result)(matches.get(i-1));
					r=r.getNext();
					i-=1;	
					if (r!=null) break;
				} 
				// next candidate
				matches.set(i, r);
			}
		}
		
		Result r=new Result(matches.clone(),s,pos,currentpos-pos);
		return r;
	}
	
	public void action(Result r) {
		
	}
}
