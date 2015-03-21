package mikera.parser.primitives;

import java.util.*;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;

public class Sequence extends Parser {
	private Parser[] parsers;
	private int count;
	
	public Sequence(Parser p) {
		this(new Parser[] {p});
	}
	
	public Sequence(Parser p, Parser q) {
		this(new Parser[] {p,q});
	}
	
	public Sequence(Parser p, Parser q, Parser r) {
		this(new Parser[] {p,q,r});
	}
	
	public Sequence(Parser p, Parser q, Parser r,Parser s) {
		this(new Parser[] {p,q,r,s});
	}
	
	public Sequence(Parser p, Parser q, Parser r,Parser s, Parser t) {
		this(new Parser[] {p,q,r,s,t});
	}
	
	public Sequence(Parser[] ps) {
		parsers=(Parser[])ps.clone();
		count=parsers.length;
	}

	
	public Result parseNew(String s, int pos, ResultList rl) {
		ArrayList<Result> matches=(ArrayList<Result>)rl.getData();
		int currentpos=pos;
		boolean adding=false;
		if (matches==null) {
			matches=new ArrayList<Result>();
			rl.setData(matches);
			
			adding=true;
		} else if (matches.size()==0) {
			// already returned all matches
			return null;
		}
		
		int i=matches.size();
		
		// complete array
		while (adding||(i>0)) {
			Result r=null;
			
			if (adding) {
				// try to add add a new result
				r=parsers[i].parseList(s, currentpos).first();
				if (r==null) {
					adding=false;
					continue; 
					// continue, as we want to roll over on the next iteration
				}
				matches.add(r);
				i++;
				currentpos+=r.length;
			} else {
				// try to roll over to next result
				r=matches.get(i-1);
				r=r.getNext();
				if (r!=null) {
					// next result
					matches.set(i-1, r);
					currentpos=r.position+r.length;
					adding=true;
				} else {
					// no result, so backtrack
					matches.remove(i-1);
					i--;
					adding=false;
					if (i==0) return null;
					r=matches.get(i-1);
					currentpos=r.position+r.length;
				}
			}
			
			if (i==count) {
				Result radd=new Result(matches.clone(),s,pos,currentpos-pos);
				return radd;
			}
		}
		
		matches.clear();
		return null;
	}
}
