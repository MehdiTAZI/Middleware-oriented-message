package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public class TestBFFactory {
    @Test
    public void createOrb() {
	assertNotNull(BFFactory.createOrb(null, null));
    }

    @Test
    public void createChannel() {
	String name = "createChannel";
	int capacity = 0;
	Channel chanOutput = EventServer.getInstance().createChannelEntity(
		name, capacity);
	assertNotNull(chanOutput);
	assertEquals(name, chanOutput.getTopic());
	assertEquals(capacity, chanOutput.getCapacity());

    }

    @Test
    public void testCreateChannelAlreadyExist() {
	String name = "createChannel";
	int capacity = 0;
	try {
	    Channel chanOutput = BFFactory.createChannel(name, capacity);
	    Channel chanOutput2 = BFFactory.createChannel(name, capacity);
	    fail("exception non levée");
	} catch (ChannelAlreadyExistsException e) {
	    assertTrue(true);
	}

    }

    @Test
    public void createChannelCtr() {
	String name = "createChannelCtr";
	ChannelCtr channelCtr = BFFactory.createChannelCtr(name);
	assertNotNull(channelCtr);
	assertNotNull(channelCtr.getChannel());
	assertEquals(name, channelCtr.getChannel().getTopic());
    }

    @Test
    public void createChannelAdminCtr() {
	String name = "createChannelAdminCtr";
	ChannelAdminCtr channelAdminCtr = BFFactory.createChannelAdminCtr(name);
	assertNotNull(channelAdminCtr);
	assertEquals(name, channelAdminCtr.getTopic());
    }

    @Test
    public void createChannelAdminImpl() {
	String name = "createChannelAdminImpl";
	ChannelAdminImpl channelAdminImpl = BFFactory
		.createChannelAdminImpl(name);
	assertNotNull(channelAdminImpl);
	assertEquals(name, channelAdminImpl.getTopic());
    }

}
