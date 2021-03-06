package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

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
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST_CONNECT", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST_CONNECT","testsupplier");
		mockCtr.addProxyForPushSupplierToConnectedList(pfps);

		//pour que l'appel getChannel.getTopic() fonctionne
		EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST_CONNECT", 1));

		// enregistrement
		EasyMock.replay(mockCtr);

		//mockCtr.addProxyForPushSupplierToConnectedList(pfps);


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
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);

		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST","testsupplier");

		mockCtr.removeProxyForPushSupplierFromConnectedList(pfps);

		//pour que l'appel getChannel.getTopic() fonctionne
		EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST", 1));

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
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// appel et valeur de retour espérée
		EasyMock.expect(mockCtr.getChannel()).andReturn(
				new Channel("TEST",3));
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST","testsupplier");
		// Nouvel event :
		Header header=new Header(123, 1, 01012011, 120);

		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_PUSH");
		Body body = new Body(any,"String");

		Event e = new Event(header,body);
		mockCtr.addEvent(e);
		//pour que l'appel getChannel.getTopic() fonctionne
		EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST", 3));
		// on rembobine le mock
		EasyMock.replay(mockCtr);
		// on fait le connect
		pfps.connect();
		// test !
		pfps.push(e);
	}

	@Test
	public void testPushNotConnected() {
		// ajout manuel du channel pour la correspondance channel-composant
		Channel channel = EventServer.getInstance().createChannelEntity("TEST", 2);
		DAOFactory.getJdbcDAO().insertChannel(channel);

		// création du mock pour le contrôleur
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl("TEST","testsupplier");
		// Nouvel event :
		Header header=new Header(123, 1, 01012011, 120);

		ORB orb=ORB.init();
		Any any=orb.create_any();
		any.insert_string("Test_PUSH");
		Body body = new Body(any,"String");

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
