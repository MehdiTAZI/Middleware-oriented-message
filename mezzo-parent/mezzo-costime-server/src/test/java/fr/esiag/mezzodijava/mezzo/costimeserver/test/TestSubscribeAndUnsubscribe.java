package fr.esiag.mezzodijava.mezzo.costimeserver.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.impl.TimeServiceImpl;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;


public class TestSubscribeAndUnsubscribe {

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
	public void testSubscrib() throws AlreadyRegisteredException{
		TimeServiceCtr ctr=new TimeServiceCtr(new TimeServiceModel());
		TimeServiceImpl test=new TimeServiceImpl(ctr);
		Synchronizable mockSync=EasyMock.createNiceMock(Synchronizable.class);
		test.subscribe(mockSync);
		//passage par iterator vue qu'on utilise les set mainteant. 
		Iterator<Synchronizable> iterator=  test.getCtr().getModel().getComponentSubscribed().iterator();
		Synchronizable firstElem= iterator.next();
		assertEquals(mockSync,firstElem);
	}
	
	@Test
	public void testUnsubscrib() throws NotRegisteredException, AlreadyRegisteredException{
		TimeServiceCtr ctr=new TimeServiceCtr(new TimeServiceModel());
		TimeServiceImpl test=new TimeServiceImpl(ctr);
		Synchronizable mockSync=EasyMock.createNiceMock(Synchronizable.class);
		test.subscribe(mockSync);
		assertTrue(test.getCtr().getModel().getComponentSubscribed().contains(mockSync));
		test.unsubscribe(mockSync);
		assertFalse(test.getCtr().getModel().getComponentSubscribed().contains(mockSync));
	}
	
	@Test
	public void testSubscribException() {
		TimeServiceCtr ctr=new TimeServiceCtr(new TimeServiceModel());
		TimeServiceImpl test=new TimeServiceImpl(ctr);
		Synchronizable mockSync=EasyMock.createNiceMock(Synchronizable.class);
		try {
			test.subscribe(mockSync);
			test.subscribe(mockSync);
			fail("exception non levée");
		} 
		catch (AlreadyRegisteredException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUnsubscribException() {
		TimeServiceCtr ctr=new TimeServiceCtr(new TimeServiceModel());
		TimeServiceImpl test=new TimeServiceImpl(ctr);
		Synchronizable mockSync=EasyMock.createNiceMock(Synchronizable.class);
		try {
			test.unsubscribe(mockSync);
			fail("exception non levée");
		} catch (NotRegisteredException e) {
			assertTrue(true);
		}
	}
	
}