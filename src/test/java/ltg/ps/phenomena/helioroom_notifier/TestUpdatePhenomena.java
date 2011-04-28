/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

import static org.junit.Assert.assertTrue;

import java.io.File;

import ltg.ps.phenomena.helioroom_notifier.commands.UpdatePhenomena;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class TestUpdatePhenomena {
	
	HelioroomNotifier hn = new HelioroomNotifier("hr_dev_notification");
	UpdatePhenomena up = new UpdatePhenomena(hn);
	File xml = new File("src/test/resources/phenUpdate.xml");
	

	/**
	 * Test method for {@link ltg.ps.phenomena.helioroom_notifier.commands.UpdatePhenomena#execute()}.
	 */
	@Test
	public void testExecute() {
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
		assertTrue(true);
	}

}
