package mikera.parser.test;

import junit.framework.TestCase;
import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.ResultList;
import mikera.parser.primitives.*;

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
	
	public void testAmbiguousSequence() {
		Parser p=new Sequence(new Whitespace(),new Char("x"),new Whitespace(), new End());
		assertEquals(1,p.parseCount("   x "));
		Result fp=p.parseFirst(" x ");
		assertEquals("x",fp.getSubResult(1).object);
		
		p=new Sequence(new Whitespace(),new Whitespace(),new End());
		assertEquals(2,p.parseCount("   "));
	}
	
	
	public void testEndSequence() {
		Parser q=new Sequence(new Whitespace(), new End());
		assertEquals(1,q.parseCount("        "));
		assertEquals(3,q.parseFirst("   ").length);
	}

	public void testRepeatSimple() {
		Parser pa=new Sequence(new Repeat(new Match("a")),new End());
		assertNull(pa.parse(""));
		assertNull(pa.parse("b"));
		assertNotNull(pa.parse("a"));
		assertEquals(1,pa.parse("a").length);		
		assertNotNull(pa.parse("aa"));
		assertNotNull(pa.parse("aaaaaaaa"));
	}
		
	public void testRepeatMany() {
		Parser pa=new Repeat(new Match("a"));
		ResultList rl=pa.parseList("aaaa",0);
		assertEquals(4,rl.first().length);
		assertEquals(4,rl.count());

	}
	
	public void testRepeatCombinations() {
		Parser pa=new Repeat(new Choice(new Match("a"),new Match("aa")));
		ResultList rl=pa.parseList("aaaa",0);
		assertEquals(11,rl.count());
	}
	
	public void testRepeatLimit() {
		Parser pa=new Repeat(new Match("a"),3,5);
		ResultList rl=pa.parseList("aaaaaaaaaa",0);
		assertEquals(5,rl.first().length);
		assertEquals(3,rl.count());
	}
	
	public void testSubsequence() {
		String s=new String("abcdefgh");
		CharSequence cs=s.subSequence(3, 7);
		assertEquals("defg",cs.toString());
	}
	
	public void testRepeatRegex() {
		String s=new String("ababab");
		Parser p=new Repeat(new Regex(".b"));
		assertEquals(3,p.parseCount(s));
		assertEquals(6,p.parseFirst(s).length);
	}
	
	public void testSequencedRegex() {
		String s=new String("adabdb");
		Parser p=new Sequence(new Regex("a.a"),new Regex("b.b"));
		assertEquals(1,p.parseCount(s));
		assertEquals(6,p.parseFirst(s).length);
	}
	
	
	public void testRepeat() {
		String s=new String("ababababa");
		
		Parser p=new Repeat(new Match("ab"));
		
		ResultList rl=p.parseList(s,0);
		assertEquals(4,rl.count());
		assertEquals(8,rl.first().length);
		
		Result r=rl.first();
		assertTrue(r!=null);
		assertTrue(r.object instanceof ArrayList);
		
		Parser q=new Sequence(new Repeat(new Match("ab"),1,4), new End());
		assertNull(q.parse("",0));
		assertNotNull(q.parse("ab",0));
		assertNotNull(q.parse("abababab",0));
		assertNull(q.parse("ababababab",0));
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
