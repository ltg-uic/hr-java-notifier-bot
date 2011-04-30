/*
 * Created Jan 18, 2011
 */
package ltg.ps.phenomena.support;

import java.io.File;

import ltg.ps.phenomena.helioroom_notifier.HelioroomNotifier;
import ltg.ps.phenomena.helioroom_notifier.commands.UpdatePhenomena;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class NotifierLoader {
	
	private HelioroomNotifier hn = new HelioroomNotifier("hr_dev_notification");
	private UpdatePhenomena up = new UpdatePhenomena(hn);
	private File xml = new File("src/test/resources/phenUpdate.xml");

	/**
	 * TODO Description
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		NotifierLoader nl = new NotifierLoader();
		nl.start();
	}

	
	private void start() {
		// Load the notifier parameters
		hn.configureWindows("<windows><win id=\"hr_dev_w_notifier_text\" type=\"text\" />" +
				"<win id=\"hr_dev_w_notifier_voice\" type=\"voice\" />" +
				"<win id=\"hr_dev_w_notifier_cp\" type=\"control\" /></windows>");
		hn.configure("<config><howManyPlanetsFromTheOutside>9</howManyPlanetsFromTheOutside>" +
				"<howManySecondsInAdvance>30</howManySecondsInAdvance>" +
				"<enableText>true</enableText><enableVoice>false</enableVoice></config>");
		
		// Load helioroom data
		Document doc = null;
		// Read file
		SAXReader r = new SAXReader();
		try {
			doc = r.read(xml);
		} catch (DocumentException e) {
			System.err.println("Impossible to parse file");
		}
		up.parse(doc.getRootElement());
		up.execute();
		// Start the thread
		hn.start();
	}

}
