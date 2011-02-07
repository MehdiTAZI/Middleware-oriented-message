package fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

@Entity (name = "Events")
@Table(name = "EVENTS")
//@IdClass(EventInfoPK.class)
public class EventInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int max_pression = 155;
	private static final int max_temperature = 323;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "time")
	private Long time;
	
	//fauderais choisir une autre unité ! ou on aura un nombre plus petit
	private static final double max_radioactivite = 1000.0 ;
	
	@Column(name = "code")
	private int code;
	@Column(name = "type")
	private String type;
	
	
	@Column(name = "data")
	private String data;
	
	@Transient
	private StringTokenizer  st;
	
	public EventInfo(Event e)
	{	
		this.time = e.timestamp;
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
	

	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}


	public boolean isAlerte()
	{
		//return (this.code>900);
		
		if ("pression".equalsIgnoreCase(this.type) )
		{
			int value = Integer.parseInt(this.data);	
			if (value>max_pression+5)
				return true;
		}
		else if ("temperature".equalsIgnoreCase(this.type) )
		{
			int value = Integer.parseInt(this.data);	
			if (value>max_temperature+5)
				return true;
		}
		else if ("radioactivite".equalsIgnoreCase(this.type) )
		{
			double value = Double.parseDouble(this.data);	
			if (value>max_temperature+100)
				return true;
		}
		return false;
	}

}
