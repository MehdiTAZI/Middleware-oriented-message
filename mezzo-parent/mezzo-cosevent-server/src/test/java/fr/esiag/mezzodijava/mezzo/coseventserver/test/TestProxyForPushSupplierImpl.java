package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

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
	public void testConnect() {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl(mockCtr);
		try {
			mockCtr.addProxyForPushSupplierToConnectedList(pfps);
		} catch (AlreadyConnectedException e1) {
			fail();
			e1.printStackTrace();
		} catch (MaximalConnectionReachedException e1) {
			fail();
			e1.printStackTrace();
		}
		// enregistrement
	    EasyMock.replay(mockCtr);
	    try {
	    	// lancement du test de la méthode
			pfps.connect();
		} catch (AlreadyConnectedException e) {
			fail();
			e.printStackTrace();
		} catch (MaximalConnectionReachedException e) {
			fail();
			e.printStackTrace();
		}
		// vérification de l'appel à la méthode d'ajout  
	    EasyMock.verify(mockCtr);
	    
	    pfps = null;
	}

	@Test
	public void testDisconnect() {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl(mockCtr);
		
		try {
			mockCtr.removeProxyForPushSupplierFromConnectedList(pfps);
		} catch (NotConnectedException e1) {
			fail();
			e1.printStackTrace();
		}
		
		// enregistrement
	    EasyMock.replay(mockCtr);
	    
	    	// lancement du test de la méthode
			try {
				pfps.disconnect();
			} catch (ChannelNotFoundException e) {
				//nothing to do, test ok
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		// vérification de l'appel à la méthode de suppression 
	    EasyMock.verify(mockCtr);
	    pfps = null;
	}

	@Test
	public void testPushConnected() throws NotConnectedException, AlreadyConnectedException, MaximalConnectionReachedException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl(mockCtr);
		// Nouvel event :
		Event e = new Event((new Date()).getTime(),"TEST_PUSH");
		mockCtr.addEvent(e);
		// on fait le connect
		pfps.connect();
		// on rembobine le mock
	    EasyMock.replay(mockCtr);
		//test !
		pfps.push(e);
	}
	@Test
	public void testPushNotConnected()  {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushSupplierImpl pfps = new ProxyForPushSupplierImpl(mockCtr);
		// Nouvel event :
		Event e = new Event((new Date()).getTime(),"TEST_PUSH");
		//enregistrement d'un appel de addevent dans le mock
		mockCtr.addEvent(e);
		// on rembobine le mock
	    EasyMock.replay(mockCtr);
		//appel a tester
	    try {
			pfps.push(e);
			fail("NotConnectException was expected here");
		} catch (NotConnectedException e1) {
			//nothing to do, test ok
		}
	}

}
