/**
 * 
 */
package fr.esiag.mezzodijava.mezzo.libclient;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.EventClientException;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientExceptionCRAP;

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
	public void testInit() throws EventClientException{

			EventClient ec = EventClient.init(null);


	}
}
