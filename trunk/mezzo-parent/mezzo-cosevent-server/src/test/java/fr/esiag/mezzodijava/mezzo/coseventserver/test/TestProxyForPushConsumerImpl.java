package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

public class TestProxyForPushConsumerImpl {

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
	public void testProxyForPushConsumerImplChannelCtr() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testProxyForPushConsumerImplString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testProxyForPushConsumerImpl() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSubscribe() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUnsubscribe() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConnect() {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl(mockCtr);
		try {
			mockCtr.addProxyForPushConsumerToConnectedList(pfpc);
		} catch (AlreadyConnectedException e1) {
			fail();
			e1.printStackTrace();
		} catch (MaximalConnectionReachedException e1) {
			fail();
			e1.printStackTrace();
		} catch (NotRegisteredException e) {
			fail();
			e.printStackTrace();
		}
		// enregistrement
	    EasyMock.replay(mockCtr);
	    try {
	    	// lancement du test de la méthode
	    	pfpc.connect();
		} catch (AlreadyConnectedException e) {
			fail();
			e.printStackTrace();
		} catch (MaximalConnectionReachedException e) {
			fail();
			e.printStackTrace();
		} catch (NotRegisteredException e) {
			fail();
			e.printStackTrace();
		}
		// vérification de l'appel à la méthode d'ajout  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testDisconnect() {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl(mockCtr);
		try {
			mockCtr.removeProxyForPushConsumerFromConnectedList(pfpc);
		} catch (NotRegisteredException e) {
			fail();
			e.printStackTrace();
		} catch (NotConnectedException e) {
			fail();
			e.printStackTrace();
		}
		// enregistrement
	    EasyMock.replay(mockCtr);
	    try {
	    	// lancement du test de la méthode
	    	pfpc.disconnect();
		} catch (NotRegisteredException e) {
			fail();
			e.printStackTrace();
		} catch (NotConnectedException e) {
			fail();
			e.printStackTrace();
		}
		// vérification de l'appel à la méthode de suppression  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testReceive() {
		fail("Not yet implemented"); // TODO
	}

}
