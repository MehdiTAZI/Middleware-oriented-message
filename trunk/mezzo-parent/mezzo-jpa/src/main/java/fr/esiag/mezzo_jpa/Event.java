package fr.esiag.mezzo_jpa;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	@Column(name="MESSAGE")
	public String message;
	
	@ManyToOne
	@JoinColumn(name="TOPIC")
	public Topic topic;
	
	public Event(){}
	public Event(Topic topic, String message){
		this.topic = topic;
		this.message = message;
		topic.addEvent(this);
	}
}
