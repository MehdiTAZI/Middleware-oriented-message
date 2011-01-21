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

	private ORB orb;
	private ChannelCtr channelCtr;
	private String channel;

	// Constructor
	public ChannelAdminCtr(String channel) {
		this.channel = channel;
		this.orb = BFFactory.createOrb(null, null);
		this.channelCtr = BFFactory.createChannelCtr(channel);
	}

	public String getTopic() {
		return channel;
	}

	public ProxyForPushSupplier createProxyForPushSupplier() {
		ProxyForPushSupplier ppc = null;

		try {
			ProxyForPushSupplierImpl proxy = null;
			proxy = new ProxyForPushSupplierImpl(channel);
			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
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

}
