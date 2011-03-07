package fr.esiag.mezzodijava.mezzo.costimeserver.test;


import org.easymock.EasyMock;
import static org.junit.Assert.*;
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
		assertEquals(mockSync,test.getCtr().getModel().getComponentSubscribed().get(0));
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
	
}