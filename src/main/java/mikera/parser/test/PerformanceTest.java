package mikera.parser.test;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.primitives.*;
import junit.framework.TestCase;

public class PerformanceTest extends TestCase {
	public void testManyWhiteSpace() {
		Parser p=new Repeat(new Whitespace());
		String ws="           ";
		
		Result r=p.parseFirst(ws);
		assertEquals(ws.length(),r.length);
		
		int c=p.parseCount(ws);
		assertEquals((1<<ws.length())-1,c);
	}
	
	public void testLongString() {
		Parser mws=new Maybe(new Whitespace());
		Parser p=new Sequence(new Repeat(new Sequence(mws,new Char("x"))), new End());
		StringBuilder sb=new StringBuilder();
		for (int i=0; i<10000; i++) {
			sb.append(" x  xx");
		}
		String s=sb.toString();
		
		//int c=p.parseCount(s);
		//assertEquals(1,c);
		Result r=p.parseFirst(s);
		assertEquals(s.length(),r.length);

	}
}
