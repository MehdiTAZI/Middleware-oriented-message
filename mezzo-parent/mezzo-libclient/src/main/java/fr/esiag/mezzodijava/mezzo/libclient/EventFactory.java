package fr.esiag.mezzodijava.mezzo.libclient;

import java.util.Random;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;

public class EventFactory {
	public static Event createEventString(int priority,long timetolive,String content ){
		Random r1= new Random();
		Header h1 = new Header(code, priority, creationdate, timetolive);
		Body b1 = new Body(content);
		return new Event(h1, b1);
	}
	public static Event createEventObject(int priority,long timetolive,Object content ){
		return null;
		
	}
	

}
