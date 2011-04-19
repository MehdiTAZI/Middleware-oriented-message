package fr.esiag.mezzodijava.mezzo.costimeserver.test;


import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.ThreadTime;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.main.CosTimeServer;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

public class TestThreadTime {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    CosTimeServer.initConf();
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
	public void testRunWithComponentSubscribedWithTimeSpan() throws AlreadyRegisteredException, NotRegisteredException, InterruptedException {
		
		// création du mock pour un component Synchronizable
		Synchronizable mockSync = EasyMock.createNiceMock(Synchronizable.class);
		// création du time model
		TimeServiceModel model = new TimeServiceModel();
		// création du time controller
		TimeServiceCtr ctr = new TimeServiceCtr(model);
		// ajout d'un composant
		ctr.subscribe(mockSync,500);
		// appel espéré
		//mockSync.date(EasyMock.anyLong());
		// enregistrement
		EasyMock.replay(mockSync);
		// création du threadTime
		ThreadTime tt = new ThreadTime(mockSync,model,100);
		(new Thread(tt)).start();
		Thread.sleep(500);
		ctr.unsubscribe(mockSync);
		//EasyMock.verify(mockSync);
		mockSync = null;
	}

}
