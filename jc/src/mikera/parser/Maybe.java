package mikera.parser;

public class Maybe extends Parser {
	private Parser parser;

	public Maybe() {
		super();
	}
	
	public Maybe(Parser p) {
		parser=p;
	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		Result sr=(Result)rl.getData();
		Result r=null;
		
		if (sr==null) {
			r= new Result(null,s,pos,0);
			rl.setData(r);
			return r;
		} else {
			if (sr.length>0) {
				sr=sr.getNext();
				if (sr==null) return null;
				r= new Result(sr,s,pos,sr.length);
				rl.setData(sr);
				return r;
			} else {
				sr=parser.parseList(s, pos).first();
				if (sr==null) return null;
				r= new Result(sr,s,pos,sr.length);
				rl.setData(sr);
				return r;
			}
		}
	}
	
	public void action(Result r) {
		
	}
}
