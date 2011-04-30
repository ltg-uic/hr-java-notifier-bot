/*
 * Created Apr 30, 2011
 */
package ltg.ps.phenomena.helioroom_notifier.commands;

import org.dom4j.Element;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.phenomena.helioroom_notifier.HelioroomNotifier;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class UpdateCorrectionFactor extends PhenomenaCommand {
	
	private int cf = 0;

	/**
	 * @param target
	 */
	public UpdateCorrectionFactor(Phenomena target) {
		super(target);
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	public void execute() {
		((HelioroomNotifier) target).setCorrectionFactor(cf);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	public void parse(Element xml) {
		cf = Integer.valueOf(xml.elementTextTrim("correctionFactor"));
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#toXML()
	 */
	public String toXML() {
		return null;
	}
	
	
	public int getCorrectionFactor() {
		return cf;
	}

}
