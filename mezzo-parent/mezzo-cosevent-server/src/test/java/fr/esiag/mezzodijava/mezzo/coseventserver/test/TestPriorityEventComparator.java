package fr.esiag.mezzodijava.mezzo.coseventserver.test;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.PriorityEventComparator;

public class TestPriorityEventComparator {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCompare(){
		PriorityEventComparator p = new PriorityEventComparator();
		Body body = new Body("nothing");
		// on compare sur les dates de création
		Header h1 = new Header(1, 1, 15111982, 10);
		Header h2 = new Header(1, 1, 15111981, 10);
		Event e1 = new Event(h1, body);
		Event e2 = new Event(h2, body);
		// si e1 est > à e2 donc plus récente
		assertEquals(p.compare(e1, e2),1);
		
		// on compare sur les dates de création
		Header h3 = new Header(1, 1, 15111981, 10);
		Header h4 = new Header(1, 1, 15111982, 10);
		Event e3 = new Event(h3, body);
		Event e4 = new Event(h4, body);
		// si e3 est < à e4 donc plus ancienne
		assertEquals(p.compare(e3, e4),-1);
		
		// on compare sur les priorités
		Header h5 = new Header(1, 2, 15111982, 10);
		Header h6 = new Header(1, 1, 15111981, 10);
		Event e5 = new Event(h5, body);
		Event e6 = new Event(h6, body);
		// si priorité e5 est > à e6 donc plus prioritaire
		assertEquals(p.compare(e5, e6),-1);
		
		// on compare sur les priorités
		Header h7 = new Header(1, 1, 15111982, 10);
		Header h8 = new Header(1, 2, 15111981, 10);
		Event e7 = new Event(h7, body);
		Event e8 = new Event(h8, body);
		// si priorité e7 est < à e8 donc moins prioritaire
		assertEquals(p.compare(e7, e8),1);
		
		// si égaux
		Header h9 = new Header(1, 1, 15111982, 10);
		Header h10 = new Header(1, 1, 15111982, 10);
		Event e9 = new Event(h9, body);
		Event e10 = new Event(h10, body);
		// si priorité e9 est = à e10
		assertEquals(p.compare(e9, e10),1);
	}

}
