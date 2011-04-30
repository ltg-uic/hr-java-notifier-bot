/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

import java.util.List;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class TextNotifierWindow extends PhenomenaWindow {
	
	private List<Notification> notifications = null;

	/**
	 * @param windowName
	 */
	public TextNotifierWindow(String windowName) {
		super(windowName);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.PhenomenaWindow#toXML()
	 */
	@Override
	public String toXML() {
		if (notifications == null)
			return "";
		String s = "<notifications>";
		for (Notification n : notifications) 
			s+= n.toXML();
		s += "</notifications>";
		// HUMAN READABLE VERSION
//		s = "";
//		for (Notification n : notifications) 
//			s+= n.toString();
		// END HUMAN READABLE
		return s;
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.PhenomenaWindow#update(ltg.ps.api.phenomena.Phenomena)
	 */
	@Override
	public void update(Phenomena p) {
		notifications = ((HelioroomNotifier)p).getNotifications();
		System.err.println(toXML());
	}

}
