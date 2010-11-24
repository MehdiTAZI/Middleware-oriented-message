package fr.esiag.mezzo_jpa;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="TOPIC")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	@Column(name="NAMETOPIC")
	public String nameTopic;
	
	@OneToMany  (mappedBy="topic")
	@Column(name="EVENTS")
	public Set<Event> events;
	
	public Topic(){}
	public Topic(String topic){
		nameTopic=topic;
		events = new TreeSet<Event>();
	}
	public void addEvent(Event event){
		events.add(event);
	}
}
