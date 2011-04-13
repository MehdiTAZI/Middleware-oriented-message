package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;

import org.omg.CORBA.ORB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.impl.TimeServiceImpl;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;
import fr.esiag.mezzodijava.mezzo.costimeserver.publisher.TimeServicePublisher;

/**
 * Class CosTimeServer
 * 
 * Main class for the Mezzo di Java's COS Time Server
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 */
public class CosTimeServer {

    {
	initLogging();
    }
    
    final static Logger log = LoggerFactory.getLogger(CosTimeServer.class);

    public CosTimeServer(String[] args) {
	Properties props = new Properties();
	try {
	    props.load(this.getClass().getClassLoader()
		    .getResourceAsStream("eventserver.properties"));
	} catch (IOException e) {
		log.error("Properties loading error",e);

	}
	TimeServiceCtr ctr = new TimeServiceCtr(new TimeServiceModel());
	TimeServiceImpl timeService = new TimeServiceImpl(ctr);
	ORB orb = ORB.init(args, props);
	String timeServerName = args[0];
	long timeServerLifeSpan = Long.parseLong(args[1]);
	TimeServicePublisher.publish(timeServerName, timeService, orb, timeServerLifeSpan);
	log.info("Mezzo COS Time Server \" {} \" is running...", timeServerName);
	log.trace("Mezzo COS Time Server \" {} \" is running...", timeServerName);
	orb.run();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	//initLogging();
	new CosTimeServer(args);
    }

    public static void initLogging() {
	try {
	    LogManager.getLogManager().readConfiguration(
		    CosTimeServer.class
			    .getResourceAsStream("/mezzolog.properties"));
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
