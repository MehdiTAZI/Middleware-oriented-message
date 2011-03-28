package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.omg.CORBA.Any;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

public class EventConvertor {
	
	public EventModel transformToEventModel(Event e){
		EventModel em = new EventModel();
		// Serialize to a byte array
	    ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
	    ObjectOutput out;
		try {
			out = new ObjectOutputStream(bos);
			Object object = e.body.content.extract_Object();
			out.writeObject(object);
			out.close();	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    // Get the bytes of the serialized object
	    byte[] buf = bos.toByteArray();
		
		em.setCode(e.header.code);
		em.setCreationdate(e.header.creationdate);
		em.setData(buf);
		em.setPriority(e.header.priority);
		em.setTimetolive(e.header.timetolive);
		em.setType(e.body.type);
		return em;
	}
	
	public Event transformToEvent(EventModel em){
		Event e = new Event();
		Any a = null;
		// Deserialize from a byte array
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(em.getData()));
			a = (Any)in.readObject();
			in.close();
	    
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    
		e.header.code = em.getCode();
		e.header.creationdate = em.getCreationdate();
		e.header.priority = em.getPriority();
		e.header.timetolive = em.getTimetolive();
		e.body.type = em.getType();
		e.body.content = a;
		
		return e;
	}

}
