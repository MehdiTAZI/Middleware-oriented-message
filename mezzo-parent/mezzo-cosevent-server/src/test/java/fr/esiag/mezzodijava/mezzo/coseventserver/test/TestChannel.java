package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;

import fr.esiag.mezzodijava.mezzo.cosevent.Body;
import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.cosevent.Header;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

public class TestChannel {

    Channel ch;
    String topic;
    int capacity;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.ch = new Channel("toto", 99);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.ch = null;
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#getTopic()}
     * .
     */
    @Test
    public void testGetTopic() {

	this.topic = "sensor";
	assertNotNull("topic n'est pas null", ch.getTopic());
	ch.setTopic(null);
	assertNull("topic n'est pas null", ch.getTopic());
	ch.setTopic(this.topic);
	assertNotNull("topic est null", ch.getTopic());
	assertEquals(this.topic, ch.getTopic());
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#setTopic(java.lang.String)}
     * .
     */
    @Test
    public void testSetTopic() {

	this.topic = "sensor";
	assertNotNull("topic n'est pas null", ch.getTopic());
	ch.setTopic(null);
	assertNull("topic n'est pas null", ch.getTopic());
	ch.setTopic(this.topic);
	assertNotNull("topic est null", ch.getTopic());
	assertEquals(this.topic, ch.getTopic());
	ch.setTopic("foot");
	assertEquals("foot", ch.getTopic());
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#getCapacity()}
     * .
     */
    @Test
    public void testGetCapacity() {

	this.capacity = 1000;
	assertEquals(99, ch.getCapacity());
	ch.setCapacity(-2);
	assertTrue("capacity est négative", ch.getCapacity() < 0);
	ch.setCapacity(this.capacity);
	assertTrue("capacity est négative", ch.getCapacity() > 0);
	assertEquals(this.capacity, ch.getCapacity());
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#setCapacity(int)}
     * .
     */
    @Test
    public void testSetCapacity() {

	this.capacity = 5;
	assertEquals(99, ch.getCapacity());
	ch.setCapacity(-2);
	assertTrue("capacity est négative", ch.getCapacity() < 0);
	ch.setCapacity(this.capacity);
	assertTrue("capacity est négative", ch.getCapacity() > 0);
	assertEquals(this.capacity, ch.getCapacity());
	ch.setCapacity(22);
	assertEquals(22, ch.getCapacity());
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#getQueueEvents()}
     * .
     */
    @Test
    public void testGetEvents() {

	Header header = new Header(Long.MAX_VALUE, 1, new Date().getTime(),
		Long.MAX_VALUE);
	ORB orb = ORB.init();
	Any any = orb.create_any();
	any.insert_string("Test");
	Body body = new Body(any, "String");

	Event testevent = new Event(header, body);
	EventModel em = new EventConvertor().transformToEventModel(testevent);
	assertTrue(ch.getEvents().isEmpty());
	ch.getEvents().add(em);
	assertTrue(ch.getEvents().contains(em));
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#setQueueEvents(java.util.PriorityQueue)}
     * .
     */
    @Test
    public void testSetEvents() {

	Header header = new Header(Long.MAX_VALUE, 1, new Date().getTime(),
		Long.MAX_VALUE);
	ORB orb = ORB.init();
	Any any = orb.create_any();
	any.insert_string("Test");
	Body body = new Body(any, "String");

	Event testevent = new Event(header, body);
	SortedSet<EventModel> queue = new TreeSet<EventModel>();
	EventModel em = new EventConvertor().transformToEventModel(testevent);
	queue.add(em);
	ch.setEvents(queue);
	assertEquals(queue, ch.getEvents());
    }

    /**
     * Test method for
     * {@link fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#addEvent(fr.esiag.mezzodijava.mezzo.cosevent.Event)}
     * .
     */
    @Test
    public void testAddEvents() {

	Header header = new Header(Long.MAX_VALUE, 1, new Date().getTime(),
		Long.MAX_VALUE);

	ORB orb = ORB.init();
	Any any = orb.create_any();
	any.insert_string("Test");
	Body body = new Body(any, "String");

	Event testevent = new Event(header, body);
	EventModel em = new EventConvertor().transformToEventModel(testevent);
	ch.addEvent(em);
	assertTrue(ch.getEvents().contains(em));
    }

}
