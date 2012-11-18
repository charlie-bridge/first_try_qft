package qft;

public class MatrixElement {
    
    //Class that evaluates the size of a matrix element of the interaction part of the Hamiltonian
    
    private double _matrixValue;
    private double _epsilon;
    private double _mass;
    private int _systemSize;
    private int _maxParticles;
    private double _coupling;
    
    public MatrixElement(int systemSize, int maxParticles, double epsilon, double mass, double coupling) {
        
        //Constructor for the Matrix Element
        
        _systemSize = systemSize;
        _maxParticles = maxParticles;
        _epsilon = epsilon;
        _mass = mass;
        _coupling = coupling;
        _matrixValue = -1.0;
        
    }
    
    public void calcElement(FockState bra, FockState ket) {
        
        //Method for calculating the matrix element value given two Fock states
        
        _matrixValue = 0.0;
        InteractionPartPhi3 interaction = new InteractionPartPhi3(_systemSize, _maxParticles, _epsilon, _mass);
        
        for(int p=0; p <_systemSize; p++) {
            for(int q=0; q < _systemSize; q++) {
                
                interaction.calcPart(p, q, true, true, true, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, true, true, false, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, true, false, true, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, false, true, true, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, true, false, false, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, false, true, false, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, false, false, true, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
                interaction.calcPart(p, q, false, false, false, bra, ket);
                _matrixValue = _matrixValue + interaction.getValue();
                
            }
            
        }
            
        //Multiply by the coupling-strength-dependent pre-factor
            
        _matrixValue = (_matrixValue * (_coupling/(6*Math.sqrt(_epsilon * Math.pow(_systemSize, 3)))));
        
    }
    
    public double getValue() {
        
        //Gets the matrix element value
        
        return _matrixValue;
        
    }

}
