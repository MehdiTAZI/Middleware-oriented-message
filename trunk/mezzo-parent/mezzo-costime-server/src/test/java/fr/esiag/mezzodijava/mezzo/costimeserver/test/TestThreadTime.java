package fr.esiag.mezzodijava.mezzo.costimeserver.test;


import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.ThreadTime;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;

public class TestThreadTime {

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
	public void testRunWithComponentSubscribed() throws AlreadyRegisteredException {
		
		// création du mock pour un component Synchronizable
		Synchronizable mockSync = EasyMock.createNiceMock(Synchronizable.class);
		// création du time model
		TimeServiceModel model = new TimeServiceModel();
		// création du time controller
		TimeServiceCtr ctr = new TimeServiceCtr(model);
		// ajout d'un composant
		ctr.subscribe(mockSync);
		// appel espéré
		mockSync.date(EasyMock.anyLong());
		// enregistrement
		EasyMock.replay(mockSync);
		// création du threadTime
		ThreadTime tt = new ThreadTime(model);
		tt.synchronizeComponent();
		EasyMock.verify(mockSync);
		mockSync = null;
	}
	
	@Test
	public void testRunWithComponentSubscribedWithTimeSpan() throws AlreadyRegisteredException {
		
		// création du mock pour un component Synchronizable
		Synchronizable mockSync = EasyMock.createNiceMock(Synchronizable.class);
		// création du time model
		TimeServiceModel model = new TimeServiceModel();
		// création du time controller
		TimeServiceCtr ctr = new TimeServiceCtr(model);
		// ajout d'un composant
		ctr.subscribe(mockSync);
		// appel espéré
		mockSync.date(EasyMock.anyLong());
		// enregistrement
		EasyMock.replay(mockSync);
		// création du threadTime
		ThreadTime tt = new ThreadTime(model,0);
		tt.synchronizeComponent();
		EasyMock.verify(mockSync);
		mockSync = null;
	}

}
