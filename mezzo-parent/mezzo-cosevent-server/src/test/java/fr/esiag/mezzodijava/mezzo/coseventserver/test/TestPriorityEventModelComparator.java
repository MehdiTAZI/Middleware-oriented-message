package fr.esiag.mezzodijava.mezzo.coseventserver.test;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.PriorityEventModelComparator;

public class TestPriorityEventModelComparator {

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
		PriorityEventModelComparator p = new PriorityEventModelComparator();
		EventModel e1 = new EventModel();
		EventModel e2 = new EventModel();
		EventModel e3 = new EventModel();
		EventModel e4 = new EventModel();
		EventModel e5 = new EventModel();
		EventModel e6 = new EventModel();
		EventModel e7 = new EventModel();
		EventModel e8 = new EventModel();
		EventModel e9 = new EventModel();
		EventModel e10 = new EventModel();
		
		// on compare sur les dates de création
		e1.setCode(1);
		e1.setCreationdate(15111982);
		e1.setPriority(1);
		e1.setTimetolive(10);
		e1.setType("String");
		e2.setCode(1);
		e2.setCreationdate(15111981);
		e2.setPriority(1);
		e2.setTimetolive(10);
		e2.setType("String");
		// si e1 est > à e2 donc plus récente
		assertEquals(p.compare(e1, e2),1);
		
		// on compare sur les dates de création
		e3.setCode(1);
		e3.setCreationdate(15111981);
		e3.setPriority(1);
		e3.setTimetolive(10);
		e3.setType("String");
		e4.setCode(1);
		e4.setCreationdate(15111982);
		e4.setPriority(1);
		e4.setTimetolive(10);
		e4.setType("String");
		// si e3 est < à e4 donc plus ancienne
		assertEquals(p.compare(e3, e4),-1);
		
		// on compare sur les priorités
		e5.setCode(1);
		e5.setCreationdate(15111982);
		e5.setPriority(2);
		e5.setTimetolive(10);
		e5.setType("String");
		e6.setCode(1);
		e6.setCreationdate(15111981);
		e6.setPriority(1);
		e6.setTimetolive(10);
		e6.setType("String");
		// si priorité e5 est > à e6 donc plus prioritaire
		assertEquals(p.compare(e5, e6),-1);
		
		// on compare sur les priorités
		e7.setCode(1);
		e7.setCreationdate(15111982);
		e7.setPriority(1);
		e7.setTimetolive(10);
		e7.setType("String");
		e8.setCode(1);
		e8.setCreationdate(15111981);
		e8.setPriority(2);
		e8.setTimetolive(10);
		e8.setType("String");
		// si priorité e7 est < à e8 donc moins prioritaire
		assertEquals(p.compare(e7, e8),1);
		
		// si égaux
		e9.setCode(1);
		e9.setCreationdate(15111982);
		e9.setPriority(1);
		e9.setTimetolive(10);
		e9.setType("String");
		e10.setCode(1);
		e10.setCreationdate(15111981);
		e10.setPriority(2);
		e10.setTimetolive(10);
		e10.setType("String");
		// si priorité e9 est = à e10
		assertEquals(p.compare(e9, e10),1);
	}

}
