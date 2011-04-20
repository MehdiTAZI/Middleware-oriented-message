package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.CannotReduceCapacityException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.EventServerChannelAdminImpl;

public class TestEventServerChannelAdminImpl {

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
	public void testEventServerChannelAdminImpl() {
		assertNotNull(BFFactory.createEventServerChannelAdminCtr("server"));
	}

	@Test
	public void testCreateChannel() throws ChannelAlreadyExistsException {
		// création du mock pour le contrôleur
		EventServerChannelAdminCtr mockAdminCtr = EasyMock
				.createNiceMock(EventServerChannelAdminCtr.class);
		// init du channel
		long id = -1;
		String name = "toto";
		int nb = 20;
		// appel et valeur de retour espérée
		EasyMock.expect(mockAdminCtr.createChannel(name, nb)).andReturn(2300L);
		// enregistrement
		EasyMock.replay(mockAdminCtr);
		// inject mock in Factory
		BFFactory.setAlternateServerChannelAdminCtr("test", mockAdminCtr);
		// création de la classe sous test
		EventServerChannelAdminImpl ev = new EventServerChannelAdminImpl("test");
		id = ev.createChannel(name, nb);
		EasyMock.verify(mockAdminCtr);
		assertTrue(id > -1);
		assertEquals(id, 2300);
		mockAdminCtr = null;
	}

	@Test
	public void testGetChannel() throws ChannelNotFoundException {

		// création des mock pour le contrôleur
		EventServerChannelAdminCtr mockAdminCtr = EasyMock
				.createNiceMock(EventServerChannelAdminCtr.class);
		ChannelAdmin mockAdmin = EasyMock.createNiceMock(ChannelAdmin.class);
		// init du channel id
		long id = 2300;
		// appel et valeur de retour espérée
		EasyMock.expect(mockAdminCtr.getChannel(id)).andReturn(mockAdmin);
		// mockAdminCtr.getChannel(2300L);
		// enregistrement
		EasyMock.replay(mockAdminCtr);
		// inject mock in Factory
		BFFactory.setAlternateServerChannelAdminCtr("test", mockAdminCtr);
		// création de la classe sous test
		EventServerChannelAdminImpl ev = new EventServerChannelAdminImpl("test");
		ev.getChannel(2300L);
		EasyMock.verify(mockAdminCtr);
		mockAdminCtr = null;
		mockAdmin = null;
	}

	@Test
	public void testDestroyChannel() throws ChannelNotFoundException {
		// création des mock pour le contrôleur et l'admin
		EventServerChannelAdminCtr mockAdminCtr = EasyMock
				.createNiceMock(EventServerChannelAdminCtr.class);
		// init du channel id
		long id = 2300;
		// appel et valeur de retour espérée
		mockAdminCtr.destroyChannel(id);
		// enregistrement
		EasyMock.replay(mockAdminCtr);
		// inject mock in Factory
		BFFactory.setAlternateServerChannelAdminCtr("test", mockAdminCtr);
		// création de la classe sous test
		EventServerChannelAdminImpl ev = new EventServerChannelAdminImpl("test");
		ev.destroyChannel(2300L);
		EasyMock.verify(mockAdminCtr);
		mockAdminCtr = null;
	}

	@Test
	public void testChangeChannelCapacity() throws ChannelNotFoundException, CannotReduceCapacityException {
		// création des mock pour le contrôleur et l'admin
		EventServerChannelAdminCtr mockAdminCtr = EasyMock
				.createNiceMock(EventServerChannelAdminCtr.class);
		// init du channel id
		long id = 2300;
		int capacity = 20;
		// appel et valeur de retour espérée
		mockAdminCtr.changeChannelCapacity(id, capacity);
		// enregistrement
		EasyMock.replay(mockAdminCtr);
		// inject mock in Factory
		BFFactory.setAlternateServerChannelAdminCtr("test", mockAdminCtr);
		// création de la classe sous test
		EventServerChannelAdminImpl ev = new EventServerChannelAdminImpl("test");
		ev.changeChannelCapacity(id, capacity);
		EasyMock.verify(mockAdminCtr);
		mockAdminCtr = null;
	}

}
