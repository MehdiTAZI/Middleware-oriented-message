package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

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

    // /**
    // * Test method for {@link
    // fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#getEvents()}.
    // */
    // @Test
    // public void testGetEvents() {
    //
    // Event testevent = new Event();
    // List<Event> testObject = new ArrayList<Event>();
    // assertTrue(testObject.isEmpty());
    // assertTrue(ch.getEvents().isEmpty());
    // testObject.add(testevent);
    // ch.addEvents(testevent);
    // assertTrue(testObject.contains(testevent));
    // assertTrue(ch.getEvents().contains(testevent));
    // assertEquals(testObject,ch.getEvents());
    // }
    //
    // /**
    // * Test method for {@link
    // fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#setEvents(java.util.List)}.
    // */
    // @Test
    // public void testSetEvents() {
    //
    // Event testevent = new Event();
    // Vector<Event> testObject = new Vector<Event>();
    // assertTrue(testObject.isEmpty());
    // assertTrue(ch.getEvents().isEmpty());
    // testObject.add(testevent);
    // ch.setEvents(testObject);
    // assertTrue(testObject.contains(testevent));
    // assertTrue(ch.getEvents().contains(testevent));
    // assertEquals(testObject,ch.getEvents());
    // }
    //
    // /**
    // * Test method for {@link
    // fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel#addEvents(fr.esiag.mezzodijava.mezzo.cosevent.Event)}.
    // */
    // @Test
    // public void testAddEvents() {
    //
    // Event testevent = new Event();
    // List<Event> testObject = new ArrayList<Event>();
    // assertTrue(testObject.isEmpty());
    // assertTrue(ch.getEvents().isEmpty());
    // testObject.add(testevent);
    // ch.addEvents(testevent);
    // assertTrue(testObject.contains(testevent));
    // assertTrue(ch.getEvents().contains(testevent));
    // assertEquals(testObject,ch.getEvents());
    // }

}
