package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;

import org.omg.CORBA.ORB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.commons.ConfMgr;
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

    /**
     * Configuration properties
     */
    public static Properties properties;

    final static Logger log = LoggerFactory.getLogger(CosTimeServer.class);

    /**
     * 
     * @param args
     *            properties for the CosTimeServer
     */
    public CosTimeServer(String[] args) {
	TimeServiceCtr ctr = new TimeServiceCtr(new TimeServiceModel());
	TimeServiceImpl timeService = new TimeServiceImpl(ctr);
	ORB orb = ORB.init(args, properties);
	String timeServerName;
	if (args.length == 1) {
	    timeServerName = args[0];
	} else {
	    timeServerName = properties.getProperty("timeserver.name");
	}
	TimeServicePublisher.publish(timeServerName, timeService, orb);
	log.info("Mezzo COS Time Server \" {} \" is running...", timeServerName);
	log.trace("Mezzo COS Time Server \" {} \" is running...",
		timeServerName);
	orb.run();
    }

    /**
     * YAMain
     * 
     * @param args
     * 
     */
    public static void main(String[] args) {
	initConf();
	new CosTimeServer(args);
    }

    /**
     * Load properties files.
     */
    public static void initConf() {
	properties = ConfMgr.loadProperties("timeserver_default", "timeserver");
    }
}
