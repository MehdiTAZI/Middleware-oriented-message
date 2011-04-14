package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;

public class EventConvertor {
	private static Logger log = LoggerFactory.getLogger(EventConvertor.class);

	/**
	 * transform an Event to the Event Model in order to persist it
	 * @param e the event to persist
	 * @return the new event
	 */
    public EventModel transformToEventModel(Event e) {
	EventModel em = new EventModel();
	// Serialize to a byte array
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ObjectOutput out;
	try {
	    out = new ObjectOutputStream(bos);
	    if (e.body.type.equals("String")) {
		out.writeObject(e.body.content.extract_string());
	    } else {
		out.writeObject(e.body.content.extract_Value());
	    }
	    out.close();
	} catch (IOException e1) {
	    log.error("IOException",e1);
	}
	// Get the bytes of the serialized object
	byte[] buf = bos.toByteArray();
	log.trace("bufer size = "+buf.length);
	em.setCode(e.header.code);
	em.setCreationdate(e.header.creationdate);
	em.setData(buf);
	em.setPriority(e.header.priority);
	em.setTimetolive(e.header.timetolive);
	em.setType(e.body.type);
	return em;
    }
    
/**
 * transform and EventModel to the Event in order to modify it
 * @param em the eventModel to modify
 * @return the event
 */
    public Event transformToEvent(EventModel em) {
	try {
	    Event e;
	    // Deserialize from a byte array
	    ObjectInputStream in;
	    in = new ObjectInputStream(new ByteArrayInputStream(em.getData()));
	    Object val = (Serializable) in.readObject();
	    in.close();

	    if (em.getType().equals("String")) {
		e = EventFactory.createEventString(em.getPriority(),
			em.getTimetolive(), (String) val);
	    } else {
		e = EventFactory.createEventObject(em.getPriority(),
			em.getTimetolive(), (Serializable) val, em.getType());
	    }
	    e.header.code = em.getCode();
	    e.header.creationdate = em.getCreationdate();

	    return e;
	} catch (Exception e1) {
		log.error("Error in transformToEvent",e1);
		throw new RuntimeException("error in transformToEvent", e1);
	}
    }
}
