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
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

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
	public void testSubscribe() throws AlreadyRegisteredException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("TEST");
		
		// CallBackConsumerMock
		CallbackConsumer ccMock = EasyMock.createNiceMock(CallbackConsumer.class);
		//pour que l'appel getChannel.getTopic() fonctionne
		EasyMock.expect(mockCtr.getChannel()).andReturn(new Channel("TEST", 1));

		mockCtr.addProxyForPushConsumerToSubscribedList(pfpc);
		
		// enregistrement
	    EasyMock.replay(mockCtr);
	    
	    // lancement du test de la méthode
	    pfpc.subscribe(ccMock);
		
		// vérification de l'appel à la méthode d'ajout  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testUnsubscribe() throws NotRegisteredException {
		//channelCtr.removeProxyForPushConsumerFromSubscribedList(this);
		
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("TEST");

		mockCtr.removeProxyForPushConsumerFromSubscribedList(pfpc);
		
		// enregistrement
	    EasyMock.replay(mockCtr);
	    
	    // lancement du test de la méthode
	    pfpc.unsubscribe();
		
		// vérification de l'appel à la méthode remove  
	    EasyMock.verify(mockCtr);
	    
	    pfpc = null;
	}

	@Test
	public void testConnect() throws NotRegisteredException, AlreadyConnectedException, MaximalConnectionReachedException {
		// création du mock pour le contrôleur 
		ChannelCtr mockCtr = EasyMock.createNiceMock(ChannelCtr.class);
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("TEST");
		
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
		// inject mock in Factory
		BFFactory.setAlternateChannelCtr("TEST", mockCtr);
		// nouveau proxy
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("TEST");
		
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
		Event evt = new Event(23,"toto",1,82567);
		
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
