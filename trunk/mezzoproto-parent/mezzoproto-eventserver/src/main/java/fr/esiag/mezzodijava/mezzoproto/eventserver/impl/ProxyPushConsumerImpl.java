package fr.esiag.mezzodijava.mezzoproto.eventserver.impl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.Channel;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumerHelper;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumerPOA;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushConsumer;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.PushSupplier;

public class ProxyPushConsumerImpl extends ProxyPushConsumerPOA {

	private Channel canal;
	private PushSupplier ps;
	private PushConsumer pc;
	private ORB orb;

	public ProxyPushConsumerImpl(String topic, ORB orb) {

		try {

			this.orb = orb;
			NamingContextExt nc = NamingContextExtHelper.narrow(orb
					.resolve_initial_references("NameService"));
			canal = ChannelHelper.narrow(nc.resolve_str(topic));
		} catch (Exception e) {
			System.out.println("erreur : " + e.getMessage());
		}
	}

	@Override
	public void connect(PushSupplier ps) {

		this.ps = ps;
		try {
			POA poa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			poa.the_POAManager().activate();

			canal.add_consumer(ProxyPushConsumerHelper.narrow(poa
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
			canal.remove_consumer(ProxyPushConsumerHelper.narrow(poa
					.servant_to_reference(this)));

		} catch (Exception e) {
			System.out.println("erreur : " + e.getMessage());
		}

	}

	public void push(Event event) {
		ps.receive(event);
		canal.add_event(event);

	}

	@Override
	public PushSupplier getSupplier() {
		return ps;
	}

}
