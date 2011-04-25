package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

/**
 * 
 * @author MEZZODIJAVA Can convert an event to an eventModel which is used to
 *         persist event
 * 
 */
public class EventConvertor {
    private static Logger log = LoggerFactory.getLogger(EventConvertor.class);

    /**
     * transform an Event to the Event Model in order to persist it
     * 
     * @param e
     *            the event to persist
     * @return the new event
     */
    public EventModel transformToEventModel(Event e) {
	EventModel em = new EventModel();
	em.setCode(e.header.code);
	em.setCreationdate(e.header.creationdate);
	if (e.body.type.equals("String")) {
	    em.setData(e.body.content.extract_string().getBytes());
	} else {
	    em.setData(OctetSeqHelper.extract(e.body.content));
	}
	em.setPriority(e.header.priority);
	em.setTimetolive(e.header.timetolive);
	em.setType(e.body.type);
	return em;
    }

    /**
     * transform and EventModel to the Event in order to modify it
     * 
     * @param em
     *            the eventModel to modify
     * @return the event
     */
    public Event transformToEvent(EventModel em) {
	Header header = new Header(em.getCode(), em.getPriority(),
		em.getCreationdate(), em.getTimetolive());
	Any any = ORB.init().create_any();
	Body body = new Body(any, em.getType());
	if (em.getType().equals("String")) {
	    body.content.insert_string(new String(em.getData()));
	} else {
	    OctetSeqHelper.insert(body.content, em.getData());
	}
	return new Event(header, body);
    }
}
