package mikera.parser.test;

import mikera.parser.Parser;
import mikera.parser.Result;
import mikera.parser.primitives.*;
import junit.framework.TestCase;

public class PrimitiveTest extends TestCase {
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

	
	public void testWhiteSpace() {
		Parser p=new Whitespace();
		
		assertTrue(p.parse("",0)==null);
		assertTrue(p.parse(" ",0)!=null);
		assertEquals(2,p.parseList("  ").count());
		assertEquals(5,p.parseList("     ").first().length);
		assertEquals(3,p.parseList(" \t ").count());
		
		assertTrue(p.parse("a a",0)==null);
		assertTrue(p.parse("a a",1)!=null);
	}
	
	public void testChar() {
		Parser p=new Char("x");
		
		assertTrue(p.parse("",0)==null);
		assertTrue(p.parse(" ",0)==null);
		assertTrue(p.parse("x",0)!=null);
		assertEquals(1,p.parseList("x",0).count());
		
		
	}
	
	public void testChoice() {
		Parser p=new Choice(new Match("a"),new Match("b"));
		
		assertNotNull(p.parse("axxx"));
		assertNotNull(p.parse("bxxx"));
		assertNull(p.parse("cxxx"));
		
		Parser q=new Choice(new Match("a"),new Match("ab"));
		
		assertEquals(2,q.parseCount("abbbb"));
		assertEquals(1,q.parseFirst("abbbb").length);
	}
	
	public void testRepeat() {
		Parser p=new Repeat(new Char("x"));
		
		assertEquals(4,p.parseCount("xxxx"));
		assertEquals(4,p.parseFirst("xxxx").length);
	}
	
	public void testRepeatWithLimits() {
		Parser p=new Repeat(new Char("x"),0,4);
		
		assertEquals(1,p.parseCount(""));
		assertEquals(4,p.parseFirst("xxxxxxxxxxx").length);
		assertEquals(5,p.parseCount("xxxxxxxxxxx"));
	}
	
	public void testSequence() {
		Parser p=new Sequence(new Char("a"),new Char("b"));
		
		assertEquals(1,p.parseCount("ab"));
	}
	
	public void testRegex() {
		Parser p=new Regex("ab");
		assertEquals(1,p.parseCount("abbbb"));
		assertEquals(2,p.parseFirst("abbbb").length);
		assertEquals(0,p.parseCount("xabxxx"));
		
		Parser q=new Regex(".+b");
		assertEquals(1,q.parseCount("xxbxb"));
		assertEquals(5,q.parseFirst("xxbxb").length);
		assertEquals("bbbbb",q.parseFirst("bbbbb").object);
	}
	
	public void testMaybe() {
		Parser whitespace=new Whitespace();
		Parser maybespace=new Maybe(whitespace);
		
		assertEquals(1,maybespace.parseCount("a"));
		assertEquals(2,maybespace.parseCount(" a"));
		assertEquals(3,maybespace.parseCount("  a"));
		assertEquals(1,maybespace.parseCount(""));
	}
	

}
