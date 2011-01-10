/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.libclient;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.CallbackConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.TopicNotFoundException;

/**Test Event client.
 * 
 * Ajouter dans eclipse (Run Configurations->Arguments->VM Arguments): 
 * -Djava.endorsed.dirs=${env_var:JACORB_HOME}/lib
 * @author Franck
 *
 */
public class TestEventClient {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInit() throws EventClientException, TopicNotFoundException, ChannelNotFoundException, AlreadyRegisteredException{

			/*EventClient ec = EventClient.init(null);
			ChannelAdmin channelAdmin= ec.resolveChannelByTopic("MEZZO");
			ProxyForPushConsumer consumerProxy = channelAdmin.getProxyForPushConsumer("MEZZO");
			callBackConsumerImpl callbackImpl = new callBackConsumerImpl();
			CallbackConsumer cbc=ec.serveCallbackConsumer(callbackImpl);
			consumerProxy.subscribe(cbc);
			System.out.println("ALL DONE");*/
	}
}
