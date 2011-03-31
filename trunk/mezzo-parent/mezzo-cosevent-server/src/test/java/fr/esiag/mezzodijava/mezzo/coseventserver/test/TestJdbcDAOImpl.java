package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDOAImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

public class TestJdbcDAOImpl {

    public static JdbcDOAImpl dao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	dao = new JdbcDOAImpl("test", "test", "C:/mezzodev/coseventBase");

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
    public void testDao(){
	List<Channel> list = dao.findAllChannel();
	SortedSet<EventModel> set = dao.findEventByChannel(0);
	Map<String, ConsumerModel> map = dao.findConsumerByChannel(0);
	dao.deleteEvent(0);
    }

}
