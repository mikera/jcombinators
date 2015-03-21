package mikera.calculator;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.primitives.*;

import java.util.*;

public class Calculator {
	private Parser parser;
	
	public Parser whitespace=new Whitespace();
	public Parser maybespace=new Maybe(whitespace);
	public Parser plus=new Match("+");
	public Parser minus=new Match("-");
	public Parser multiply=new Match("*");
	public Parser divide=new Match("/");
	
	public Parser addop=new Choice(plus,minus);
	public Parser mulop=new Choice(multiply,divide);
	
	public Holder expression=new Holder();
	public Holder term=new Holder();
	
	public Parser integer=new Repeat(new Digit(),1,100) {
		public void action(Result r) {
			double v=0;
			List l=(List)(r.object);
			for (int i=0; i<l.size(); i++) {
				Result sr=r.getSubResult(i);
				int d=((Integer)(sr.object)).intValue();
				v=v*10+d;
			}
			r.object=new Double(v);
		}
	};
	
	
	public Parser number=new Choice(
		integer,
		new Sequence(integer,new Match("."),integer) {
			public void action(Result r) {
				double d1=r.getSubResult(0).asDouble();
				double d2=r.getSubResult(2).asDouble();
				
				int dp=r.getSubResult(2).matchedString().length();
				
				for (int i=0; i<dp; i++) {d2*=0.1;}
				
				r.object=new Double(d1+d2);
			}
		}
	);
	
	public Parser value=new Choice(
			number,
			new Sequence(new Match("("),maybespace,expression,maybespace,new Match(")")) {
				public void action(Result r) {
					r.object=new Double(r.getSubResult(2).asDouble());
				}
			}
	);
	
	public Parser mulexp=new Sequence(value,new Repeat(new Sequence(maybespace,mulop,maybespace,value),1)) {
		public void action(Result r) {
			double d1=r.getSubResult(0).asDouble();
			List dl=(List)r.getSubResult(1).object;
			
			for (Object or: dl) {
				Result rr=(Result)or;
				double d2=rr.getSubResult(3).asDouble();
				if (rr.getSubResult(1).asString().equals("*")) {
					d1=(d1*d2);
				} else {
					d1=(d1/d2);		
				}
			}
			r.object=new Double(d1);
		}
	};
	


	
	public Parser addexp=new Sequence(term,new Repeat(new Sequence(maybespace,addop,maybespace,term),1)) {
		public void action(Result r) {
			double d1=r.getSubResult(0).asDouble();
			List dl=(List)r.getSubResult(1).object;
			
			for (Object or: dl) {
				Result rr=(Result)or;
				double d2=rr.getSubResult(3).asDouble();
				if (rr.getSubResult(1).asString().equals("+")) {
					d1=(d1+d2);
				} else {
					d1=(d1-d2);		
				}
			}
			r.object=new Double(d1);
		}
	};
	
	
	public Calculator() {

		term.setParser(new Choice(
				mulexp,
				value
		));
			
		expression.setParser(new Choice(addexp,term));
		
		parser=new Sequence(maybespace,expression,maybespace,new End()) {
			public void action(Result r) {
				r.object=r.getSubResult(1).object;
			}
		};
	}
	
	public double calculate(String s) {
		
		Result r=parser.parseList(s).first();
		return ((Double)r.object).doubleValue();
	}
}
