package fr.esiag.mezzodijava.mezzo.coseventserver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;

public class AbstractProxyImpl {
	
	private static Logger log = LoggerFactory.getLogger(AbstractProxyImpl.class);

    protected String idComponent;
    
    protected boolean connected;
    
    /**
     * The Channel Controller used by this facade
     */
    protected ChannelCtr channelCtr;

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

    /**
     * 
     * @return IdComponent
     */
    public String getIdComponent() {
    	log.debug("Access to idComponent {} in AbstractProxyImpl",idComponent);
        return idComponent;
    }

    /**
     * 
     * @return channelCtr
     */
    public ChannelCtr getChannelCtr() {
        return channelCtr;
    }
}
