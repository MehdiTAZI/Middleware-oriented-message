package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyConnectedException;
import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ConsumerNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
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
	public void testSubscribe() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUnsubscribe() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConnect() throws NotRegisteredException, AlreadyConnectedException, MaximalConnectionReachedException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl(mockCtr);
		
		mockCtr.addProxyForPushConsumerToConnectedList(pfpc);
		
		// enregistrement
	    EasyMock.replay(mockCtr);
	    
	    // lancement du test de la méthode
	    pfpc.connect();
		
		// vérification de l'appel à la méthode d'ajout  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testDisconnect() throws NotRegisteredException, NotConnectedException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl(mockCtr);
		
		mockCtr.removeProxyForPushConsumerFromConnectedList(pfpc);
		
		// enregistrement
	    EasyMock.replay(mockCtr);
	   
	    // lancement du test de la méthode
	    pfpc.disconnect();
	    	
		// vérification de l'appel à la méthode de suppression  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testReceive() throws AlreadyRegisteredException, ConsumerNotFoundException {
		// création du mock pour le callback
		CallbackConsumer mockCall = EasyMock.createNiceMock(CallbackConsumer.class);
		
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("toto");
		
		// nouvel event
		Event evt = new Event(23,"toto");
		
		mockCall.receive(evt);
		
		// enregistrement
	    EasyMock.replay(mockCall);
	    
	    // subscribe du mock
	    pfpc.subscribe(mockCall); 
		
	    // lancement du test de la méthode
		pfpc.receive(evt);
		
		// vérification de l'appel à la méthode receive 
	    EasyMock.verify(mockCall);
	    
	    pfpc = null;
	}

}
