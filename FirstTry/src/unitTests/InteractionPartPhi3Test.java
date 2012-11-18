package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import qft.InteractionPartPhi3;

import qft.FockState;

public class InteractionPartPhi3Test {

    @Test
    public void test0Calcs() {
        
        //Test configurations that should return a value of 0:
        //1st, the third operator destroys the state
        //2nd, the second operator destroys the state
        //3rd, the first operator destroys the state
        //4th, the final states are not the same
        
        InteractionPartPhi3 tester1 = new InteractionPartPhi3(5, 4, 1.1, 2.1);
        FockState bra = new FockState(5, 4, 1.1, 2.1);
        FockState ket = new FockState(5, 4, 1.1, 2.1);
        bra.setAsVacuum();
        ket.setAsVacuum();
        double epsilon = 0.0000001;
        
        //1st test:
        ket.setCoeff(2, 2);
        tester1.calcPart(1, 2, false, false, false, bra, ket);
        assertEquals("1st Test", tester1.getValue(), 0.0, epsilon);
        ket.setAsVacuum();
        
        //2nd test:
        ket.setCoeff(3,1);
        tester1.calcPart(1, 2, false, false, true, bra, ket);
        assertEquals("2nd Test", tester1.getValue(), 0.0, epsilon);
        ket.setAsVacuum();
        
        //3rd test:
        ket.setCoeff(4,2);
        ket.setCoeff(2,3);
        tester1.calcPart(1, 2, false, true, true, bra, ket);
        assertEquals("3rd Test", tester1.getValue(), 0.0, epsilon);
        ket.setAsVacuum();
        
        //4th test:
        ket.setCoeff(0, 1);
        ket.setCoeff(2, 3);
        tester1.calcPart(1, 2, false, false, false, bra, ket);
        assertEquals("4th Test", tester1.getValue(), 0.0, epsilon);
     
    }

    @Test
    public void testActualCalcs() {
        
        //Test that the produced interaction factor match the expected value
        
        InteractionPartPhi3 tester2 = new InteractionPartPhi3(6, 4, 1.1, 2.1);
        FockState bra = new FockState(6, 4, 1.1, 2.1);
        FockState ket = new FockState(6, 4, 1.1, 2.1);
        bra.setAsVacuum();
        ket.setAsVacuum();
        double epsilon = 0.0000000001;
        
        ket.setCoeff(1, 3);
        ket.setCoeff(3, 1);
        ket.setCoeff(4, 2);
        bra.setCoeff(1, 1);
        bra.setCoeff(2, 1);
        bra.setCoeff(3, 1);
        bra.setCoeff(4, 2);
        tester2.calcPart(2, 1, true, false, false, bra, ket);
        assertEquals("Actual Calculation", tester2.getValue(), 0.233597354, epsilon);
        
    }

}
