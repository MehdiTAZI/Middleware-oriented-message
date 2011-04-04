package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class AbstractProxyImpl {
	
	private static Logger log = LoggerFactory.getLogger(AbstractProxyImpl.class);

    protected String idComponent;
    /**
     * The Channel Controller used by this facade
     */
    protected ChannelCtr channelCtr;

    protected boolean connected = false;

    /**
     * Build a ProxyForPushSupplier instance associated with the given topic and
     * build the underlying Channel Controller.
     * 
     * @param topic
     *            Channel Topic.
     */
    protected AbstractProxyImpl(String topic, String idComponent) {
    	log.debug("Instanciation of AbstractProxyImpl for {} with idComponent {}",topic, idComponent);
	this.idComponent = idComponent;
	channelCtr = BFFactory.createChannelCtr(topic);
    }

    public String getIdComponent() {
    	log.debug("Access to idComponent {} in AbstractProxyImpl",idComponent);
        return idComponent;
    }
}
