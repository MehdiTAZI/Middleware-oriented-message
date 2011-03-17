package fr.esiag.mezzodijava.mezzo.libclient;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;

public class EventFactory {
	public static Event createEventString(int priority,long timetolive,String content ){
		Random r1= new Random();
		long code = r1.nextLong();
		Header h1 = new Header(code, priority, new Date().getTime(), timetolive);
		Any any = ORB.init().create_any();
		any.insert_Value(content);
		Body b1 = new Body(any,"String");
		return new Event(h1, b1);
	}
	public static Event createEventObject(int priority,long timetolive,Serializable content ){
		Random r1= new Random();
		long code = r1.nextLong();
		Header h1 = new Header(code, priority, new Date().getTime(), timetolive);
		Any any = ORB.init().create_any();
		any.insert_Value(content);
		Body b1 = new Body(any,content.getClass().toString());
		return new Event(h1, b1);
		
	}
	

}
