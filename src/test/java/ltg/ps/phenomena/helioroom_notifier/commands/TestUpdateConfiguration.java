package ltg.ps.phenomena.helioroom_notifier.commands;

import static org.junit.Assert.*;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;

public class TestUpdateConfiguration {
	
	private String cmd = "<updateConfiguration><howManyPlanetsFromTheOutside>9</howManyPlanetsFromTheOutside><howManySecondsInAdvance>30</howManySecondsInAdvance><correctionFactor>6</correctionFactor><enableText>true</enableText><enableVoice>false</enableVoice></updateConfiguration>";   

	@Test
	public final void testParse() {
		UpdateConfiguration uc = new UpdateConfiguration(null);
		Document xml = null;
		try {
			xml = DocumentHelper.parseText(cmd);
		} catch (DocumentException e) {
			System.err.println("Error parsing...");
		}
		uc.parse(xml.getRootElement());
		assertEquals(9, uc.getHowManyPlanetsFromTheOutside());
		assertEquals(30, uc.getHowManySecondsInAdvance());
		assertEquals(6, uc.getCorrectionFactor());
		assertEquals(true, uc.isEnableText());
		assertEquals(false, uc.isEnableVoice());
	}

}
