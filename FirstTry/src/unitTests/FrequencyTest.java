package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import qft.Frequency;

public class FrequencyTest {

	@Test
	public void testConstructAndGet() {
	    
	    //Tests constructor and get
		
		Frequency tester1 = new Frequency(4, 0.15, 204, 0.7);
		double value = tester1.getValue();
		assertEquals(value, 1.078764327, 0.00000001);
		
	}
	
	@Test
	public void testSetMode() {
	    
	    //Tests set
	    
	    Frequency tester2 = new Frequency(4, 0.17, 209, 0.4);
	    tester2.setMode(73);
	    double value = tester2.getValue();
	    assertEquals(value, 10.47799822, 0.00000001);
	    
	}

}
