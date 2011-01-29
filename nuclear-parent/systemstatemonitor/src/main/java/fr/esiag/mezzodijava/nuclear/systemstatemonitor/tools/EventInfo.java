package fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools;

import java.util.StringTokenizer;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

public class EventInfo {
	
	private int code;
	private String type;
	private String data;
	
	StringTokenizer  st;
	
	public EventInfo(Event e)
	{	
		st= new StringTokenizer(e.content,"/");
		if(st.hasMoreElements())
			this.code =Integer.parseInt(st.nextToken());
		else
			System.err.println("miss-formed Event Structure : can't find code element");
		
		
		if(st.hasMoreElements())
			this.type = st.nextToken();
		else
			System.err.println("miss-formed Event Structure : can't find type element");
		
		
		if(st.hasMoreElements())
			this.data = st.nextToken();
		else
			System.err.println("miss-formed Event Structure : can't find data element");
		
		if (this.code>999)
			System.err.println("miss-formed Event Structure : code value is to high ! Check the injectorSystem supplier configuration");
		
	}

	public int getCode() {
		return this.code;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getData() {
		return this.data;
	}

	public boolean isAlerte()
	{
		return (this.code>900);
	}

}
