package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventServer;

/**
 * 
 * @author MEZZODIJAVA Abstract factory
 * 
 */
public abstract class DAOFactory {

    private static JdbcDAO jdbcDAOinstance = null;

    private DAOFactory() {

    }

    /**
     * Singleton access to the JdbcDAO
     * 
     * @return the jdbcDAO
     */
    public static synchronized JdbcDAO getJdbcDAO() {
	if (jdbcDAOinstance == null) {
	    String dbFile = CosEventServer.properties
		    .getProperty("eventserver.persistence.dbfile");
	    if (dbFile == null) {
		dbFile = System.getProperty("user.home")
			+ System.getProperty("file.separator") + "dbcosevent"
			+ EventServer.getInstance().getServerName();
	    }
	    jdbcDAOinstance = new JdbcDOAImpl("test", "test", dbFile);
	}
	return jdbcDAOinstance;
    }
}
