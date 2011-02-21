package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;

public class DbEventConnectorImpl extends JpaDAO<String, EventInfo> implements InjectorDAO,DbEventConnector {
	
private static InjectorDAO instance = null;
	
	public static synchronized InjectorDAO getInstance() {
		if (instance == null) {
			instance = new DbEventConnectorImpl();
		}
		return instance;
	}
	
	

}
