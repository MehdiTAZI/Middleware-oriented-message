package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;


public class TestBFFactory {
	@Test
	public void createOrb(){
		assertNotNull(BFFactory.createOrb(null, null));
	}
	@Test
	public void createChannel(){
		String name = "createChannel";
		int capacity = 0;
		Channel chanOutput = BFFactory.createChannel(name, capacity);
		assertNotNull(chanOutput);
		assertEquals(name, chanOutput.getTopic());
		assertEquals(capacity, chanOutput.getCapacity());
		
	}
	@Test
	public void createChannelCtr(){
		String name = "createChannelCtr";
		ChannelCtr channelCtr = BFFactory.createChannelCtr(name);
		assertNotNull(channelCtr);
		assertNotNull(channelCtr.getChannel());
		assertEquals(name,channelCtr.getChannel().getTopic());
	}
	
	@Test
	public void createChannelAdminCtr(){
		String name = "createChannelAdminCtr";
		ChannelAdminCtr channelAdminCtr = BFFactory.createChannelAdminCtr(name);
		assertNotNull(channelAdminCtr);
		assertEquals(name, channelAdminCtr.getTopic());
	}
	
	@Test
	public void createChannelAdminImpl(){
		String name = "createChannelAdminImpl";
		ChannelAdminImpl channelAdminImpl = BFFactory.createChannelAdminImpl(name);
		assertNotNull(channelAdminImpl);
		assertEquals(name, channelAdminImpl.getTopic());
	}

}