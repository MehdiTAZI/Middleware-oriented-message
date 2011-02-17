package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.fail;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class TestProxyForPushSupplierImpl {

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
	public void testConnect() throws AlreadyConnectedException, MaximalConnectionReachedException {
		// création du mock pour le contrôleur
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// appel et valeur de retour espérée
		EasyMock.expect(mockCtr.getChannel()).andReturn(
				new Channel("TEST",3));
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST");

		mockCtr.addProxyForPushSupplierToConnectedList(pfps);

		// enregistrement
		EasyMock.replay(mockCtr);

		// lancement du test de la méthode
		pfps.connect();

		// vérification de l'appel à la méthode d'ajout
		EasyMock.verify(mockCtr);

		pfps = null;
	}

	@Test
	public void testDisconnect() throws ChannelNotFoundException,
			NotConnectedException {
		// création du mock pour le contrôleur
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// appel et valeur de retour espérée
		EasyMock.expect(mockCtr.getChannel()).andReturn(
				new Channel("TEST",3));
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST");

		mockCtr.removeProxyForPushSupplierFromConnectedList(pfps);

		// enregistrement
		EasyMock.replay(mockCtr);

		// lancement du test de la méthode
		pfps.disconnect();

		// vérification de l'appel à la méthode de suppression
		EasyMock.verify(mockCtr);
		pfps = null;
	}

	@Test
	public void testPushConnected() throws NotConnectedException,
			AlreadyConnectedException, MaximalConnectionReachedException {
		// création du mock pour le contrôleur
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// appel et valeur de retour espérée
		//EasyMock.expect(mockCtr.getChannel()).andReturn(
		//		new Channel("TEST",3));
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST");
		// Nouvel event :
		Header header=new Header(123, 1, 01012011, 120);
		Body body=new Body("Test_PUSH");
		Event e = new Event(header,body);
		mockCtr.addEvent(e);
		// on rembobine le mock
		EasyMock.replay(mockCtr);
		// on fait le connect
		pfps.connect();
		// test !
		pfps.push(e);
	}

	@Test
	public void testPushNotConnected() {
		// création du mock pour le contrôleur
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST");
		// Nouvel event :
		Header header=new Header(123, 1, 01012011, 120);
		Body body=new Body("Test_PUSH");
		Event e = new Event(header,body);
		// enregistrement d'un appel de addevent dans le mock
		mockCtr.addEvent(e);
		// on rembobine le mock
		EasyMock.replay(mockCtr);
		// appel a tester
		try {
			pfps.push(e);
			fail("NotConnectException was expected here");
		} catch (NotConnectedException e1) {
			// nothing to do, test ok
		}
	}

}
