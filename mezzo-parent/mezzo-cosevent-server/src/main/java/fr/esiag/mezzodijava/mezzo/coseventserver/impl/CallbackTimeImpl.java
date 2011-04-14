package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;

public class CallbackTimeImpl implements SynchronizableOperations {

    final static Logger log = LoggerFactory.getLogger(CallbackTimeImpl.class);
    private long delta;

    
/**
 *  @return the date in long format
 */
    @Override
    public long date() {

	return CosEventServer.getDelta();
    }
/**
 * @return the delta associated to the actual time and the time in argument
 */
    @Override
    public void date(long arg) {

	delta = arg - new Date().getTime();
	log.trace(" Time synchronisé " + delta);
	CosEventServer.setDelta(delta);

    }

}
