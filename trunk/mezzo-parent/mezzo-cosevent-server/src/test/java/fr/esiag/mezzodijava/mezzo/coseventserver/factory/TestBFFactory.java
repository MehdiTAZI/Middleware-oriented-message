package fr.esiag.mezzodijava.mezzo.coseventserver.factory;

import static org.junit.Assert.*;

import org.junit.Assert.*;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ChannelAdminImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;


public class TestBFFactory {
	@Test
	public void createOrb(){
		assertNotNull(BFFactory.createOrb(null, null));
	}
	@Test
	public void createChannel(){
		String name = "bla";
		int capacity = 0;
		Channel chanOutput = BFFactory.createChannel(name, capacity);
		assertNotNull(chanOutput);
		assertEquals(name, chanOutput.getTopic());
		assertEquals(capacity, chanOutput.getCapacity());
		
	}
	@Test
	public void createChannelCtr(){
		String name = "bla";
		ChannelCtr channelCtr = BFFactory.createChannelCtr(name);
		assertNotNull(channelCtr);
		assertNotNull(channelCtr.getChannel());
		assertEquals(name,channelCtr.getChannel().getTopic());
	}
	
	@Test
	public void createChannelAdminCtr(){
		String name = "bla";
		ChannelAdminCtr channelAdminCtr = BFFactory.createChannelAdminCtr(name);
		assertNotNull(channelAdminCtr);
		assertEquals(name, channelAdminCtr.getTopic());
	}
	
	@Test
	public void createChannelAdminImpl(){
		String name = "bla";
		ChannelAdminImpl channelAdminImpl = BFFactory.createChannelAdminImpl(name);
		assertNotNull(channelAdminImpl);
		assertEquals(name, channelAdminImpl.getTopic());
	}

}
