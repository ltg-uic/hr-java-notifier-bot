/*
 * Created Jan 18, 2011
 */
package ltg.ps.phenomena.support.model;


/**
 * TODO Description
 *
 * @author Gugo
 */
public class HelioroomWindow extends Window {
	
	private String windowName = null;
	
	private int viewAngleBegin = -1;
	private int viewAngleEnd = -1;
	
	
	public HelioroomWindow(String windowName, int viewAngleBegin, int viewAngleEnd) {
		this.windowName = windowName;
		this.viewAngleBegin = viewAngleBegin;
		this.viewAngleEnd = viewAngleEnd;
	}

	
	public String getWindowName() {
		return windowName;
	}

	
	public int getViewAngleBegin() {
		return viewAngleBegin;
	}


	public int getViewAngleEnd() {
		return viewAngleEnd;
	}

}
