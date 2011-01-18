package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
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
	public void testProxyForPushSupplierImplChannelCtr() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testProxyForPushSupplierImplString() {
		fail("Not yet implemented"); // TODO
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		// vérification de l'appel à la méthode de suppression 
	    EasyMock.verify(mockCtr);
	 
	    pfps = null;
	}

	@Test
	public void testPush() {
		fail("Not yet implemented"); // TODO
	}

}
