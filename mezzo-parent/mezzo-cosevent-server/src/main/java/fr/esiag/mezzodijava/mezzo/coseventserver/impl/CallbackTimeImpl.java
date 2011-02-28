package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.Date;

import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;


public class CallbackTimeImpl implements SynchronizableOperations{

	private long delta;
	
	@Override
	public long date() {
		
		return CosEventServer.getDelta();
	}

	@Override
	public void date(long arg) {
		
		delta=arg - new Date().getTime();
		System.out.println(" Time synchronisé "+delta);
		CosEventServer.setDelta(delta);
		
		
	}

}
