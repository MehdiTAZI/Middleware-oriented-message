package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name = "ChannelModel")
@Table(name = "ChannelModel")
public class ChannelModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "topic")
	private String topic;
	
	@ElementCollection  
	@CollectionTable(name = "channelevents")  
	@Column(name = "events")  
	List<EventModel> events;
	
	@ElementCollection  
	@CollectionTable(name = "consumerevents")  
	@Column(name = "events")  
	List<ConsumerEvents> consumerEvents;
	
	

}
