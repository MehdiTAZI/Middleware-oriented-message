package fr.esiag.mezzodijava.mezzo.costimeserver.ctr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.costime.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.NotRegisteredException;
import fr.esiag.mezzodijava.mezzo.costime.Synchronizable;
import fr.esiag.mezzodijava.mezzo.costimeserver.main.CosTimeServer;
import fr.esiag.mezzodijava.mezzo.costimeserver.model.TimeServiceModel;
import fr.esiag.mezzodijava.mezzo.servercommons.ThreadPool;

/**
 * TimeServiceCtr : To interact with the model
 * 
 * UC n°06: US18 (+ children)
 * 
 * @author Mezzo-Team
 * 
 */

public class TimeServiceCtr {

    private static Logger log = LoggerFactory.getLogger(TimeServiceCtr.class);

    private TimeServiceModel model;

    private ThreadPool threadPool = new ThreadPool(
	    Integer.valueOf(CosTimeServer.properties
		    .getProperty("timeserver.maxconnection")));

    /**
     * Get the model
     * 
     * @return TimeServiceModel
     * 
     */
    public TimeServiceModel getModel() {
	return model;
    }

    /**
     * Constructor
     * 
     * @param model
     *            the TimeServiceModel
     */
    public TimeServiceCtr(TimeServiceModel model) {
	this.model = model;
    }

    /**
     * Subscribe a component to the channel.
     * 
     * The server will push the time to this component.
     * 
     * @param component
     *            a component of type Synchronizable
     * @param timeSpan
     *            desirated refresh time (cannot be below 200 ms)
     * @throws AlreadyRegisteredException
     *             If already present in the list.
     */
    public final void subscribe(Synchronizable cc, long timeSpan)
	    throws AlreadyRegisteredException {

	if (!model.getComponentSubscribed().add(cc)) {
	    throw new AlreadyRegisteredException();
	}
	threadPool.runTask(new ThreadTime(cc, model, timeSpan));
	log.info("Component subscribed : " + cc);
    }

    /**
     * Unsubscribe a component from the channel.
     * 
     * remove the component from the list
     * 
     * @param component
     *            a component of type Synchronizable
     * @throws NotRegisteredException
     *             If the component is not registered.
     */
    public void unsubscribe(Synchronizable cc) throws NotRegisteredException {
	if (!model.getComponentSubscribed().contains(cc)) {
	    throw new NotRegisteredException();
	}
	model.getComponentSubscribed().remove(cc);
	log.info("Component unsubscribed : " + cc);
    }

    /**
     * Pull time.
     * 
     * @return current time in millis.
     */
    public long getTime() {
	return 0;
    }
}
