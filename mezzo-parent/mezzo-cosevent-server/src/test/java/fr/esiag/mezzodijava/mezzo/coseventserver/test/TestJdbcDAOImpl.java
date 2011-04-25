package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.Any;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDAO;
import fr.esiag.mezzodijava.mezzo.coseventserver.factory.BFFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.ConsumerModel;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.EventModel;

public class TestJdbcDAOImpl {

    public static JdbcDAO dao;
    private static EventServerChannelAdminCtr ctr;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	CosEventServer.initConf();
	dao = DAOFactory.getJdbcDAO();
	ctr = new EventServerChannelAdminCtr();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    	dao.deleteAllBase();
    }

    @Test
    public void testFindAllChannel() throws ChannelAlreadyExistsException, ChannelNotFoundException{
		long id1 = ctr.createChannel("MEZZOCHANNEL3", 30);
		long id2 = ctr.createChannel("MEZZOCHANNEL2", 20);
		List<Channel> channels = dao.findAllChannel();
		assertEquals("MEZZOCHANNEL3",channels.get(0).getTopic());
		assertEquals("MEZZOCHANNEL2",channels.get(1).getTopic());
		ctr.destroyChannel(id1);
		ctr.destroyChannel(id2);
    }

    @Test
    public void testFindConsumerByChannel() throws ChannelAlreadyExistsException, AlreadyRegisteredException, ChannelNotFoundException{
    	
    	long id1 = ctr.createChannel("MEZZOCHANNEL4", 30);
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("MEZZOCHANNEL4","testconsumer");
		pfpc.subscribe();
		List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		Map<String, ConsumerModel> map = dao.findConsumerByChannel(id);
		assertTrue("erreur pas de consumer",map.containsKey("testconsumer"));
		ctr.destroyChannel(id1);
    }

    @Test
    public void testFindEventByChannel() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL5", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("special");
		em.setCode(999);
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "toto".getBytes();
		em.setData(b);
    	dao.insertEvent(id,em);
    	SortedSet <EventModel> events = dao.findEventByChannel(id);
    	assertEquals("special",events.last().getType());
    }
    
    
    @Test
    public void testFindEventByConsumer() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL6", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("special");
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "toto".getBytes();
		em.setData(b);
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("consumer");
		int idConsumer = dao.insertConsumer(cm);
    	int idEvent = dao.insertEvent(id,em);
    	dao.addEventToConsumer(idEvent, idConsumer);
    	SortedSet <EventModel> events = dao.findEventByConsumer(idConsumer);
    	assertEquals("special",events.last().getType());
    }

    @Test
    public void testInsertChannel(){
    	Channel c = new Channel();
    	c.setIdentifier(5555);
    	c.setTopic("MEZZOCHANNEL10");
    	dao.insertChannel(c);
    	assertEquals("MEZZOCHANNEL10",dao.findAllChannel().get(0).getTopic());
    }

    @Test
    public void testInsertConsumer() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL25", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("consumermod");
		int idConsumer = dao.insertConsumer(cm);
		assertTrue("erreur pas de consumer trouve",dao.findConsumerByChannel(id).containsKey("consumermod"));
    }

    @Test
    public void testInsertEvent() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL30", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("special");
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "toto".getBytes();
		em.setData(b);
    	int idEvent = dao.insertEvent(id,em);
    	SortedSet <EventModel> events = dao.findEventByChannel(id);
    	assertEquals("special",events.last().getType());
    }

    @Test
    public void testAddEventToConsumer() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL42", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("specialconsum");
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "titi".getBytes();
		em.setData(b);
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("Monconsumer");
		int idConsumer = dao.insertConsumer(cm);
    	int idEvent = dao.insertEvent(id,em);
    	dao.addEventToConsumer(idEvent, idConsumer);
    	SortedSet <EventModel> events = dao.findEventByConsumer(idConsumer);
    	assertEquals("specialconsum",events.last().getType());
    }

    @Test
    public void testUpdateChannel(){
    	Channel c = new Channel();
    	c.setIdentifier(5555);
    	c.setTopic("MEZZOCHANNEL53");
    	c.setCapacity(42);
    	dao.insertChannel(c);
    	assertEquals("MEZZOCHANNEL53",dao.findAllChannel().get(0).getTopic());
    	assertEquals(42,dao.findAllChannel().get(0).getCapacity());
    	c.setCapacity(23);
    	dao.updateChannel(c);
    	assertEquals(23,dao.findAllChannel().get(0).getCapacity());
    }

    @Test
    public void testDeleteConsumer() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL60", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("consumermod");
		int idConsumer = dao.insertConsumer(cm);
		assertTrue("erreur pas de consumer trouve",dao.findConsumerByChannel(id).containsKey("consumermod"));
		dao.deleteConsumer(idConsumer);
		assertFalse("erreur consumer trouve",dao.findConsumerByChannel(id).containsKey("consumermod"));
    }

    @Test
    public void testDeleteChannel(){
    	Channel c = new Channel();
    	c.setIdentifier(5555);
    	c.setTopic("MEZZOCHANNEL10");
    	int channelId = dao.insertChannel(c);
    	assertEquals("MEZZOCHANNEL10",dao.findAllChannel().get(0).getTopic());
    	dao.deleteChannel(channelId);
    	assertTrue(dao.findAllChannel().isEmpty());
    }

    @Test
    public void testDeleteAllConsumers() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL70", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("consumermodel");
		int idConsumer = dao.insertConsumer(cm);
		assertTrue("erreur pas de consumer trouve",dao.findConsumerByChannel(id).containsKey("consumermodel"));
		dao.deleteAllConsumers(id);
		assertTrue(dao.findConsumerByChannel(id).isEmpty());
    }

    @Test
    public void testDeleteEventByConsumer() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL89", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("specialconsum");
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "titi".getBytes();
		em.setData(b);
		ConsumerModel cm = new ConsumerModel();
		cm.setChannel(channels.get(0));
		cm.setIdConsumer("Monconsumer");
		int idConsumer = dao.insertConsumer(cm);
    	int idEvent = dao.insertEvent(id,em);
    	dao.addEventToConsumer(idEvent, idConsumer);
    	SortedSet <EventModel> events = dao.findEventByConsumer(idConsumer);
    	assertEquals("specialconsum",events.last().getType());
    	dao.deleteEventByConsumer(idConsumer, idEvent);
    	assertTrue(dao.findEventByConsumer(idConsumer).isEmpty());
    }

    @Test
    public void testDeleteEvent() throws ChannelAlreadyExistsException{
    	long id1 = ctr.createChannel("MEZZOCHANNEL98", 30);
    	List<Channel> channels = dao.findAllChannel();
		int id = channels.get(0).getId();
		EventModel em = new EventModel();
		em.setType("special");
		em.setCreationdate(1234455);
		em.setPriority(2);
		em.setTimetolive(99999);
		byte[] b = "toto".getBytes();
		em.setData(b);
    	int idEvent = dao.insertEvent(id,em);
    	SortedSet <EventModel> events = dao.findEventByChannel(id);
    	assertEquals("special",events.last().getType());
    	dao.deleteEvent(idEvent);
    	assertTrue(dao.findEventByChannel(id).isEmpty());
    }
    
    @Test
    public void testDeleteAllBase(){
    	dao.deleteAllBase();
    	assertTrue(dao.findAllChannel().isEmpty());
    	assertTrue(dao.findEventByConsumer(1).isEmpty());
    	assertTrue(dao.findConsumerByChannel(1).isEmpty());
    	assertTrue(dao.findEventByChannel(1).isEmpty());
    }


}
