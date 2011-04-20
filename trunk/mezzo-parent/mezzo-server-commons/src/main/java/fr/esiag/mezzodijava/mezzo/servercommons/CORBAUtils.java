package fr.esiag.mezzodijava.mezzo.servercommons;

import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides CORBA useful tools.
 * 
 * @author Mezzo-Team
 */
public class CORBAUtils {

    private static  Logger log = LoggerFactory.getLogger(CORBAUtils.class);
    
    /**
     * Try to create a subcontext of name <code>context</code> in the context <code>nc</code>.
     * 
     * @param nc Root context
     * @param context name to create 
     * @throws InvalidName Forgiven name
     * @throws NotFound Root context not found
     * @throws CannotProceed Cannot Proceed
     */
    public static void createContext(NamingContextExt nc, String context) throws InvalidName, NotFound, CannotProceed{
	NameComponent[] nameContext = nc.to_name(context);
	    try {
		nc.bind_new_context(nameContext);
		log.debug("context {} bound",context);
	    } catch (AlreadyBound e) {
		log.debug("context {} already bound, using it",context);
	    }
    }
}
