/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.helioroom_notifier;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ltg.ps.api.phenomena.ActivePhenomena;
import ltg.ps.api.phenomena.PhenomenaWindow;
import ltg.ps.phenomena.support.HelioroomNotifierPersistence;
import ltg.ps.phenomena.support.model.Helioroom;
import ltg.ps.phenomena.support.model.HelioroomWindow;
import ltg.ps.phenomena.support.model.Planet;
import ltg.ps.phenomena.support.model.Window;

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
	private List<HelioroomWindow> clientWins = null;
	
	// Notifier data
	private int refreshRate = 6;
	private int howManyPlanetsFromTheOutside = -1;
	private int howManySecondsInAdvance = -1;
	private int correctionFactor = 0;
	private boolean enableText = false;
	private boolean enableVoice = false;
	private List<Notification> notifications = null;
	
	// Components
	private HelioroomNotifierPersistence db = null;


	/**
	 * @param instanceId
	 */
	public HelioroomNotifier(String instanceId) {
		super(instanceId);
		db = new HelioroomNotifierPersistence(this);
		clientWins = new ArrayList<HelioroomWindow>();
		notifications = new ArrayList<Notification>();
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
		// Clear notifications
		notifications.clear();
		// check that all parameters are initialized
		if(observedPhenomena == null || howManyPlanetsFromTheOutside == -1 || howManySecondsInAdvance == -1)
			return;
		long timeDelta = new Date().getTime()/1000 - observedPhenomena.getStartTime();
		// for each planet from the limit-on
		List<Planet> plans = observedPhenomena.getPlanets();
		int pi = plans.size() - howManyPlanetsFromTheOutside;
		if (pi < 0)
			return;
		Planet p = null;
		while (pi < plans.size()) {
			p = plans.get(pi);
			// compute the current position of each planet (in degrees) 
			p.computeCurrentPosition((float)timeDelta);
			// apply the correction factor
			int travelTime = howManySecondsInAdvance + correctionFactor;
			// compute the distance it will cover in the next travelTime
			p.computeDistanceTraveled((float) travelTime);
			// compute if and which window the planet will enter
			// and check it is different from the last one
			if (p.findWindow(this.clientWins)) {
				// if so write a notification
				notifications.add(new Notification(p.getName(), p.getColorName(), 
								p.getWindow().getWindowName(), howManySecondsInAdvance));
			}
			pi++;
		}
	}
	
	
	public List<Notification> getNotifications() {
		return notifications;
	}


	public boolean isEnableText() {
		return enableText;
	}


	public boolean isEnableVoice() {
		return enableVoice;
	}


	public void setObservedPhenomena(Helioroom h) {
		this.observedPhenomena = h;
		//find clients windows
		int wc=0;
		for (Window w: observedPhenomena.getPhenWindows())
			if(w instanceof HelioroomWindow) {
				clientWins.add((HelioroomWindow) w);
				wc++;
			}
		// update refresh rate of the simulation
		int quickestPlanet = observedPhenomena.getPlanets().get(0).getClassOrbitalTime()*60;
		int time = quickestPlanet / (wc * refreshRate);
		if(time > 0) {
			setSleepTime(time);
			log.info("Planet positions will be updated every " + time + " seconds.");
		}
	}
	
	
	
	public void setCorrectionFactor(int cf) {
		this.correctionFactor = cf;
	}


	public void setHowManyPlanetsFromTheOutside(int howManyPlanetsFromTheOutside) {
		this.howManyPlanetsFromTheOutside = howManyPlanetsFromTheOutside;
	}


	public void setHowManySecondsInAdvance(int howManySecondsInAdvance) {
		this.howManySecondsInAdvance = howManySecondsInAdvance;
	}


	public void setEnableText(boolean enableText) {
		this.enableText = enableText;
	}


	public void setEnableVoice(boolean enableVoice) {
		this.enableVoice = enableVoice;
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
	
	
	
	public static String removeXMLDeclaration(Document doc) {
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
