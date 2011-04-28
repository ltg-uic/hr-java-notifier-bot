/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import ltg.ps.api.phenomena.ActivePhenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.support.HelioroomNotifierPersistence;
import ltg.ps.phenomena.support.model.Helioroom;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class HelioroomNotifier extends ActivePhenomena {

	// Phenomena data
	private Helioroom observedPhenomena = null;
	
	// Notifier data
	private int refreshRate = 4;
	private int howManyPlanetsFromTheOutside = -1;
	private int howManySecondsInAdvance = -1;
	private boolean enableText = false;
	private boolean enableVoice = false;
	
	
	// Components
	private HelioroomNotifierPersistence db = null;


	/**
	 * @param instanceId
	 */
	public HelioroomNotifier(String instanceId) {
		super(instanceId);
		db = new HelioroomNotifierPersistence(this);
	}


	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.ActivePhenomena#configure(java.lang.String)
	 */
	@Override
	public void configure(String configXML) {
		// reset
		howManyPlanetsFromTheOutside = -1;
		howManySecondsInAdvance = -1;
		enableText = false;
		enableVoice = false;
		// load state from XML
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(configXML);
			Element el = doc.getRootElement();
			// Phenomena properties
			howManyPlanetsFromTheOutside = Integer.valueOf(el.elementTextTrim("howManyPlanetsFromTheOutside"));
			howManySecondsInAdvance = Integer.valueOf(el.elementTextTrim("howManySecondsInAdvance"));
			enableText = Boolean.valueOf(el.elementTextTrim("enableText"));
			enableVoice = Boolean.valueOf(el.elementTextTrim("enableVoice"));
			db.save();
		} catch (DocumentException e) {
			log.info("Impossible to configure helioroom");
		}
	}


	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.Phenomena#configureWindows(java.lang.String)
	 */
	@Override
	public void configureWindows(String windowsXML) {
		// reset the windows
		phenWindows.clear();
		// create new windows
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(windowsXML);
			@SuppressWarnings("unchecked")
			List<Element>windows = doc.getRootElement().elements();
			for(Element e: windows) {
				if(e.attributeValue("type").equals("text")) 
					phenWindows.add(new TextNotifierWindow(e.attributeValue("id")));
				if(e.attributeValue("type").equals("voice"))
					phenWindows.add(new VoiceNotifierWindow(e.attributeValue("id")));
				if(e.attributeValue("type").equals("control"))
					phenWindows.add(new ConfigurationWindow(e.attributeValue("id")));
			}
			db.save();
		} catch (DocumentException e) {
			log.info("Impossible to configure helioroom windows");
		}
	}


	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.ActivePhenomena#restore()
	 */
	@Override
	public void restore() {
		db.restore();
	}



	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.Phenomena#cleanup()
	 */
	@Override
	public void cleanup() {
		db.cleanup();
	}
	
	
	
	/* (non-Javadoc)
	 * @see ltg.ps.api.phenomena.ActivePhenomena#update()
	 */
	@Override
	protected void update() throws InterruptedException {
		// check that all parameters are initialized
		if(observedPhenomena == null || howManyPlanetsFromTheOutside == -1 || howManySecondsInAdvance == -1)
			return;
		// compute the position of planets in degrees
		
		// compute how far they are from the beginning of each screen
		// write notifications somewhere
		// notifyObservers 
		//// Observers will fetch notifications and send them out
	}


	public void setObservedPhenomena(Helioroom h) {
		this.observedPhenomena = h;
		// update refresh rate of the simulation
		int quickestPlanet = observedPhenomena.getPlanets().get(0).getClassOrbitalTime()*60;
		int time = quickestPlanet / (observedPhenomena.getPhenWindows().size() * refreshRate);
		if(time>0)
			this.setSleepTime(time);
	}
	
	
	
	public String toXML() {
		Element root = DocumentHelper.createElement(instanceName);
		// Windows
		Element wins = DocumentHelper.createElement("windows");
		Element e = null;
		for(PhenomenaWindow w: phenWindows) {
			e = DocumentHelper.createElement("win");
			e.addAttribute("id", w.getWindowId());
			if(w instanceof TextNotifierWindow) {
				e.addAttribute("type", "text");
			}
			if(w instanceof VoiceNotifierWindow) {
				e.addAttribute("type", "voice");
			}
			if(w instanceof ConfigurationWindow) {
				e.addAttribute("type", "control");
			}
			wins.add(e);
		}
		root.add(wins);
		// Configuration
		Element conf = DocumentHelper.createElement("config");
		conf.addElement("howManyPlanetsFromTheOutside").addText(String.valueOf(howManyPlanetsFromTheOutside));
		conf.addElement("howManySecondsInAdvance").addText(String.valueOf(howManySecondsInAdvance));
		conf.addElement("enableText").addText(String.valueOf(enableText));
		conf.addElement("enableVoice").addText(String.valueOf(enableVoice));
		root.add(conf);
		// Create document
		return removeXMLDeclaration(DocumentHelper.createDocument(root));
	}
	
	
	
	private String removeXMLDeclaration(Document doc) {
		StringWriter w = new StringWriter();
		OutputFormat f =  OutputFormat.createPrettyPrint();
		f.setSuppressDeclaration(true);
		XMLWriter xw = new XMLWriter(w, f);
		try {
			xw.write(doc);
		} catch (IOException e1) {
			// Unable to print to a string? Really?
		}
		return w.toString();
	}

}
