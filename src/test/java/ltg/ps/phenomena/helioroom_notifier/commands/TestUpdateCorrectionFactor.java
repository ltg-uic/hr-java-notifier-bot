package ltg.ps.phenomena.helioroom_notifier.commands;

import static org.junit.Assert.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

public class TestUpdateCorrectionFactor {
	
	String cmd = "<updateCorrectionFactor><correctionFactor>8</correctionFactor></updateCorrectionFactor>";
	

	@Test
	public final void testParse() {	
		UpdateCorrectionFactor ucf = new UpdateCorrectionFactor(null);
		Document xml = null;
		try {
			xml = DocumentHelper.parseText(cmd);
		} catch (DocumentException e) {
			System.err.println("Error parsing...");
		}
		ucf.parse(xml.getRootElement());
		assertEquals(8, ucf.getCorrectionFactor());
	}

}
