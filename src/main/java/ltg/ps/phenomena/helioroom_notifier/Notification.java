/*
 * Created Apr 30, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
		Element root = DocumentHelper.createElement("notification");
		root.addAttribute("type", "Helioroom");
		root.addElement("planetName").addText(planetName);
		root.addElement("planetColor").addText(planetColor);
		root.addElement("enteringWindow").addText(window);
		root.addElement("inSeconds").addText(secondsInAdvance);
		return HelioroomNotifier.removeXMLDeclaration(DocumentHelper.createDocument(root));
	}
	
	public String toString() {
		return planetName + ", the " + planetColor + " planet, will enter window " 
				+ window + " in approximately " + secondsInAdvance + " seconds.\n";
	}
		

}
