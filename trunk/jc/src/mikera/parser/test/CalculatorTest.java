package mikera.parser.test;

import junit.framework.TestCase;
import mikera.calculator.*;

public class CalculatorTest extends TestCase {
	public void testCalculate() {
		Calculator c=new Calculator();
		
		assertEquals(4.0,c.value.parse("4").asDouble(),1E-10);
		assertEquals(4.0,c.mulexp.parse("2*2").asDouble(),1E-10);
		assertEquals(4.0,c.term.parse("4").asDouble(),1E-10);
		assertEquals(4.0,c.value.parse("(4)").asDouble(),1E-10);
		
		assertEquals(2.0,c.calculate("1+1"),1E-10);
		assertEquals(4.0,c.calculate("7-3"),1E-10);
		assertEquals(4.0,c.calculate("2*2"),1E-10);

		assertEquals(3.0,c.calculate("1+1+1"),1E-10);

		assertEquals(1.0,c.calculate("2*2/4"),1E-10);
		assertEquals(2.0,c.calculate("2/2*2"),1E-10);
		assertEquals(1.0,c.calculate("1+2+3-2-(2+1)"),1E-10);

		
		assertEquals(18.0,c.calculate("(4-1)*(2+2+1+1)"),1E-10);
		
	}
}
