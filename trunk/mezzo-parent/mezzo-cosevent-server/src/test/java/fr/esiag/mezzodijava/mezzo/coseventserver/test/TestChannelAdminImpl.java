package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;

public class TestChannelAdminImpl {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	CosEventServer.initConf();
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
    public void testChannelAdminImpl() {
	assertNotNull(BFFactory.createChannelAdminImpl("topic"));
    }

    @Test
    public void testGetProxyForPushSupplier() throws ChannelNotFoundException {
	// création du mock pour le contrôleur
	ChannelAdminCtr mockAdminCtr = EasyMock
		.createNiceMock(ChannelAdminCtr.class);
	// création du pps mock
	ProxyForPushSupplier mockpps = EasyMock
		.createNiceMock(ProxyForPushSupplier.class);

	// appel espéré : marche pas du fait de la valeur de retour
	// mockAdminCtr.createProxyForPushSupplier();

	// appel et valeur de retour espérée
	EasyMock.expect(mockAdminCtr.createProxyForPushSupplier("testsupplier"))
		.andReturn(mockpps);
	// enregistrement
	EasyMock.replay(mockAdminCtr);
	// inject mock in Factory
	BFFactory.setAlternateChannelAdminCtr("TEST", mockAdminCtr);
	ChannelAdminImpl caImp = new ChannelAdminImpl("TEST");
	caImp.getProxyForPushSupplier("testsupplier");
	EasyMock.verify(mockAdminCtr);
	mockAdminCtr = null;
	mockpps = null;
	caImp = null;
    }

    @Test
    public void testGetProxyForPushConsumer() throws ChannelNotFoundException {
	// création du mock pour le contrôleur
	ChannelAdminCtr mockAdminCtr = EasyMock
		.createNiceMock(ChannelAdminCtr.class);
	// création du pps mock
	ProxyForPushConsumer mockppc = EasyMock
		.createNiceMock(ProxyForPushConsumer.class);
	// appel et valeur de retour espérée
	EasyMock.expect(mockAdminCtr.createProxyForPushConsumer("testconsumer"))
		.andReturn(mockppc);
	// enregistrement
	EasyMock.replay(mockAdminCtr);
	// inject mock in Factory
	BFFactory.setAlternateChannelAdminCtr("TEST", mockAdminCtr);
	ChannelAdminImpl caImp = new ChannelAdminImpl("TEST");
	caImp.getProxyForPushConsumer("testconsumer");
	EasyMock.verify(mockAdminCtr);
	mockAdminCtr = null;
	mockppc = null;
	caImp = null;
    }

}
