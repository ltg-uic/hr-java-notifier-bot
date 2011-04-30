/*
 * Created Apr 30, 2011
 */
package ltg.ps.phenomena.support.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class TestDegree {

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Degree#Degree(float)}.
	 */
	@Test
	public final void testDegree() {
		Degree d = null; 
		d = new Degree(90);
		assertEquals(90.0f, d.getValue(), 0f);
		d = new Degree(-90);
		assertEquals(270.0f, d.getValue(), 0f);
		d = new Degree(450);
		assertEquals(90.0f, d.getValue(), 0f);
		d = new Degree(-450);
		assertEquals(270.0f, d.getValue(), 0f);
		d = new Degree(360);
		assertEquals(0.0f, d.getValue(), 0f);
	}

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Degree#add(ltg.ps.phenomena.support.model.Degree)}.
	 */
	@Test
	public final void testAdd() {
		Degree d1,d2, d3 = null;
		// ++
		d1 = new Degree(45.0f);
		d2 = new Degree(35.0f);
		d3 = d1.add(d2);
		assertEquals(80.0f, d3.getValue(), 0f);
		// +-
		d1 = new Degree(45.0f);
		d2 = new Degree(-35.0f);
		d3 = d1.add(d2);
		assertEquals(10.0f, d3.getValue(), 0f);
		// -+
		d1 = new Degree(-45.0f);
		d2 = new Degree(35.0f);
		d3 = d1.add(d2);
		assertEquals(350.0f, d3.getValue(), 0f);
		// --
		d1 = new Degree(-45.0f);
		d2 = new Degree(-35.0f);
		d3 = d1.add(d2);
		assertEquals(280.0f, d3.getValue(), 0f);
	}

	/**
	 * Test method for {@link ltg.ps.phenomena.support.model.Degree#sub(ltg.ps.phenomena.support.model.Degree)}.
	 */
	@Test
	public final void testSub() {
		Degree d1,d2, d3 = null;
		// ++
		d1 = new Degree(45.0f);
		d2 = new Degree(35.0f);
		d3 = d1.sub(d2);
		assertEquals(10.0f, d3.getValue(), 0f);
		// +-
		d1 = new Degree(45.0f);
		d2 = new Degree(-35.0f);
		d3 = d1.sub(d2);
		assertEquals(80.0f, d3.getValue(), 0f);
		// -+
		d1 = new Degree(-45.0f);
		d2 = new Degree(35.0f);
		d3 = d1.sub(d2);
		assertEquals(280.0f, d3.getValue(), 0f);
		// --
		d1 = new Degree(-45.0f);
		d2 = new Degree(-35.0f);
		d3 = d1.sub(d2);
		assertEquals(350.0f, d3.getValue(), 0f);
	}
	
	@Test
	public final void testInsideCCWArc() {
		Degree d,begin, end = null;
		// no across 0 inside
		d = new Degree(88.0f);
		begin = new Degree(90.0f);
		end = new Degree(0.0f);
		assertTrue(d.insideCCWArc(begin, end));
		// across 0 inside
		d = new Degree(356.0f);
		begin = new Degree(5.0f);
		end = new Degree(275.0f);
		assertTrue(d.insideCCWArc(begin, end));
		// no across 0 outside
		d = new Degree(92.0f);
		begin = new Degree(90.0f);
		end = new Degree(0.0f);
		assertFalse(d.insideCCWArc(begin, end));
		// across 0 outside
		d = new Degree(7.0f);
		begin = new Degree(5.0f);
		end = new Degree(275.0f);
		assertFalse(d.insideCCWArc(begin, end));
	}
	

}
