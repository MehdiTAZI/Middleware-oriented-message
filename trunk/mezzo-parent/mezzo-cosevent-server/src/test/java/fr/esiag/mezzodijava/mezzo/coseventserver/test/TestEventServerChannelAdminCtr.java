package fr.esiag.mezzodijava.mezzo.coseventserver.test;


import static org.junit.Assert.*;

import net.sf.cglib.proxy.Factory;

import org.apache.derby.vti.Restriction.OR;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
@SuppressWarnings("static-access")
public class TestEventServerChannelAdminCtr{

	private static EventServerChannelAdminCtr ctr;
	private static BFFactory factory;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = EasyMock.createMock(BFFactory.class);
		ctr=new EventServerChannelAdminCtr();
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
	public void createChannel() throws ChannelAlreadyExistsException{
		EasyMock.replay(factory);
		long id = ctr.createChannel("MEZZO1", 30);
		EasyMock.verify(factory);
		assertEquals(factory.getChannel("MEZZO1").getIdentifier(),id);
		assertEquals(factory.getChannel(id).getCapacity(),30);
	}
	@Test
	public void getChannel() throws ChannelAlreadyExistsException, ChannelNotFoundException{
		long id = ctr.createChannel("MEZZO2", 30);
		assertNotNull(ctr.getChannel(id));
	}
	@Test 
	public void destroyChannel() throws ChannelAlreadyExistsException, ChannelNotFoundException{
		long id = ctr.createChannel("MEZZO3", 30);
		assertEquals(factory.getChannel("MEZZO3").getIdentifier(),id);
		assertEquals(factory.getChannel("MEZZO3").getCapacity(),30);
		ctr.destroyChannel(id);
		assertNull(ctr.getChannel(id));
	}
	@Test 
	public void changeChannelCapacity() throws ChannelAlreadyExistsException, ChannelNotFoundException, CannotReduceCapacityException{
		long id = ctr.createChannel("MEZZO4", 30);
		assertEquals(factory.getChannel("MEZZO4").getCapacity(),30);
		ctr.changeChannelCapacity(id,40);
		assertEquals(factory.getChannel(id).getCapacity(),40);
	}
}
