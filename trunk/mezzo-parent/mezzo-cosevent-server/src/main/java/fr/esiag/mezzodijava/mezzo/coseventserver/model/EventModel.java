package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class EventModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "priority")
    private int priority;

    @Column(name = "date")
    private long date;

    @Column(name = "data")
    @Lob
    private byte[] data;

    public EventModel() {
	super();
    }

    public EventModel(int id, String type, long code, int priority, long date,
	    byte[] data) {
	super();
	this.id = id;
	this.priority = priority;
	this.date = date;
	this.data = data;
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

    public long getDate() {
	return date;
    }

    public void setDate(long date) {
	this.date = date;
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

}
