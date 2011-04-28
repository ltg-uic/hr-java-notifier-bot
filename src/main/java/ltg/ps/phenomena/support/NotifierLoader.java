/*
 * Created Jan 18, 2011
 */
package ltg.ps.phenomena.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ltg.ps.phenomena.helioroom_notifier.HelioroomNotifier;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class NotifierLoader {

	/**
	 * TODO Description
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// read from input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String conf = null;
		try {
			conf = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conf!=null)
			System.out.println(conf);
		
		// do stuff with the instance
		HelioroomNotifier hn = new HelioroomNotifier("hr_dev_notifier");
		hn.configureWindows(conf);
		hn.configure("<config><howManyPlanetsFromTheOutside>9</howManyPlanetsFromTheOutside><howManySecondsInAdvance>30</howManySecondsInAdvance><enableText>true</enableText><enableVoice>false</enableVoice></config>");
	}

}
