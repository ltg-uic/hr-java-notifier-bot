/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.helioroom_notifier.commands;

import ltg.ps.api.phenomena.Phenomena;
import ltg.ps.api.phenomena.PhenomenaCommand;
import ltg.ps.phenomena.helioroom_notifier.HelioroomNotifier;
import ltg.ps.phenomena.support.model.Helioroom;

import org.dom4j.Element;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class UpdatePhenomena extends PhenomenaCommand {
	
	private Helioroom h = null;

	/**
	 * @param target
	 */
	public UpdatePhenomena(Phenomena target) {
		super(target);
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#execute()
	 */
	public void execute() {
		((HelioroomNotifier) target).setObservedPhenomena(h);
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#parse(org.dom4j.Element)
	 */
	public void parse(Element xml) {
		Element e = (Element) xml.elements().get(0);
		h = new Helioroom(e.getName());
		h.configureWindows(e.element("windows").asXML());
		h.configure(e.element("config").asXML());
	}

	
	/* (non-Javadoc)
	 * @see ltg.ps.api.Command#toXML()
	 */
	public String toXML() {
		return null;
	}

}
