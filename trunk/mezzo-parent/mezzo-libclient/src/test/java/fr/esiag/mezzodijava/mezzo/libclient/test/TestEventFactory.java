package fr.esiag.mezzodijava.mezzo.libclient.test;

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.Event;
import fr.esiag.mezzodijava.mezzo.libclient.EventFactory;
import fr.esiag.mezzodijava.mezzo.libclient.exception.EventClientException;

public class TestEventFactory {

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
    public void testCreateEventString() {
	Event e= EventFactory.createEventString(2, 200, "TestEvent");
    }
   
    @Test
    public void testCreateEventObject() {
	Event e= EventFactory.createEventObject(2, 200L, new MyMessage("Salut"));
    }

    public class MyBean implements Serializable{
	public int entier = 4;
	public String chaine = "toto";
    }
    @Test
    public void testExtractObject() {
	String test = "Salut";
	Event e= EventFactory.createEventObject(2, 200L, new MyMessage(test));
	MyMessage res = EventFactory.extractObject(MyMessage.class, e);
	Assert.assertEquals("contenu identique",test,res.myString);
	try{
	    e= EventFactory.createEventObject(2, 200L, new MyBean());
	    Assert.fail("EventClientException expected");
	}catch (EventClientException exc){
	    ; //OK nothin to do
	}
    }

}
