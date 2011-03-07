package fr.esiag.mezzodijava.mezzo.costimeserver.test;


import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExtOperations;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import fr.esiag.mezzodijava.mezzo.costime.TimeServicePOATie;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.ThreadTime;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.impl.TimeServiceImpl;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;
import fr.esiag.mezzodijava.mezzo.costimeserver.publisher.TimeServicePublisher;

public class TestPublisher {

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
	public void testPublish() throws NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		/*// création des mocks
		TimeServiceCtr mockCtr = EasyMock.createNiceMock(TimeServiceCtr.class);	
		TimeServiceImpl mocktime = EasyMock.createNiceMock(TimeServiceImpl.class);
		ThreadTime mocktt = EasyMock.createNiceMock(ThreadTime.class);
		TimeServicePublisher publisher = new TimeServicePublisher();
		long timespan = 1000;
		String name = "TIME";
		ORB mockorb = EasyMock.createNiceMock(ORB.class);
		NamingContextExt mocknc = EasyMock.createNiceMock(NamingContextExt.class);
		
		// appels espérés
		//EasyMock.expect(mocktime.getCtr()).andReturn(EasyMock.isA(TimeServiceCtr.class));
		//mocktt.run();
		//mocknc.rebind(mocknc.to_name(name),(Object) EasyMock.anyObject());
		
		
		// enregistrement
		EasyMock.replay();
		// lancement de publish
		publisher.publish(name, mocktime, mockorb, timespan);
		EasyMock.verify();
		mockCtr = null;*/
	}

}
