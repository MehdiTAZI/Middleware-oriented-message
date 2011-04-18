package fr.esiag.mezzodijava.mezzo.libclient;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
/**
 * 
 * @author MEZZODIJAVA
 * Factory who can create event
 */
public class EventFactory {
	/**
	 * Create an event with a String content
	 * @param priority
	 * @param timetolive
	 * @param content
	 * @return an event containing a String content
	 */
	public static Event createEventString(int priority,long timetolive,String content ){
		Random r1= new Random();
		long code = r1.nextLong()*100;
		Header header = new Header(code, priority, new Date().getTime(), timetolive);
		Any any = ORB.init().create_any();
		any.insert_string(content);
		Body body = new Body(any,"String");		
		return new Event(header, body);
	}
	/**
	 * Create an event with a serializable content
	 * @param priority
	 * @param timetolive
	 * @param content
	 * @param type
	 * @return an event containing a serializable content
	 */
	public static Event createEventObject(int priority,long timetolive,Serializable content,String type){
		
		Random r1= new Random();
		long code = r1.nextLong()*100;
		Header header = new Header(code, priority, new Date().getTime(), timetolive);
		Any any = ORB.init().create_any();
		any.insert_Value(content);
		Body body = new Body(any,type);
		return new Event(header, body);
		
	}
	

}
