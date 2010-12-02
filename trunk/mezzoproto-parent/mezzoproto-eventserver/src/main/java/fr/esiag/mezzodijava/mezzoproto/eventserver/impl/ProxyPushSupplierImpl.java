package fr.esiag.mezzodijava.mezzoproto.eventserver.impl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.Channel;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplierHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplierPOA;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushConsumer;

public class ProxyPushSupplierImpl extends ProxyPushSupplierPOA {

	private Channel channel;
	private PushConsumer pc;
	private ORB orb;

	public ProxyPushSupplierImpl(String topic, ORB orb) {

		try {
			this.orb = orb;
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));
			channel = ChannelHelper.narrow(nc.resolve_str(topic));

		} catch (Exception e) {

		}
	}

	@Override
	public void connect(PushConsumer pc) {

		this.pc = pc;

		try {

			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			channel.add_supplier(ProxyPushSupplierHelper.narrow(poa
					.servant_to_reference(this)));

		} catch (Exception e) {
			System.out.println("erreur : " + e.getMessage());
		}
	}

	@Override
	public void disconnect() {
		try {

			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();
			channel.remove_supplier(ProxyPushSupplierHelper.narrow(poa
					.servant_to_reference(this)));

		} catch (Exception e) {
			System.out.println("erreur : " + e.getMessage());
		}

	}

	public void receive(Event event) {
		System.out.println("appel recieve");
		pc.push(event);

	}

	@Override
	public PushConsumer getConsumer() {

		return pc;
	}

}
