package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import qft.FockState;

public class FockStateTest {

    @Test
    public void testSetUpAndGet() {
        
        //Test setting and getting coeffs
        
        FockState tester1 = new FockState(41, 5, 3.1, 1.6);
        double epsilon = 0.00000001;
        tester1.setCoeff(13, 3);
        tester1.setCoeff(38, 1);
        tester1.setMaxParticles(7);
        assertEquals(tester1.getCoeff(7), 0, epsilon);
        assertEquals(tester1.getCoeff(13), 3, epsilon);
        assertEquals(tester1.getCoeff(38), 1, epsilon);
        assertEquals(tester1.getMaxParticles(), 7, epsilon);
     
    }

    @Test
    public void testVacuum() {
        
        //Tests setting up a vacuum state
        
        FockState tester2 = new FockState(37, 4, 2.4, 1.1);
        tester2.setAsVacuum();
        
        for(int i=0; i<37; i++) {
            assert(tester2.getCoeff(i) == 0);
        }
        
    }

    @Test
    public void testSameState() {
        
        //Tests the state comparison method
        
        FockState tester3 = new FockState(65, 4, 2.2, 0.5);
        FockState tester4 = new FockState(65, 4, 2.2, 0.5);
        tester3.setAsVacuum();
        tester4.setAsVacuum();
        tester3.setCoeff(53, 3);
        tester4.setCoeff(53, 3);
        assert(tester3.isStateSame(tester4));
        assert(tester4.isStateSame(tester3));
        
    }

    @Test
    public void testOperators() {
        
        //Checks the application of creation and annihilation operators, as well
        //as the method for checking for state annihilation
        
        FockState tester5 = new FockState(50, 3, 1.5, 0.5);
        tester5.setAsVacuum();
        tester5.setCoeff(23, 2);
        tester5.setCoeff(19, 1);
        assert(tester5.willAnnihilDestroy(23) == false);
        assert(tester5.willAnnihilDestroy(17) == true);
        tester5.applyCreation(17);
        tester5.applyCreation(23);
        tester5.applyAnnihil(19);
        assert(tester5.willAnnihilDestroy(17) == false);
        assert(tester5.willAnnihilDestroy(19) == true);
        assertEquals(tester5.getCoeff(23), 3);
        
    }
    
    @Test
    public void testIncrement() {
        
        //Tests the state incrementation method in various cases
        
        FockState tester6 = new FockState(5, 4, 1.5, 0.5);
        
        tester6.setAsVacuum();
        tester6.setCoeff(0, 5);
        tester6.incrementState();
        assertEquals("simple check 1", tester6.getCoeff(0), 4);
        assertEquals("simple check 2", tester6.getCoeff(1), 1);
        tester6.incrementState();
        assertEquals("simple check 3", tester6.getCoeff(0), 4);
        assertEquals("simple check 4", tester6.getCoeff(2), 1);
        
        tester6.setAsVacuum();
        tester6.setCoeff(2, 2);
        tester6.setCoeff(4, 2);
        tester6.incrementState();
        assertEquals("middle check 1", tester6.getCoeff(2), 1);
        assertEquals("middle check 2", tester6.getCoeff(3), 3);
        
        tester6.setAsVacuum();
        tester6.setCoeff(3, 2);
        tester6.setCoeff(4, 1);
        tester6.incrementState();
        assertEquals("end check 1", tester6.getCoeff(3), 1);
        assertEquals("end check 2", tester6.getCoeff(4), 2);
        
        tester6.setAsVacuum();
        tester6.setCoeff(4, 2);
        tester6.incrementState();
        assertEquals("particle increase check 1", tester6.getCoeff(0), 3);
        assertEquals("particle increase check 2", tester6.getCoeff(4), 0);
        
        tester6.setAsVacuum();
        tester6.setCoeff(4, 4);
        tester6.incrementState();
        assertEquals("particle overload check 1", tester6.getCoeff(0), 0);
        assertEquals("particle overload check 2", tester6.getCoeff(4), 4);
        
    }

    @Test
    public void testEnergyCalc() {
        
        //Tests the energy calculation method
        
        FockState tester7 = new FockState(6, 3, 0.55, 2.2);
        double epsilon = 0.0000001;
        tester7.setAsVacuum();
        tester7.setCoeff(1, 1);
        tester7.setCoeff(2, 3);
        tester7.setCoeff(4, 2);
        assertEquals(tester7.energy(), 22.0617339, epsilon);
        
    }
    
}
