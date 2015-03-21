package mikera.parser;
import mikera.parser.engine.*;
import java.util.*;

public class Factory {
	public Parser match(String s) {
		return new Match(s);
	}
	
	public Parser match(char c) {
		return new Char(Character.toString(c));
	}
	
	public Parser whitespace() {
		return new Whitespace();
	}
	
	public Parser maybespace() {
		return new Maybe(new Whitespace());
	}
	
	public Parser maybe(Parser p) {
		return new Maybe(p);
	}
	
	public Holder holder(Parser p) {
		return new Holder(p);
	}
	
	public Parser sequence(Parser a) {
		return new Sequence(a);
	}
	
	public Parser sequence(Parser a, Parser b) {
		return new Sequence(a,b);
	}
	
	public Parser sequence(Parser a, Parser b, Parser c) {
		return new Sequence(a,b,c);
	}
	
	public Parser list(Parser item, Parser separator) {
		Parser lp= sequence(new Repeat(sequence(item,separator),0),item);
		// TODO: sort out object
		lp.setAction(new Action() {
			public void action(Result r) {

			}
		});
		return holder(lp);
	}
}
