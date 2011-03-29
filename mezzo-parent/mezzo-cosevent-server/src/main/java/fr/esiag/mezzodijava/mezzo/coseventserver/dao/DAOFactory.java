package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

public abstract class DAOFactory {

    private static ChannelDAO channelDAOinstance = null;

    public static synchronized ChannelDAO getChannelDAO() {
	if (channelDAOinstance == null) {
	    channelDAOinstance = new ChannelDAOImpl();
	}
	return channelDAOinstance;
    }
}
