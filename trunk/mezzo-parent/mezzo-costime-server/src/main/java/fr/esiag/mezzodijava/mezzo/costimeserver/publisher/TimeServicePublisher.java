package fr.esiag.mezzodijava.mezzo.costimeserver.publisher;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import fr.esiag.mezzodijava.mezzo.costime.TimeServicePOATie;
import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.ThreadTime;
import fr.esiag.mezzodijava.mezzo.costimeserver.impl.TimeServiceImpl;

/**
 * TimeServicePublisher : to publish and start the thread for time service
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author cosTime-Team
 * 
 */
public class TimeServicePublisher {
    private static Thread thread;

    /**
     * Publish with CORBA ORB <code>orb</code> a TimeService
     * <code>timeService</code> and register it on the name service with the
     * name <code>name</code>.  
     * 
     * @param name name of this time service in the Name Service
     * @param timeService implementation of TimeService
     * @param orb CORBA ORB
     * @param timeSpan synchronization refresh time.
     */
    public static void publish(String name, TimeServiceImpl timeService,
	    ORB orb, long timeSpan) {
	ThreadTime tt = new ThreadTime(timeService.getCtr().getModel(),
		timeSpan);
	thread = new Thread(tt);
	thread.start();

	try {
	    POA poa = POAHelper.narrow(orb
		    .resolve_initial_references("RootPOA"));
	    poa.the_POAManager().activate();
	    NamingContextExt nc = NamingContextExtHelper.narrow(orb
		    .resolve_initial_references("NameService"));
	    nc.rebind(nc.to_name(name), poa
		    .servant_to_reference(new TimeServicePOATie(timeService)));
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Publish with CORBA ORB <code>orb</code> a TimeService
     * <code>timeService</code> and register it on the name service with the
     * name <code>name</code> a a default refresh time of 1s.
     * 
     * @param name name of this time service in the Name Service
     * @param timeService implementation of TimeService
     * @param orb CORBA ORB
     * */
    public static void publish(String name, TimeServiceImpl timeService, ORB orb) {
	publish(name, timeService, orb, 1000);
    }

}