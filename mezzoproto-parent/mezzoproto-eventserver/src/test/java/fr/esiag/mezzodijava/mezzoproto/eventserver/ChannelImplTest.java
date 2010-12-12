package fr.esiag.mezzodijava.mezzoproto.eventserver;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.esiag.mezzodijava.mezzoproto.CosEvent.Event;
import fr.esiag.mezzodijava.mezzoproto.CosEvent.ProxyPushSupplier;
import fr.esiag.mezzodijava.mezzoproto.eventserver.impl.ChannelImpl;
import fr.esiag.mezzodijava.mezzoproto.eventserver.model.EventString;
import fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.ChannelDAO;

public class ChannelImplTest {

	private ChannelImpl channel;

	private ChannelDAO mockDao;
	
	private ProxyPushSupplier mockPPS;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockDao = createMock(ChannelDAO.class); // 1
		channel = new ChannelImpl("MEZZO", 10, mockDao);
		mockPPS = createMock(ProxyPushSupplier.class); // 1
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Testadd_event() {

		Event event = new Event();
		event.message = "TEST_EVENT";

		// 2 on s'attend à ce que add event appelle persist avec un
		// EventString(Attention à la méthode equals de EventString)
		
		mockDao.persist(new EventString(event.message));
		// 3 on passe le mock en mode replay
		replay(mockDao);

		//Essayons EasyMock avec le proxy push supplier
		mockPPS.receive(event);
		replay(mockPPS);
		channel.add_supplier(mockPPS);
		
		//TEST !
		channel.add_event(event);
	}
}
