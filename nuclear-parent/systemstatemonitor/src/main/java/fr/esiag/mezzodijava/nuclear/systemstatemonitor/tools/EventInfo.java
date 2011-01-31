package fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools;

import java.io.Serializable;
import java.util.StringTokenizer;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

@Entity (name = "Events")
@Table(name = "EVENTS")
@IdClass(EventInfoPK.class)
public class EventInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private int code;
	@Id
	private String type;
	
	
	@Column(name = "data")
	private String data;
	
	@Transient
	private StringTokenizer  st;
	
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
	

	public void setCode(int code) {
		this.code = code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isAlerte()
	{
		return (this.code>900);
	}

}
