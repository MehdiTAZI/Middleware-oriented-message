package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.MaximalConnectionReachedException;
import fr.esiag.mezzodijava.mezzo.cosevent.NotConnectedException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class TestProxyForPullSupplierImpl {

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
	public void testConnect() throws ChannelNotFoundException,
		MaximalConnectionReachedException,
		AlreadyConnectedException {
		
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// création du mock pour le callback
		CallbackSupplier mockCall = EasyMock.createNiceMock(CallbackSupplier.class);
		// appel et valeur de retour espérée
		EasyMock.expect(mockCtr.getChannel()).andReturn(
				new Channel("TEST",3));
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPullSupplierImpl pfps = new ProxyForPullSupplierImpl("TEST","testsupplier");
		mockCtr.addProxyForPullSupplierToConnectedList(pfps);
		// enregistrement
	    EasyMock.replay(mockCtr);
	    
	    // lancement du test de la méthode
	    pfps.connect(mockCall);
		
		// vérification de l'appel à la méthode d'ajout  
	    EasyMock.verify(mockCtr);
	    pfps = null;
	}

	@Test
	public void testDisconnect() throws ChannelNotFoundException,
		NotConnectedException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPullSupplierImpl pfps = new ProxyForPullSupplierImpl("TEST","testsupplier");
		//cette methode doit etre appelee
		mockCtr.removeProxyForPullSupplierFromConnectedList(pfps);
		// appel et valeur de retour espérée
		EasyMock.expect(mockCtr.getChannel()).andReturn(
				new Channel("TEST",3));
		// enregistrement
	    EasyMock.replay(mockCtr);
	   
	    // lancement du test de la méthode
	    pfps.disconnect();
	    	
		// vérification de l'appel à la méthode de suppression  
	    EasyMock.verify(mockCtr);
	    pfps = null;
	}

	@Test
	public void testAsk() {
		fail("Not yet implemented");
	}

}
