package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.ChannelModel;

public class DbChannelConnectorImpl extends JpaDAO<String, ChannelModel> implements ChannelDAO,DbChannelConnector {
	
private static ChannelDAO instance = null;
	
	public static synchronized ChannelDAO getInstance() {
		if (instance == null) {
			instance = new DbChannelConnectorImpl();
		}
		return instance;
	}
}
