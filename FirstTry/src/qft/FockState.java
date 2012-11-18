package qft;

public class FockState {
    
    //Class for a member of the infinite Fock space

	private int _systemSize;
	private int _maxParticles;
	private final double _epsilon;
	private final double _mass;
	private int[] _coeffs;
	
	public FockState(int systemSize, int maxParticles, double epsilon, double mass) {
		
		//Initialise Fock_state
		//NOTE: What happens if Fock_State instance is created without constructor?
		
		_systemSize = systemSize;
		_maxParticles = maxParticles;
		_epsilon = epsilon;
		_mass = mass;
		_coeffs = new int[_systemSize];
		
	}

	public int getCoeff(int x) {
		
		//Gets x component of the state
		//NOTE: deal with x>(system_size - 1) differently? -1 used as error val
		
		if(x<_systemSize) {
			return _coeffs[x];
		}
		else {
			return -1;
		}
		
	}

	public void setCoeff(int y, int z) {
		
	    //Sets y component of the state to z
	    //NOTE: what happens if we increase the total number of particles beyond max?
		
		if( (y<_systemSize) && (z>=0)) {
			_coeffs[y] = z;
		}
		
	}
	
	public void setMaxParticles(int n) {
		
		//Sets the maximum number of particles allowed in a Fock State to n
		
		_maxParticles = n;
		
	}
	
	public int getMaxParticles() {
	    
	    //Gets the maximum number of allowed particles
	    
	    return _maxParticles;
	    
	}
	
	public int getSystemSize() {
	    
	    //Gets the system size
	    
	    return _systemSize;
	    
	}
	
	public double getEpsilon() {
	    
	    //Gets the value for epsilon
	    
	    return _epsilon;
	    
	}
	
	public double getMass() {
	    
	    //Gets the value for the mass
	    
	    return _mass;
	    
	}
	
	public void setAsVacuum() {
		
		//Sets all the coefficients to zero - the vacuum state
		
		for(int i=0; i<_systemSize; i++) {
			_coeffs[i] = 0;
		}
		
	}

	public boolean isStateSame(FockState passedState) {
		
		//Checks if the passed Fock_State is the same as this one
		
		boolean result = true;
		
		if( (_systemSize == passedState.getSystemSize()) &&
		    (_epsilon == passedState.getEpsilon()) &&
		    (_mass == passedState.getMass()) &&
		    (_maxParticles == passedState.getMaxParticles()) ) {
			for(int j=0; j<_systemSize; j++) {
				if(_coeffs[j] != passedState.getCoeff(j)) {
					result = false;
					j = _systemSize;
				}
			}
		}
		else {
			result = false;
		}
		
		return result;
		
	}
	
	public boolean willAnnihilDestroy(int q) {
		
		//Checks to see if an annihilation operator for the 
		//qth momentum will destroy the state
		//NOTE: Deal with q>system_size differently - returns false always atm?
		
		if(q<_systemSize) {
			if(_coeffs[q] == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	
	}
	
	public void applyCreation(int p) {
		
		//Applies a creation operator for the pth momentum mode
		//NOTE: deal with p>(system_size - 1) differently?
	    //NOTE: what if we push beyond maxParticles?
		
		if(p<_systemSize) {
			_coeffs[p]++;
		}
		
	}
	
	public void applyAnnihil(int r) {
		
		//Applies an annihilation operator for the qth momentum mode
		//NOTE: deal with r>(system_size - 1) differently?
	    //NOTE: what happens if applied to a 0? - we have a check method but still,
	    //should be more robust
		
		if(r<_systemSize) {
			_coeffs[r]--;
		}
				
	}

	public void incrementState() {
		
		// Increments the Fock State according to the rules laid out on p17
		
		//First, find the last and second last non-zero numbers in the state
		
		int lastNumPos = 0;
		int lastNumVal = _coeffs[0];
		int scndLastNumPos = 0;
		int scndLastNumVal = 0;
		
		for(int k=1; k<_systemSize; k++){
			
			if(_coeffs[k] != 0) {
				scndLastNumPos = lastNumPos;
				scndLastNumVal = lastNumVal;
				lastNumPos = k;
				lastNumVal = _coeffs[k];
			}
			
		}
		
		if(lastNumPos != (_systemSize - 1)) {
			
			//If the last number is not at the end of the state, take one
			//from it and place that one just ahead of it
			
			_coeffs[lastNumPos] = (lastNumVal - 1);
			_coeffs[lastNumPos + 1] = 1;
			
		}
		else {
			
			if(scndLastNumVal != 0) {
				
				//If the last number is at the end of the state (and we are not
				//in the special case where that is the only number) then take
				//one off the 2nd last number, add it to the last and place it
				//just ahead of the 2nd last
				
				_coeffs[scndLastNumPos] = (scndLastNumVal - 1);
				_coeffs[lastNumPos] = 0;
				_coeffs[scndLastNumPos + 1] = (lastNumVal + 1);
				
			}
			else if(lastNumVal < _maxParticles) {
				
				//In this special case, move to the start of the next set of
				//state which includes one more particle
			    //NOTE: incrementing beyond that max number of particles does nothing
			    //can use the compare state as a check for reaching the end?
				_coeffs[lastNumPos] = 0;
				_coeffs[0] = lastNumVal + 1;
				
			}
			
		}
		
	}
	
	public double energy() {
		
		//Returns the energy of the Fock State (i.e it's eigenvalue as an
		//eigenstate of the free Hamiltonian
		
		Frequency hopFreq = new Frequency(0, _epsilon, _systemSize, _mass);
		double energyTotal = (_coeffs[0] * hopFreq.getValue());
		
		for(int h=1; h<_systemSize; h++) {
			hopFreq.setMode(h);
			energyTotal+= (_coeffs[h] * hopFreq.getValue());
		}
		
		return energyTotal;
		
	}
	
	public void makeSameAs(FockState passedState) {
	    
	    //Makes this Fock state the same as the passed one (if they represent systems of the same
	    //size, mass etc.)
	    
	    if((passedState.getSystemSize() == _systemSize) &&
	       (passedState.getMaxParticles() == _maxParticles) &&
	       (passedState.getEpsilon() == _epsilon) &&
	       (passedState.getMass() == _mass)) {
	        for(int k=0; k<_systemSize; k++) {
	            _coeffs[k] = passedState.getCoeff(k);
	        }
	    }
	    
	}
	
}
