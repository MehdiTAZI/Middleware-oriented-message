package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import java.io.IOException;
import java.util.Properties;

import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.costimeserver.ctr.TimeServiceCtr;
import fr.esiag.mezzodijava.mezzo.costimeserver.impl.TimeServiceImpl;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;
import fr.esiag.mezzodijava.mezzo.costimeserver.publisher.TimeServicePublisher;

public class CosTimeServer {

	public CosTimeServer(String[] args) {
		Properties props = new Properties();
		try {
		    props.load(this.getClass().getClassLoader()
			    .getResourceAsStream("eventserver.properties"));
		} catch (IOException e) {
		    // TODO log here
		    
		}
		TimeServicePublisher publisher=new TimeServicePublisher();
		TimeServiceCtr ctr=new TimeServiceCtr(new TimeServiceModel());
		TimeServiceImpl timeService=new TimeServiceImpl(ctr);
		ORB orb = ORB.init(args, props);
	    String timeServerName = args[0];
		long timeServerLifeSpan =  Long.parseLong(args[1]);
		publisher.publish(timeServerName, timeService, orb,timeServerLifeSpan);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CosTimeServer(args);
	}

}
