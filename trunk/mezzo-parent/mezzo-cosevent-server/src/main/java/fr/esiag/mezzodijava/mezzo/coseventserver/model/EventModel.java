package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;

/**
 * 
 * @author MEZZODIJAVA
 * Class used for persist
 *
 */
public class EventModel {

    private static final long serialVersionUID = 1L;

    private int id;

    private int priority;

    private byte[] data;

    private long code;

    private long creationdate;

    private long timetolive;

    private String type;

    /**
     * Constructor
     */
    public EventModel() {
	super();
    }
/**
 * Contructor
 * @param id
 * @param type
 * @param code
 * @param priority
 * @param data
 * @param creationdate
 * @param timetolive
 */
    public EventModel(int id, String type, long code, int priority,
	    byte[] data, long creationdate, long timetolive) {
	super();
	this.id = id;
	this.priority = priority;
	this.data = data.clone();
	this.code = code;
	this.creationdate = creationdate;
	this.timetolive = timetolive;
	this.type = type;
    }

    /**
     * Getter of the Id
     * @return
     */
    public int getId() {
	return id;
    }

    /**
     * Set the Id
     * @param id
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Getter of the Priority
     * @return
     */
    public int getPriority() {
	return priority;
    }

    /**
     * Set the Priority
     * @param priority
     */
    public void setPriority(int priority) {
	this.priority = priority;
    }

    /**
     * Getter of the data
     * @return
     */
    public byte[] getData() {
	return data;
    }

    /**
     * Set the data
     * @param data
     */
    public void setData(byte[] data) {
	this.data = data.clone();
    }

    /**
     * Getter of svuid
     * @return
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    /**
     * Getter of the code
     * @return
     */
    public long getCode() {
	return code;
    }

    /**
     * Set the code
     * @param code
     */
    public void setCode(long code) {
	this.code = code;
    }

    /**
     * Getter of the creationDate
     * @return
     */
    public long getCreationdate() {
	return creationdate;
    }

    /**
     * Set the creationDate
     * @param creationdate
     */
    public void setCreationdate(long creationdate) {
	this.creationdate = creationdate;
    }

    /**
     * Getter of the TimeToLive
     * @return
     */
    public long getTimetolive() {
	return timetolive;
    }

    /**
     * Set the TimeToLive
     * @param timetolive
     */
    public void setTimetolive(long timetolive) {
	this.timetolive = timetolive;
    }

    /**
     * Getter of the type
     * @return
     */
    public String getType() {
	return type;
    }

    /**
     * Set the type
     * @param type
     */
    public void setType(String type) {
	this.type = type;
    }
}
