package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;

public class TestChannelAdminCtr {

	ChannelAdminCtr cac = new ChannelAdminCtr("TEST_ADMIN");
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
	public void testChannelAdminCtr() {
		assertEquals("TEST_ADMIN", cac.getTopic());
	}

	@Test
	public void testCreateProxyForPushSupplier() {
		cac.createProxyForPushSupplier("testconsumer");
	}

	@Test
	public void testCreateProxyForPushConsumer() {
		cac.createProxyForPushConsumer("testconsumer");
	}

}
