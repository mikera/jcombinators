package mikera.parser;

import java.util.*;

public class Sequence extends Parser {
	private Parser[] parsers;
	
	public Sequence() {
	
	}
	
	public Sequence(Parser p) {
		parsers=new Parser[] {p};
	}
	
	public Sequence(Parser p, Parser q) {
		parsers=new Parser[] {p,q};
	}
	
	public Sequence(Parser p, Parser q, Parser r) {
		parsers=new Parser[] {p,q,r};
	}
	
	public Sequence(Parser p, Parser q, Parser r,Parser s) {
		parsers=new Parser[] {p,q,r,s};
	}
	
	public Sequence(Parser p, Parser q, Parser r,Parser s, Parser t) {
		parsers=new Parser[] {p,q,r,s,t};
	}
	
	public Sequence(Parser[] ps) {
		parsers=(Parser[])ps.clone();
	}

	
	public Result parseNew(String s, int pos, ResultList rl) {
		ArrayList matches=(ArrayList)rl.getData();
		if (matches==null) {
			matches=new ArrayList(parsers.length);
			rl.setData(matches);
		} else if (matches.size()==0) {
			// already returned all matches
			return null;
		}
		int currentpos=pos;
		
		// complete array
		for (int i=matches.size(); i<=parsers.length; i++) {
			if (i<0) return null;
			boolean backup=false;
			Result r=null;
			// advance if not at end of matches
			if (i<parsers.length) {
				r=parsers[i].parseList(s, currentpos).first();
				if (r!=null) {
					matches.add(r);
					currentpos=r.position+r.length;
					if (i==parsers.length-1) {
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
}
