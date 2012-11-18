package qft;

public class InteractionPartPhi3 {
    
    //Class to evaluate the value of one of the eight parts of the Phi^3 interaction
    
    private double _value;
    private FockState _bra;
    private FockState _ket;
    private double _epsilon;
    private double _mass;
    private int _systemSize;
    private int _maxParticles;
    
    public InteractionPartPhi3(int systemSize, int maxParticles, double epsilon, double mass) {
        
        //Constructor for the Interaction Part
        
        _systemSize = systemSize;
        _epsilon = epsilon;
        _mass = mass;
        _maxParticles = maxParticles;
        _value = 0;
        _bra = new FockState(_systemSize, _maxParticles, _epsilon, _mass);
        _ket = new FockState(_systemSize, _maxParticles, _epsilon, _mass);
        
    }
    
    public void calcPart(int p, int q, boolean isPDagger, boolean isQDagger, boolean is3rdDagger, FockState bra, FockState ket) {
    
        //Calculates the interaction part
        
        _value = 1.0;
        _bra.makeSameAs(bra);
        _ket.makeSameAs(ket);
        double energyFactor = (1/Math.sqrt(8));
        Frequency frequency = new Frequency(0, _epsilon, _systemSize, _mass);
    
        //Find the third momentum mode depending on which of the 8 parts it is we are dealing with
        //Looks horrible but is really just tedious, see page 18 of lab-book
    
        int r = -1;
        if (isPDagger == true) {
            if(isQDagger == true) {
                if(is3rdDagger == true) {
                    if((p+q) >= _systemSize) {
                        r = ((2*_systemSize) - p - q);
                    }
                    else {
                        r = (_systemSize - p - q);
                    }
                }
                else {
                    if((p+q) >= _systemSize) {
                        r = (p + q - _systemSize);
                    }
                    else {
                        r = (p + q);
                    }
                }
            }
            else {
                if(is3rdDagger == true) {
                    if((q-p) < 0) {
                        r = (_systemSize + q - p);
                    }
                    else {
                        r = (q - p);
                    }
                }
                else {
                    if((p-q) < 0) {
                        r = (_systemSize + p - q);
                    }
                    else {
                        r = (p - q);
                    }
                }
            }
        }
        else {
            if(isQDagger == true) {
                if(is3rdDagger == true) {
                    if((p-q) < 0) {
                        r = (_systemSize + p - q);
                    }
                    else {
                        r = (p - q);
                    }
                }
                else {
                    if((q-p) < 0) {
                        r = (_systemSize + q - p);
                    }
                    else {
                        r = (q - p);
                    }
                }
            }
            else {
                if(is3rdDagger == true) {
                    if((p+q) >= _systemSize) {
                        r = (p + q - _systemSize);
                    }
                    else {
                        r = (p + q);
                    }
                }
                else {
                    if((p+q) >= _systemSize) {
                        r = ((2*_systemSize) - p - q);
                    }
                    else {
                        r = (_systemSize - p - q);
                    }
                }
            }
        }
    
        //Determine if the third operator will destroy the state. If not, apply it and calculate the pre-factor
    
        if(is3rdDagger == true) {
            _value = (_value * Math.sqrt(_ket.getCoeff(r) + 1.0)); 
            _ket.applyCreation(r);
        }
        else {
            if(_ket.willAnnihilDestroy(r) == false) {
                _value = (_value * Math.sqrt(_ket.getCoeff(r)));
                _ket.applyAnnihil(r);
            }
            else {
                _value = 0.0;
            }
        }
    
        //Determine if the second operator will destroy the state (unless already destroyed). If not, apply it
        //and calculate the pre-factor
    
        if(_value != 0) {
            if(isQDagger == true) {
                _value = (_value * Math.sqrt(_ket.getCoeff(q) + 1.0)); 
                _ket.applyCreation(q);
            }
            else {
                if(_ket.willAnnihilDestroy(q) == false) {
                    _value = (_value * Math.sqrt(_ket.getCoeff(q)));
                    _ket.applyAnnihil(q);
                }
                else {
                    _value = 0.0;
                }
            }
        }
    
        //Determine if the first operator will destroy the state (unless already destroyed). If not, apply it
        //and calculate the pre-factor
    
        if(_value != 0) {
            if(isPDagger == true) {
                _value = (_value * Math.sqrt(_ket.getCoeff(p) + 1.0)); 
                _ket.applyCreation(p);
            }
            else {
                if(_ket.willAnnihilDestroy(p) == false) {
                    _value = (_value * Math.sqrt(_ket.getCoeff(p)));
                    _ket.applyAnnihil(p);
                }
                else {
                    _value = 0.0;
                }
            }
        }
        
        //If the value is not zero, check to see if the final ket is equal to the final bra
        if((_value !=0) &&
           (_bra.isStateSame(_ket) == false)) {
            _value = 0.0;
        }
    
        //If the value is not zero, multiply it by the appropriate factor involving the frequencies
    
        if(_value !=0) {
            frequency.setMode(r);
            energyFactor = (energyFactor * (1/Math.sqrt(frequency.getValue())));
            frequency.setMode(q);
            energyFactor = (energyFactor * (1/Math.sqrt(frequency.getValue())));
            frequency.setMode(p);
            energyFactor = (energyFactor * (1/Math.sqrt(frequency.getValue())));
            _value = (_value * energyFactor);
        }
        
        //Reset the Fock states
        
        _bra.setAsVacuum();
        _ket.setAsVacuum();
    
    }

    public double getValue() {
    
        //Gets the value
    
        return _value;
    
    }

}
