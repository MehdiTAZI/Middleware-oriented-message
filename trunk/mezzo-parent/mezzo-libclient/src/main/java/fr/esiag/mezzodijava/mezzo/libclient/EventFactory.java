package fr.esiag.mezzodijava.mezzo.libclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

/**
 * 
 * @author MEZZODIJAVA Factory who can create event
 */
public final class EventFactory {

    private static Logger log = LoggerFactory.getLogger(EventFactory.class);

    /**
     * Create an event with a String content
     * 
     * @param priority
     * @param timetolive
     * @param content
     * @return an event containing a String content
     */
    public static Event createEventString(int priority, long timetolive,
	    String content) {
	Random r1 = new Random();
	long code = r1.nextLong() * 100;
	Header header = new Header(code, priority, new Date().getTime(),
		timetolive);
	Any any = ORB.init().create_any();
	any.insert_string(content);
	Body body = new Body(any, "String");
	return new Event(header, body);
    }

    /**
     * Create an event with a serializable content
     * 
     * @param priority
     * @param timetolive
     * @param content
     * @return an event containing a serializable content
     */
    public static Event createEventObject(int priority, long timetolive,
	    Serializable content) {

	Random r1 = new Random();
	long code = r1.nextLong() * 100;
	Header header = new Header(code, priority, new Date().getTime(),
		timetolive);
	Any any = ORB.init().create_any();
	// any.insert_Value(content);
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ObjectOutput out;
	try {
	    out = new ObjectOutputStream(bos);
	    out.writeObject(content);
	    out.close();
	} catch (IOException e1) {
	    log.error("IOException", e1);
	    throw new EventClientException(
		    "IOException in serialization of Object data", e1);
	}
	// inter of byte[]
	OctetSeqHelper.insert(any, bos.toByteArray());
	// any.insert_Value(bos.toByteArray());
	//Body body = new Body(any, content.getClass().getName());
	Body body = new Body(any, content.getClass().getSimpleName());
	return new Event(header, body);
    }

    /**
     * Extrace an Object of type <code>clazz</code> from byte array contained in
     * Event <code>e</code>.
     * 
     * @param <T>
     *            Object type to extract
     * @param clazz
     *            Object Class of type <code>T</code>
     * @param e
     *            Event
     * @return object of class T contained in event
     */
    public static <T extends Serializable> T extractObject(Class<T> clazz,
	    Event e) {

	// Deserialize from a byte array
	ObjectInputStream in;
	T val;
	try {
	    in = new ObjectInputStream(new ByteArrayInputStream(
		    OctetSeqHelper.extract(e.body.content)));
	    val = (T) in.readObject();
	    in.close();
	} catch (IOException e1) {
	    log.error("Error in transformToEvent IO", e1);
	    throw new EventClientException("Error in transformToEvent IO", e1);
	} catch (ClassNotFoundException e1) {
	    log.error("Error in transformToEvent serialization ClassNotFound",
		    e1);
	    throw new EventClientException(
		    "Error in transformToEvent serialization ClassNotFound", e1);
	}
	return val;
    }

}
