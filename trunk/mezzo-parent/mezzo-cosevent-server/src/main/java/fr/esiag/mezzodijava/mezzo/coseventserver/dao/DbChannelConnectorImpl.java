package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

public class DbChannelConnectorImpl extends JpaDAO<String, Channel> implements ChannelDAO,DbChannelConnector {
	
private static ChannelDAO instance = null;
	
	public static synchronized ChannelDAO getInstance() {
		if (instance == null) {
			instance = new DbChannelConnectorImpl();
		}
		return instance;
	}
}
