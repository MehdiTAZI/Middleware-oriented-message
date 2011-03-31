package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;

public class EventModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int priority;

    private byte[] data;

    private long code;

    private long creationdate;

    private long timetolive;

    private String type;

    public EventModel() {
	super();
    }

    public EventModel(int id, String type, long code, int priority,
	    byte[] data, long creationdate, long timetolive) {
	super();
	this.id = id;
	this.priority = priority;
	this.data = data;
	this.code = code;
	this.creationdate = creationdate;
	this.timetolive = timetolive;
	this.type = type;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getPriority() {
	return priority;
    }

    public void setPriority(int priority) {
	this.priority = priority;
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public long getCode() {
	return code;
    }

    public void setCode(long code) {
	this.code = code;
    }

    public long getCreationdate() {
	return creationdate;
    }

    public void setCreationdate(long creationdate) {
	this.creationdate = creationdate;
    }

    public long getTimetolive() {
	return timetolive;
    }

    public void setTimetolive(long timetolive) {
	this.timetolive = timetolive;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }
}
