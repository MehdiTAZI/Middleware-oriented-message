package fr.esiag.mezzo_jpa;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="EVENT")
public class Event {
	@Id
	public int id;
	
	@Column(name="MESSAGE")
	public String message;
	
	@Column(name="TOPIC")
	public String topic;
	
	public Event(){}
	public Event(String topic, String message){
		this.topic = topic;
		this.message = message;
	}
}
