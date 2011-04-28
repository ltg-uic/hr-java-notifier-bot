/*
 * Created Apr 28, 2011
 */
package ltg.ps.phenomena.support;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import ltg.ps.phenomena.helioroom_notifier.HelioroomNotifier;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class HelioroomNotifierPersistence extends Persistence {

	private HelioroomNotifier p = null;

	/**
	 * @param fileName
	 */
	public HelioroomNotifierPersistence(String fileName) {
		super(fileName);
	}
	
	
	public HelioroomNotifierPersistence(HelioroomNotifier p) {
		super(p.getInstanceName());
		this.p  = p;
	}

	/* (non-Javadoc)
	 * @see ltg.ps.phenomena.support.Persistence#restore()
	 */
	@Override
	public void restore() {
		readFile();
		Element config = doc.getRootElement().element("config");
		Element wins = doc.getRootElement().element("windows");
		p.configure(config.asXML());
		if(wins!=null) {
			p.configureWindows(wins.asXML());	
		}

	}

	/* (non-Javadoc)
	 * @see ltg.ps.phenomena.support.Persistence#save()
	 */
	@Override
	public void save() {
		try {
			doc = DocumentHelper.parseText(p.toXML());
			writeFile();
		} catch (DocumentException e) {
			log.info("Impossible to save Helioroom.");
		}
	}

}
