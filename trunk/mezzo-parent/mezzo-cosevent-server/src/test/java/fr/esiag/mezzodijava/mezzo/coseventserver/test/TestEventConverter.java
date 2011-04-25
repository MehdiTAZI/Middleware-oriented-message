package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.OctetSeqHelper;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.EventConvertor;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;

public class TestEventConverter {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTransformToEventModel() {
	Event e = EventFactory.createEventObject(1, 200, new Integer(3));
	EventConvertor ec = new EventConvertor();
	EventModel res = ec.transformToEventModel(e);
	Assert.assertEquals(res.getPriority(), 1);
	Assert.assertEquals(res.getTimetolive(), 200);
	Assert.assertTrue("ttl positif", res.getTimetolive() > 0);
	Assert.assertArrayEquals(OctetSeqHelper.extract(e.body.content),
		res.getData());
	Assert.assertEquals("type", "Integer", res.getType());
    }

    @Test
    public void testTransformToEventModelString() {
	Event e = EventFactory.createEventString(1, 200, "Salut");
	EventConvertor ec = new EventConvertor();
	EventModel res = ec.transformToEventModel(e);
	Assert.assertEquals(res.getPriority(), 1);
	Assert.assertEquals(res.getTimetolive(), 200);
	Assert.assertTrue("date positif", res.getCreationdate() > 0);
	Assert.assertEquals("Salut", new String(res.getData()));
	Assert.assertEquals("type", "String", res.getType());
    }

    @Test
    public void testTransformToEvent() throws IOException {
	ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	ObjectOutputStream oos = new ObjectOutputStream(baos);
	oos.writeObject(new Integer(55));		
	oos.close();
	byte [] byteInteger = baos.toByteArray();
	Date d = new Date();
	EventModel em = new EventModel(1, "Integer", 2, 3, byteInteger, d.getTime(), 200L);
	EventConvertor ec = new EventConvertor();
	Event e = ec.transformToEvent(em);
	//Assert.assertEquals(1, e.header.id);
	Assert.assertEquals(200, e.header.timetolive);
	Assert.assertEquals(2, e.header.code);
	Assert.assertEquals(d.getTime(), e.header.creationdate);
	Assert.assertEquals(3, e.header.priority);
	Assert.assertEquals("Integer", e.body.type);
	Assert.assertEquals(55, EventFactory.extractObject(Integer.class, e).intValue());
    }
    
    @Test
    public void testTransformToEventString() throws IOException {
	Date d = new Date();
	EventModel em = new EventModel(1, "String", 2, 3, "Salut".getBytes(), d.getTime(), 200L);
	EventConvertor ec = new EventConvertor();
	Event e = ec.transformToEvent(em);
	//Assert.assertEquals(1, e.header.id);
	Assert.assertEquals(200, e.header.timetolive);
	Assert.assertEquals(2, e.header.code);
	Assert.assertEquals(d.getTime(), e.header.creationdate);
	Assert.assertEquals(3, e.header.priority);
	Assert.assertEquals("String", e.body.type);
	Assert.assertEquals("Salut", e.body.content.extract_string());
    }
}
