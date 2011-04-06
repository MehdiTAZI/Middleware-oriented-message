package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.costime.SynchronizableOperations;

public class CallbackTimeImpl implements SynchronizableOperations {

    final static Logger log = LoggerFactory.getLogger(CallbackTimeImpl.class);
    private long delta;

    @Override
    public long date() {

	return CosEventServer.getDelta();
    }

    @Override
    public void date(long arg) {

	delta = arg - new Date().getTime();
	log.trace(" Time synchronisé " + delta);
	CosEventServer.setDelta(delta);

    }

}
