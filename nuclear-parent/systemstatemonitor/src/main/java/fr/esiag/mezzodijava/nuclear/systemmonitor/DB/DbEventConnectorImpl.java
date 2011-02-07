package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfo;
import fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools.EventInfoPK;

public class DbEventConnectorImpl extends JpaDAO<Integer, EventInfo> implements InjectorDAO,DbEventConnector {
	
private static InjectorDAO instance = null;
	
	public static synchronized InjectorDAO getInstance() {
		if (instance == null) {
			instance = new DbEventConnectorImpl();
		}
		return instance;
	}

}
