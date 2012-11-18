package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import qft.FockState;

import qft.MatrixElement;

public class MatrixElementTest {

    @Test
    public void testCalc() {
        
        //Tests the matrix element value calculation for given bra and ket Fock states
        
        FockState bra = new FockState(4, 3, 0.7, 1.3);
        FockState ket = new FockState(4, 3, 0.7, 1.3);
        MatrixElement tester1 = new MatrixElement(4, 3, 0.7, 1.3, 4.0);
        double epsilon = 0.0000001;
        
        bra.setAsVacuum();
        ket.setAsVacuum();
        bra.setCoeff(1, 1);
        bra.setCoeff(3, 1);
        ket.setCoeff(1, 2);
        ket.setCoeff(2, 1);
        
        tester1.calcElement(bra, ket);
        assertEquals(tester1.getValue(), 0.07020153423, epsilon);
        
    }

}
