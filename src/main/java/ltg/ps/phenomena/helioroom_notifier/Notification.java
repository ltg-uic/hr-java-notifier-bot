/*
 * Created Apr 30, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class Notification {
	
	private String planetName = null;
	private String planetColor = null;
	private String window = null;
	private String secondsInAdvance = null;
	
	
	public Notification(String planetName, String planetColor, String window, int secondsInAdvance) {
		this.planetName = planetName;
		this.planetColor = planetColor;
		this.window = window;
		this.secondsInAdvance = String.valueOf(secondsInAdvance);
	}

	public String getPlanetName() {
		return planetName;
	}
	
	public String getPlanetColor() {
		return planetColor;
	}
	
	public String getWindow() {
		return window;
	}
	
	public String getSecondsInAdvance() {
		return secondsInAdvance;
	}
	
	
	public String toXML() {
		// "name", ("color" planet), will enter window "window" in approximately "advance" seconds
		return null;
	}
		

}
