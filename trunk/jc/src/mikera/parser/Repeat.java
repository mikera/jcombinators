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
		ArrayList<Result> matches=(ArrayList<Result>)rl.getData();
		int currentpos=pos;
		if (matches==null) {
			matches=new ArrayList<Result>();
			rl.setData(matches);
			
			// get as many results as possible
			for (int i=matches.size(); i<max; i++) {
				Result r=parser.parseList(s, currentpos).first();
				if (r==null) break;
				matches.add(r);
				currentpos+=r.length;
			}
			
			// return first result
			if (matches.size()>=min) {
				Result r=new Result(matches.clone(),s,pos,currentpos-pos);
				return r;
			}
		} else if (matches.size()==0) {
			// already returned all matches
			return null;
		}
		
		// complete array
		for (int i=matches.size(); i>=min; i--) {
			boolean backup=false;
			Result r=null;
			// we are at the end
			r=matches.get(i-1);
			r=r.getNext();
			if (r!=null) {
				matches.set(i-1, r);
				currentpos=r.position+r.length;
			} else {
				matches.remove(i-1);
				i--;
				backup=true;
				if (i==0) return null;
				r=matches.get(i-1);
				currentpos=r.position+r.length;
			}
				
			if (!backup) {
				while(i<max) {	
					Result rnext=parser.parseList(s, currentpos).first();
					if (rnext==null) break;
					matches.add(rnext);
					i++;
					currentpos+=rnext.length;
				}	
			}
			
			Result radd=new Result(matches.clone(),s,pos,currentpos-pos);
			return radd;
		}
		
		matches.clear();
		return null;
	}
	
	public void action(Result r) {
		
	}
}
