package mikera.parser.engine;

import mikera.parser.*;

public class Holder extends Parser {
	private Parser parser;
	public Holder() {
	
	}
	
	public Holder(Parser p) {
		parser=p;
	}
	
	public ResultList parseList(String s, int pos) {
		ResultList rl= parser.parseList(s,pos);
		return rl;
	}

	public void setParser(Parser p) {
		parser=p;
	}
}
