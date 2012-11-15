package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import utilities.Complex;

public class ComplexTest {

	@Test
	public void testSetUpAndGet() {
		
		Complex tester1 = new Complex(1.0, 2.0);
		double epsilon = 0.0000001;
		assertEquals(tester1.realpart(), 1.0, epsilon);
		assertEquals(tester1.complexpart(), 2.0, epsilon);
		
	}

	@Test
	public void testReset() {
		
		Complex tester2 = new Complex(3.0, 4.0);
		tester2.setreal(5.0);
		tester2.setcomp(6.0);
		double epsilon = 0.0000001;
		assertEquals(tester2.realpart(), 5.0, epsilon);
		assertEquals(tester2.complexpart(), 6.0, epsilon);
		
	}
	
	@Test
	public void testAddAndSubtract() {
		
		Complex tester3 = new Complex(3.5, 7.2);
		Complex tester4 = new Complex(2.9, 8.6);
		double epsilon = 0.0000001;
		tester3.subtract(tester4);
		assertEquals(tester3.realpart(), 0.6, epsilon);
		assertEquals(tester3.complexpart(), -1.4, epsilon);
		tester3.add(tester4);
		assertEquals(tester3.realpart(), 3.5, epsilon);
		assertEquals(tester3.complexpart(), 7.2, epsilon);
		
	}

	@Test
	public void testModulus() {
		
		Complex tester5 = new Complex(3.0, 4.0);
		assert(tester5.modulus() == 5.0);
		
	}
	
}
