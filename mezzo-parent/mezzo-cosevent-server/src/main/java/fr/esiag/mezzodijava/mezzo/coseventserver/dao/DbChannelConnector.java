package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public interface DbChannelConnector{
	public boolean persist(Channel c);//if the channel exists so update it ,else insert it;just call the persist method of JPA
}
