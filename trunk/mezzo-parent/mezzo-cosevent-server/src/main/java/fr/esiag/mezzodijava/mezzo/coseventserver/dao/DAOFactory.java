package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

public abstract class DAOFactory {

    private static JdbcDAO jdbcDAOinstance = null;

    public static synchronized JdbcDAO getJdbcDAO() {
	/*if (jdbcDAOinstance == null) {
		jdbcDAOinstance = new JdbcDAOImpl();
	}*/
	return jdbcDAOinstance;
    }
}
