package mikera.parser.test;

import junit.framework.TestCase;
import mikera.parser.*;

import java.util.*;

public class ParserTest extends TestCase {
	public void testParser() {
		String s="this is a test";
		String t="dcuidvdfvugeduv";
		
		Parser p=new Match("this") {
			
		};
		
		Parser q=new Match("dcuid") {
			
		};
		
		Parser c=new Choice(p,q);
		
		assertTrue(p.parse(s,0)!=null);
		assertTrue(p.parse(t,0)==null);
		
		
		ResultList rl=c.parseList(t,0);
		assertEquals(1,rl.count());
		
		
		Result r=null;
		
		
		
		r=c.parse(t,0);
		assertTrue(r!=null);
		assertTrue(r.length==5);
		
		r=c.parse(s,0);
		assertTrue(r!=null);
		assertTrue(r.length==4);
		
	}
	
	public void testMaybe() {
		Parser whitespace=new Whitespace();
		Parser maybespace=new Maybe(whitespace);
		
		assertEquals(1,maybespace.parseList("a").count());
		assertEquals(2,maybespace.parseList(" a").count());
		assertEquals(1,maybespace.parseList("").count());
	}

	
	public void testRepeat() {
		Parser pa=new Sequence(new Repeat(new Match("a")),new End());
		assertNotNull(pa.parse("a"));
		assertNotNull(pa.parse("aaaaaaaa"));

		
		String s=new String("ababababa");
		
		Parser p=new Repeat(new Match("ab"));
		
		ResultList rl=p.parseList(s,0);
		assertEquals(4,rl.count());
		
		Result r=rl.first();
		assertTrue(r!=null);
		assertTrue(r.object instanceof ArrayList);
		
		Parser q=new Sequence(new Repeat(new Match("ab"),1,4), new End());
		assertNull(q.parse("",0));
		assertNotNull(q.parse("ab",0));
		assertNotNull(q.parse("abababab",0));
		assertNull(q.parse("ababababab",0));
		}
	
	public void testWhiteSpace() {
		Parser p=new Whitespace();
		
		assertTrue(p.parse("",0)==null);
		assertTrue(p.parse(" ",0)!=null);
		assertEquals(2,p.parseList("  ").count());
		assertEquals(3,p.parseList(" \t ").count());
		
		assertTrue(p.parse("a a",0)==null);
		assertTrue(p.parse("a a",1)!=null);
	}
	
	public void testChoice() {
		Parser p=new Choice(new Match("a"),new Match("b"));
		
		assertNotNull(p.parse("axxx"));
		assertNotNull(p.parse("bxxx"));
		assertNull(p.parse("cxxx"));
	}

	public void testDigit() {
		Parser p=new Digit();
		
		assertNull(p.parse("",0));
		assertNotNull(p.parse("1",0));
		
		Result r=p.parse("7");
		
		Integer i=(Integer)(r.object);
		
		assertEquals(i.intValue(),7);
	}
	
	public void testEnd() {
		Parser p=new End();
		
		assertNotNull(p.parse(""));
		assertNotNull(p.parse("a",1));
		assertNull(p.parse("a",0));
	}
	
	
	public void testSequence() {
		Parser p=new Sequence(new Match("a"),new Match("b"));
		
		Result r=p.parse("ab");
		assertNotNull(r);
		
		r=p.parse("aa");
		assertNull(r);
		
		r=p.parse("aabb",1);
		assertNotNull(r);

		
		ArrayList al=(ArrayList)r.object;
		Result r0=(Result)al.get(0);
		assertEquals(r0.object,"a");
	}
}
