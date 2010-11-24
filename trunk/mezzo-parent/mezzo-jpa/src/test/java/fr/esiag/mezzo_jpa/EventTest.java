package fr.esiag.mezzo_jpa;

import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.hibernate.ejb.HibernateEntityManager;
import org.junit.Test;

public class EventTest extends TestCase
{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("mezzo-jpa-test");
    private EventDAO dao;

    public void setUp() throws Exception
    {
    	dao = new EventDAO();
    	
        /*InputStream testData = Event.class.getResourceAsStream("/event.db.xml");

        HibernateEntityManager em = (HibernateEntityManager) emf.createEntityManager();

        DbUnitDataLoader loader = new DbUnitDataLoader(testData, em.getSession().connection());

        loader.populateTestData();*/
        dao.setEntityManager(emf.createEntityManager());
        
        
        
    }
    @Test
    public void testPersist(){
    	Topic topic= new Topic("test");
        Event event = new Event(topic,"testEvent");
    	dao.persistTopic(topic);
    	dao.persistEvent(event);
    
    }

    @Test
    public void testFindByMessage()
    {
    	Topic topic= new Topic("test");
        Event event = new Event(topic,"testEvent");
        dao.persistTopic(topic);
    	dao.persistEvent(event);
    	
        assertNotNull(dao.findEventByMessage("testEvent"));

    }
}
