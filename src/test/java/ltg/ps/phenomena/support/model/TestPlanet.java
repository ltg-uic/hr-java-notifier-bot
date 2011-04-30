/*
 * Created Apr 30, 2011
 */
package ltg.ps.phenomena.support.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class TestPlanet {
	
	List<HelioroomWindow> wins = null;
	

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Planet#computeCurrentPosition(float)}.
	 */
	@Test
	public final void testComputeCurrentPosition() {
		// Mercury
		Planet m = new Planet("Mercury", "0xFF8B4513", "Brown", 2, 1);
		m.computeCurrentPosition(5689530);
		assertEquals(92.0f, m.getCurrentPosition().getValue(), 0f);
		// Pluto
		Planet p = new Planet("Pluto", "0xFF9900CC", "Purple", 2059, 321);
		p.computeCurrentPosition(5689530);
		assertEquals(301.506f, p.getCurrentPosition().getValue(), 0.001f);
	}

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Planet#computeDistanceTraveled(float)}.
	 */
	@Test
	public final void testComputeDistanceTraveled() {
		// Mercury
		Planet m = new Planet("Mercury", "0xFF8B4513", "Brown", 2, 1);
		m.computeCurrentPosition(5689530);
		assertEquals(92.0f, m.getCurrentPosition().getValue(), 0f);
		m.computeDistanceTraveled(10f);
		assertEquals(62.0f, m.getPredictedPosition().getValue(), 0f);
		// Pluto
		Planet p = new Planet("Pluto", "0xFF9900CC", "Purple", 2059, 321);
		p.computeCurrentPosition(5689530);
		assertEquals(301.506f, p.getCurrentPosition().getValue(), 0.001f);
		p.computeDistanceTraveled(10f);
		assertEquals(301.476f, p.getPredictedPosition().getValue(), 0.001f);
	}

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Planet#findWindow(java.util.List)}.
	 */
	@Test
	public final void testFindWindow() {
		createWindows();
		// Mercury
		Planet m = new Planet("Mercury", "0xFF8B4513", "Brown", 2, 1);
		m.computeCurrentPosition(5689530);
		assertEquals(92.0f, m.getCurrentPosition().getValue(), 0f);
		m.computeDistanceTraveled(10f);
		assertEquals(62.0f, m.getPredictedPosition().getValue(), 0f);
		assertFalse(m.findWindow(wins));
		m.computeDistanceTraveled(30f);
		assertEquals(2.0f, m.getPredictedPosition().getValue(), 0f);
		assertTrue(m.findWindow(wins));
		assertEquals("hr_dev_w1", m.getWindow().getWindowName());
		// Pluto
		Planet p = new Planet("Pluto", "0xFF9900CC", "Purple", 2059, 321);
		p.computeCurrentPosition(5689530);
		assertEquals(301.506f, p.getCurrentPosition().getValue(), 0.001f);
		p.computeDistanceTraveled(10f);
		assertEquals(301.476f, p.getPredictedPosition().getValue(), 0.001f);
		assertTrue(p.findWindow(wins));
		assertEquals("hr_dev_w2", p.getWindow().getWindowName());
		p.computeDistanceTraveled(20000f);
		assertEquals(243.225f, p.getPredictedPosition().getValue(), 0.001f);
		assertFalse(p.findWindow(wins));
	}
	
	
	private final void createWindows() {
		wins = new ArrayList<HelioroomWindow>();
		wins.add(new HelioroomWindow("hr_dev_w1", 45, 0));
		wins.add(new HelioroomWindow("hr_dev_w2", 315, 270));
		wins.add(new HelioroomWindow("hr_dev_w3", 225, 180));
		wins.add(new HelioroomWindow("hr_dev_w4", 135, 90));
	}

}
