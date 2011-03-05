package fr.esiag.mezzodijava.nuclear.systemstatemonitor.tools;

import java.io.Serializable;
import java.util.StringTokenizer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;

@Entity (name = "EventInfo")
@Table(name = "EventInfo")
//@IdClass(EventInfoPK.class)
public class EventInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int max_pression = 155;
	private static final int max_temperature = 323;
	private static final double max_radioactivite = 1000.0 ;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "timestamp")
	private long timestamp;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "code")
	private long code;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name= "date")
	private long date;
	
	@Column(name = "data")
	private String data;
	
	public EventInfo(Event e)
	{	
		StringTokenizer st= new StringTokenizer(e.body.content,"|");
		if(st.hasMoreElements())
			this.type = st.nextToken();
		else
			System.err.println("miss-formed Event Structure : can't find type element");
		
		if(st.hasMoreElements())
			this.data = st.nextToken();
		else
			System.err.println("miss-formed Event Structure : can't find data element");
		
		this.priority = e.header.priority;
		this.code = e.header.code;
		this.date = e.header.creationdate;
		this.timestamp = e.header.timetolive;
	}

	public boolean isAlerte()
	{
		if ("pression".equalsIgnoreCase(this.type) )
		{
			int value = Integer.parseInt(this.data);
			if (value>max_pression)
				return true;
		}
		else if ("temperature".equalsIgnoreCase(this.type) )
		{
			int value = Integer.parseInt(this.data);	
			if (value>max_temperature)
				return true;
		}
		else if ("radioactivite".equalsIgnoreCase(this.type) )
		{
			double value = Double.parseDouble(this.data);	
			if (value>max_radioactivite)
				return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
