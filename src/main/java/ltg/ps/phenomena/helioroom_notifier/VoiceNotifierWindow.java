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
public class VoiceNotifierWindow extends PhenomenaWindow {

	private List<Notification> notifications = null;
	private boolean isActive = false;

	/**
	 * @param windowName
	 */
	public VoiceNotifierWindow(String windowName) {
		super(windowName);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.PhenomenaWindow#toXML()
	 */
	@Override
	public String toXML() {
		if (!isActive || notifications == null)
			return "";
		String s = "";
		String no = "";
		for (Notification n : notifications) 
			no+= n.toXML();
		if (!"".equals(no))
			s = "<notifications>" + no +  "</notifications>";
		return s;
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.PhenomenaWindow#update(ltg.ps.api.phenomena.Phenomena)
	 */
	@Override
	public void update(Phenomena p) {
		notifications = ((HelioroomNotifier)p).getNotifications();
		isActive = ((HelioroomNotifier)p).isEnableVoice();
	}

}
