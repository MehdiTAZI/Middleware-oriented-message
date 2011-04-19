package fr.esiag.mezzodijava.mezzo.coseventserver.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzo.cosevent.AlreadyRegisteredException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAdmin;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelAlreadyExistsException;
import fr.esiag.mezzodijava.mezzo.cosevent.ChannelNotFoundException;
import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.EventServerChannelAdminCtr;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.DAOFactory;
import fr.esiag.mezzodijava.mezzo.coseventserver.dao.JdbcDAO;
import fr.esiag.mezzodijava.mezzo.coseventserver.impl.ProxyForPushConsumerImpl;
import fr.esiag.mezzodijava.mezzo.coseventserver.main.CosEventServer;
import fr.esiag.mezzodijava.mezzo.coseventserver.model.Channel;

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
    }
    
    @Test
    public void testFindAllChannel() throws ChannelAlreadyExistsException, ChannelNotFoundException{
		long id1 = ctr.createChannel("MEZZO3", 30);
		long id2 = ctr.createChannel("MEZZO2", 20);
		List<Channel> channels = dao.findAllChannel();
		assertEquals("MEZZO3",channels.get(1).getTopic());
		assertEquals("MEZZO2",channels.get(0).getTopic());
		ctr.destroyChannel(id1);
		ctr.destroyChannel(id2);
    }
    
    @Test
    public void testFindConsumerByChannel() throws ChannelAlreadyExistsException, AlreadyRegisteredException, ChannelNotFoundException{
    	long id1 = ctr.createChannel("MEZZO4", 30);
		ProxyForPushConsumerImpl pfpc = new ProxyForPushConsumerImpl("MEZZO4","testconsumer");
		pfpc.subscribe();
		ChannelAdmin ca = ctr.getChannel(id1);
		//Map<String, ConsumerModel> map = dao.findConsumerByChannel(0);
		//assertTrue("erreur pas de consumer",map.containsKey("testconsumer"));
		ctr.destroyChannel(id1);
    }
    
    @Test
    public void testFindEventByConsumer(){
    	
    }
    
    @Test
    public void testInsertChannel(){
    	
    }
    
    @Test
    public void testInsertConsumer(){
    	
    }
    
    @Test
    public void testInsertEvent(){
    	
    }
    
    @Test
    public void testAddEventToConsumer(){
    	
    }
    
    @Test
    public void testUpdateChannel(){
    	
    }
    
    @Test
    public void testDeleteConsumer(){
    	
    }
    
    @Test
    public void testDeleteChannel(){
    	
    }
    
    @Test
    public void testDeleteAllConsumers(){
    	
    }
    
    @Test
    public void testDeleteEventByConsumer(){
    	
    }
    
    @Test
    public void testDeleteEvent(){
    	
    }
    

}
