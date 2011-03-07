package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

/**
 * Classe ChannelAdminCtr
 *
 * For Proxy creation : create, store in POA and return the Proxy
 *
 * UC n°: US14,US15 (+US children)
 *
 * @author Mezzo-Team
 *
 */

public class ChannelAdminCtr {
 
    private String channel;
    private ChannelCtr channelCtr;
    private ORB orb;

    /**
     * Build a Channel Admin Controler associated with a channelCtr.
     *
     * @param topic
     *            Channel topic.
     */
    public ChannelAdminCtr(String topic) {
	this.channel = topic;
	this.orb = BFFactory.createOrb(null, null);
	this.channelCtr = BFFactory.createChannelCtr(topic);
    }

    /**
     * Create the Proxy For PUSH Consumer and serve it with te CORBA POA.
     *
     * @return A Corba Object.
     */
    public ProxyForPushConsumer createProxyForPushConsumer() {
	ProxyForPushConsumer pps = null;
	try {
	    ProxyForPushConsumerImpl proxy = null;
	    proxy = new ProxyForPushConsumerImpl(channel);
	    POA poa = POAHelper.narrow(orb
		    .resolve_initial_references("RootPOA"));
	    poa.the_POAManager().activate();

	    pps = ProxyForPushConsumerHelper
		    .narrow(poa
			    .servant_to_reference(new ProxyForPushConsumerPOATie(
				    proxy)));
	} catch (org.omg.CORBA.UserException e) {
	    // TODO Log Corba error
	    e.printStackTrace();
	}
	return pps;
    }

    /**
     * Create the Proxy For PUSH Supplier and serve it with te CORBA POA.
     *
     * @return A Corba Object.
     */
    public ProxyForPushSupplier createProxyForPushSupplier() {
	ProxyForPushSupplier ppc = null;

	try {
	    ProxyForPushSupplierImpl proxy = null;
	    proxy = new ProxyForPushSupplierImpl(channel);
	    POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    poa.the_POAManager().activate();
	    ppc = ProxyForPushSupplierHelper
		    .narrow(poa
			    .servant_to_reference(new ProxyForPushSupplierPOATie(
				    proxy)));

	} catch (org.omg.CORBA.UserException e) {
	    // TODO Log Corba error
	    e.printStackTrace();
	}
	return ppc;
    }

    /**
     * Get the Channel topic.
     *
     * @return topic
     */
    public String getTopic() {
	return channel;
    }

}
