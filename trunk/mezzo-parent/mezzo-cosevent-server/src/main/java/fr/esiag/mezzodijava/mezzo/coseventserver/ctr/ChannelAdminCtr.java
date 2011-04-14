package fr.esiag.mezzodijava.mezzo.coseventserver.ctr;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumerHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplierHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPullSupplierPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumer;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushConsumerPOATie;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplier;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierHelper;
import fr.esiag.mezzodijava.mezzo.cosevent.ProxyForPushSupplierPOATie;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPullSupplierImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushSupplierImpl;

// NO MORE COMMENT TO DO AT 14/04/2010
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

	private static Logger log = LoggerFactory.getLogger(ChannelAdminCtr.class);
	private Map<ProxyForPushConsumer, byte[]> oidProxyForPushConsumerMap = Collections
			.synchronizedMap(new HashMap<ProxyForPushConsumer, byte[]>());
	private Map<ProxyForPushSupplier, byte[]> oidProxyForPushSupplierMap = Collections
			.synchronizedMap(new HashMap<ProxyForPushSupplier, byte[]>());
	private Map<ProxyForPullConsumer, byte[]> oidProxyForPullConsumerMap = Collections
			.synchronizedMap(new HashMap<ProxyForPullConsumer, byte[]>());
	private Map<ProxyForPullSupplier, byte[]> oidProxyForPullSupplierMap = Collections
			.synchronizedMap(new HashMap<ProxyForPullSupplier, byte[]>());
	private String channel;
	private ChannelCtr channelCtr;
	private ORB orb;
	private static POA poa;

	/**
	 * 
	 * @return Map containing proxyPushConsumer and an array of byte associated
	 */
	public Map<ProxyForPushConsumer, byte[]> getOidProxyForPushConsumerMap() {
		return oidProxyForPushConsumerMap;
	}

	/**
	 * 
	 * @return Map containing proxyPushSupplier and an array of byte associated
	 */
	public Map<ProxyForPushSupplier, byte[]> getOidProxyForPushSupplierMap() {
		return oidProxyForPushSupplierMap;
	}

	/**
	 * 
	 * @return Map containing proxyPullConsumer and an array of byte associated
	 */
	public Map<ProxyForPullConsumer, byte[]> getOidProxyForPullConsumerMap() {
		return oidProxyForPullConsumerMap;
	}

	/**
	 * 
	 * @return Map containing proxyPullSupplier and an array of byte associated
	 */
	public Map<ProxyForPullSupplier, byte[]> getOidProxyForPullSupplierMap() {
		return oidProxyForPullSupplierMap;
	}
	/**
	 * Build a Channel Admin Controler associated with a channelCtr.
	 * 
	 * @param topic
	 *            Channel topic.
	 */
	public ChannelAdminCtr(String topic) {
		log.trace("Creation of ChannelAdminCtr");
		this.channel = topic;
		this.orb = BFFactory.createOrb(null, null);
		this.channelCtr = BFFactory.createChannelCtr(topic);
	}

	
	private static synchronized POA getPOA() {
		log.trace("Access to POA");
		try {
			if (poa == null) {
				poa = POAHelper.narrow(BFFactory.getOrb()
						.resolve_initial_references("RootPOA"));
				poa.the_POAManager().activate();
			}

		} catch (InvalidName e) {
			log.error("Invalid reference", e);
		} catch (AdapterInactive e) {
			log.error("", e);
		}
		log.trace("POA accessed");
		return poa;
	}

	/**
	 * Create the Proxy For PUSH Consumer and serve it with the CORBA POA.
	 * 
	 * @return A Corba Object.
	 * @throws WrongPolicy
	 * @throws ServantNotActive
	 */
	public ProxyForPushConsumer createProxyForPushConsumer(String idComponent) {
		ProxyForPushConsumer ppc = null;

		log.trace("Creation of proxyPushConsumer");
		byte[] oid;
		try {
			oid = getPOA()
					.servant_to_id(
							new ProxyForPushConsumerPOATie(
									new ProxyForPushConsumerImpl(channel,
											idComponent)));
			ppc = ProxyForPushConsumerHelper.narrow(getPOA().id_to_reference(
					oid));
			oidProxyForPushConsumerMap.put(ppc, oid);
		} catch (ServantNotActive e) {
			log.error("Servant inactive", e);
		} catch (WrongPolicy e) {
			log.error("Wrong policy", e);
		} catch (ObjectNotActive e) {
			log.error("Not an active object", e);
		}
		log.trace("proxyPushConsumer created");
		return ppc;

	}

	/**
	 * Create the Proxy For PUSH Supplier and serve it with the CORBA POA.
	 * 
	 * @return A Corba Object.
	 * @throws WrongPolicy
	 * @throws ServantNotActive
	 */
	public ProxyForPushSupplier createProxyForPushSupplier(String idComponent) {
		ProxyForPushSupplier pps = null;
		log.trace("Creation of proxyPushSupplier");
		byte[] oid;
		try {
			oid = getPOA()
					.servant_to_id(
							new ProxyForPushSupplierPOATie(
									new ProxyForPushSupplierImpl(channel,
											idComponent)));
			pps = ProxyForPushSupplierHelper.narrow(getPOA().id_to_reference(
					oid));
			oidProxyForPushSupplierMap.put(pps, oid);
		} catch (ServantNotActive e) {
			log.error("Servant inactive", e);
		} catch (WrongPolicy e) {
			log.error("Wrong policy", e);
		} catch (ObjectNotActive e) {
			log.error("Not an active object", e);
		}
		log.trace("proxyPushSupplier created");
		return pps;
	}

	/**
	 * Create the Proxy For PULL Consumer and serve it with the CORBA POA.
	 * 
	 * @return A Corba Object.
	 * @throws WrongPolicy
	 * @throws ServantNotActive
	 */
	public ProxyForPullConsumer createProxyForPullConsumer(String idComponent) {
		ProxyForPullConsumer ppc = null;

		log.trace("Creation of proxyPullConsumer");
		byte[] oid;
		try {
			oid = getPOA()
					.servant_to_id(
							new ProxyForPullConsumerPOATie(
									new ProxyForPullConsumerImpl(channel,
											idComponent)));
			ppc = ProxyForPullConsumerHelper.narrow(getPOA().id_to_reference(
					oid));
			oidProxyForPullConsumerMap.put(ppc, oid);
		} catch (ServantNotActive e) {
			log.error("Servant inactive", e);
		} catch (WrongPolicy e) {
			log.error("Wrong policy", e);
		} catch (ObjectNotActive e) {
			log.error("Not an active object", e);
		}
		log.trace("proxyPullConsumer created");
		return ppc;
	}

	/**
	 * Create the Proxy For PULL Supplier and serve it with the CORBA POA.
	 * 
	 * @return A Corba Object.
	 * @throws WrongPolicy
	 * @throws ServantNotActive
	 */
	public ProxyForPullSupplier createProxyForPullSupplier(String idComponent) {
		ProxyForPullSupplier pps = null;
		log.trace("Creation of proxyPullSupplier");
		byte[] oid;
		try {
			oid = getPOA()
					.servant_to_id(
							new ProxyForPullSupplierPOATie(
									new ProxyForPullSupplierImpl(channel,
											idComponent)));
			pps = ProxyForPullSupplierHelper.narrow(getPOA().id_to_reference(
					oid));
			oidProxyForPullSupplierMap.put(pps, oid);
		} catch (ServantNotActive e) {
			log.error("Servant inactive", e);
		} catch (WrongPolicy e) {
			log.error("Wrong policy", e);
		} catch (ObjectNotActive e) {
			log.error("Not an active object", e);
		}
		log.trace("proxyPushSupplier created");
		return pps;
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
