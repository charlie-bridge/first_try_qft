package utilities;

public class Complex {
	
	public double real;
	public double comp;
	
	public Complex(double real_val, double comp_val) {
		real = real_val;
		comp = comp_val;
	}
	
	public double realpart() {
		return real;
	}
	
	public double complexpart() {
		return comp;
	}
	
	public void setreal(double input_real) {
		real = input_real;
	}
	
	public void setcomp(double input_comp) {
		comp = input_comp;
	}
	
	public void add(Complex addor) {
		real += addor.real;
		comp += addor.comp;
	}
	
	public void subtract(Complex subtractor) {
		real -= subtractor.real;
		comp -= subtractor.comp;
	}
	
	public double modulus() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(comp, 2));
	}
	
}
