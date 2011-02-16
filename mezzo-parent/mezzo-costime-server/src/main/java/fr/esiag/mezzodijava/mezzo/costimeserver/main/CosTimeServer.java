package fr.esiag.mezzodijava.mezzo.costimeserver.main;

import fr.esiag.mezzodijava.mezzo.costimeserver.publisher.TimeServicePublisher;

public class CosTimeServer {

	public CosTimeServer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TimeServicePublisher publisher=new TimeServicePublisher();
		
		//publisher.publish("cosTime", timeService, orb)
	}

}
