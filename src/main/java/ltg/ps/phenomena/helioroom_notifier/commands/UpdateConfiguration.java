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
public class UpdateConfiguration extends PhenomenaCommand {
	
	private int howManyPlanetsFromTheOutside = -1;
	private int howManySecondsInAdvance = -1;
	private int correctionFactor = 0;
	private boolean enableText = false;
	private boolean enableVoice = false;

	/**
	 * @param target
	 */
	public UpdateConfiguration(Phenomena target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	public void execute() {
		((HelioroomNotifier) target).setHowManyPlanetsFromTheOutside(howManyPlanetsFromTheOutside);
		((HelioroomNotifier) target).setHowManySecondsInAdvance(howManySecondsInAdvance);
		((HelioroomNotifier) target).setCorrectionFactor(correctionFactor);
		((HelioroomNotifier) target).setEnableText(enableText);
		((HelioroomNotifier) target).setEnableVoice(enableVoice);
	}

	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	public void parse(Element xml) {
		howManyPlanetsFromTheOutside = Integer.valueOf(xml.elementTextTrim("howManyPlanetsFromTheOutside"));
		howManySecondsInAdvance = Integer.valueOf(xml.elementTextTrim("howManySecondsInAdvance"));
		correctionFactor = Integer.valueOf(xml.elementTextTrim("correctionFactor"));
		enableText = Boolean.valueOf(xml.elementTextTrim("enableText"));
		enableVoice = Boolean.valueOf(xml.elementTextTrim("enableVoice"));
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#toXML()
	 */
	public String toXML() {
		return null;
	}

	public int getHowManyPlanetsFromTheOutside() {
		return howManyPlanetsFromTheOutside;
	}

	public int getHowManySecondsInAdvance() {
		return howManySecondsInAdvance;
	}

	public int getCorrectionFactor() {
		return correctionFactor;
	}

	public boolean isEnableText() {
		return enableText;
	}

	public boolean isEnableVoice() {
		return enableVoice;
	}

	public void setHowManyPlanetsFromTheOutside(int howManyPlanetsFromTheOutside) {
		this.howManyPlanetsFromTheOutside = howManyPlanetsFromTheOutside;
	}

	public void setHowManySecondsInAdvance(int howManySecondsInAdvance) {
		this.howManySecondsInAdvance = howManySecondsInAdvance;
	}

	public void setCorrectionFactor(int correctionFactor) {
		this.correctionFactor = correctionFactor;
	}

	public void setEnableText(boolean enableText) {
		this.enableText = enableText;
	}

	public void setEnableVoice(boolean enableVoice) {
		this.enableVoice = enableVoice;
	}
	
	
	

}
