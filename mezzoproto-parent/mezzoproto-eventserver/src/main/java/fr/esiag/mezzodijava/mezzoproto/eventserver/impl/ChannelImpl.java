package fr.esiag.mezzodijava.mezzoproto.eventserver.impl;





import java.util.Vector;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelPOA;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushConsumer;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplier;
import fr.esiag.mezzodijava.mezzoproto.cosevent.model.EventString;
import fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.ChannelDAO;




public class ChannelImpl extends ChannelPOA{

	private Vector<ProxyPushSupplier> ppSuppliers; 
	private Vector<ProxyPushConsumer> ppConsumers;	
	private String topic;
	private int capacity;	
	private Vector<Event> events;
	private ChannelDAO dao;
	
	
	public ChannelImpl(String topic,int capacity) {
		
		this.capacity=capacity;
		this.topic=topic;
		ppConsumers=new Vector<ProxyPushConsumer>(capacity);
		ppSuppliers=new Vector<ProxyPushSupplier>(capacity);				
		events=new Vector<Event>(200);
		dao=ChannelDAO.getInstance();
	}

	/**
	 * Ajouter un consumers à la liste des consumers dans le canal.
	 * @param event un évènement (peut être String, Integer ou Double)
	 * @see fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelOperations#add_event(fr.esiag.mezzodijava.mezzoproto.CosEvent.Event)
	 */
	@Override
	public void add_consumer(ProxyPushConsumer ppc) {
		
		System.out.println("EventServer : Ajout d'un supplier PUSH");
		if(!ppConsumers.contains(ppc))
			ppConsumers.add(ppc);
		
	}

	/**
	 * Ajouter un évènement à la liste des évènement dans le canal.
	 * @param event un évènement (peut être String, Integer ou Double)
	 * @see fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelOperations#add_event(fr.esiag.mezzodijava.mezzoproto.CosEvent.Event)
	 */
	@Override
	public synchronized void add_event(Event event) {		
		//System.out.println("ADD_EVENt "+ event.message());
		System.out.println("EventServer : Ajout d'un evenement dans le canal " + topic + ": " + event.message());
		EventString es=new EventString(event.message());
		
		dao.persist(es);
		
		events.add(event);
		
		for (ProxyPushSupplier s:ppSuppliers) {	
			//System.out.println("APPEL ADD_EVENt");
			s.receive(event);
			
		}
		
	}
	
	/**
	 * Ajouter un supplier à la liste des supplier dans le canal.
	 * @param event un évènement (peut être String, Integer ou Double)
	 * @see fr.esiag.mezzodijava.mezzoproto.CosEvent.ChannelOperations#add_event(fr.esiag.mezzodijava.mezzoproto.CosEvent.Event)
	 */
	public void add_supplier(ProxyPushSupplier pps) {
		//System.out.println("ADD_CONSUMER");
		System.out.println("EventServer : Ajout d'un consumer PUSH");
		if(!ppSuppliers.contains(pps))
			ppSuppliers.add(pps);
		
	}
	
	public void remove_consumer(ProxyPushConsumer ppc) {
		ppConsumers.remove(ppc);
		
	}
	
	public void remove_supplier(ProxyPushSupplier pps) {
		ppSuppliers.remove(pps);
		
	}
	
}
