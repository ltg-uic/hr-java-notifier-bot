/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.support.model;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class Degree {
	
	// Always between 0 and 359
	private float value = -1;
	
	
	public Degree(float value) {
		this.value = normalize(value);
	}
	
	
	private float normalize(float v) {
		// Normalize it
		v = v % 360;
		// Change sign if necessary
		if(v<0)
			return 360+v;
		return v;
	}
	
	
	public Degree add(Degree v) {
		return new Degree(value + v.value);
	}
	
	
	/**
	 * Subtracts the value of the parameter from the value of this degree.
	 */
	public Degree sub(Degree v) {
		return new Degree(value - v.value);
	}
	
	
	public float getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}


}
