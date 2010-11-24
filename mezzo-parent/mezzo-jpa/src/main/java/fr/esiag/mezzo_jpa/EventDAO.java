package fr.esiag.mezzo_jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class EventDAO {
	protected EntityManager entityManager;
	
	public EventDAO() {
		entityManager = EntityManagerUtil.getEntityManager();
	}
	public EntityManager getEntityManager(){
		return entityManager;
	}
	public void setEntityManager(EntityManager em){
		 entityManager = em;
	}
	public  void persistTopic(Topic topic){		
		entityManager.persist(topic);
	}
	
	public  void persistEvent(Event ev){		
		entityManager.persist(ev);
	}

	public Event findEventById(Integer id) {
		try {
			Event instance = entityManager.find(Event.class, id);
			return instance;
		} catch (RuntimeException re) {
			return null;
		}
	}
	public Event findEventByMessage(String message) {
		try {
			Query query = entityManager.createQuery("select e from Event e where e.message like :message");
			query.setParameter("message", message);
			Event event = (Event) query.getSingleResult();
			return event;
		} catch (RuntimeException re) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public synchronized List<String> getAllMessage(String topic)
    {
		//TODO : erreur
		Query q = entityManager.createQuery("SELECT h FROM Event h WHERE h.topic like :topic");
		q.setParameter("topic", topic);
		return (List<String>)q.getResultList();
    }
}
