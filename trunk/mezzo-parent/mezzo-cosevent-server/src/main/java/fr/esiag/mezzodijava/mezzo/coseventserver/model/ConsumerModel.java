package fr.esiag.mezzodijava.mezzo.coseventserver.model;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name = "CONSUMER")
public class ConsumerModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Channel channel;

    @Column(name = "idConsumer")
    private String idConsumer;

    // @ElementCollection
    // @CollectionTable(name = "channelevents")
    // @Column(name = "events")
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @Sort(type = SortType.COMPARATOR, comparator = PriorityEventModelComparator.class)
    private SortedSet<EventModel> eventsInQueue = Collections
	    .synchronizedSortedSet(new TreeSet<EventModel>());
    @Transient
    private SortedSet<EventModel> syncEventsInQueue = Collections
	    .synchronizedSortedSet(new TreeSet<EventModel>());

    @PostLoad
    public void synchronizeCollection() {
	syncEventsInQueue = Collections.synchronizedSortedSet(eventsInQueue);
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getIdConsumer() {
	return idConsumer;
    }

    public void setIdConsumer(String idConsumer) {
	this.idConsumer = idConsumer;
    }

    public SortedSet<EventModel> getEvents() {
	return syncEventsInQueue;
    }

    public void setEvents(SortedSet<EventModel> events) {
	this.syncEventsInQueue = events;
    }
}
