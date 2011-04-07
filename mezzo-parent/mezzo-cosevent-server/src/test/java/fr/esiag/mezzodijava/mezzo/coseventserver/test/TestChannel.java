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

}
