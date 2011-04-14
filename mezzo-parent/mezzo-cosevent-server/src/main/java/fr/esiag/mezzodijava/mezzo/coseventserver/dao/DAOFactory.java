package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

public abstract class DAOFactory {

    private static JdbcDAO jdbcDAOinstance = null;

    /**
     * Singleton access to the JdbcDAO
     * @return the jdbcDAO
     */
    public static synchronized JdbcDAO getJdbcDAO() {
	if (jdbcDAOinstance == null) {
		jdbcDAOinstance = new JdbcDOAImpl("test","test",System.getProperty("user.home")+System.getProperty("file.separator")+"dbcosevent"+EventServer.getInstance().getServerName());
	}
	return jdbcDAOinstance;
    }
}
