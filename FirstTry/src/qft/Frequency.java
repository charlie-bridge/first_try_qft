package qft;

public class Frequency {
	
	private double _value = 0;
	private final int _systemSize;
	private final double _epsilon;
	private final double _mass;
	
	public Frequency(int p, double epsilon, int systemSize, double mass) {
		
		//Constructor that calculates the frequency of the pth momentum mode
		
		_systemSize = systemSize;
		_epsilon = epsilon;
		_mass = mass;
		_value = Math.sqrt((4/(Math.pow(_epsilon, 2))) * Math.pow(Math.sin((Math.PI*(double)p)/((double)_systemSize)), 2) + Math.pow(_mass, 2));
		
	}

	public void setMode(int q) {
		 
		//Changes the value of the frequency to the qth mode
		
		_value = Math.sqrt((4/(Math.pow(_epsilon, 2))) * Math.pow(Math.sin((Math.PI*(double)q)/((double)_systemSize)), 2) + Math.pow(_mass, 2));
		
	}
	
	public double getValue() {
		
		//Gets the frequency value
		
		return _value;
		
	}
	

}
