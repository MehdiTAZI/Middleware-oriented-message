package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.ChannelModel;

public interface DbChannelConnector{
	public boolean persist(ChannelModel c);//if the channel exists so update it ,else insert it;just call the persist method of JPA
}
