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
	@SuppressWarnings("unchecked")
	public synchronized List<String> getAllMessage(String topic)
    {
		//TODO : erreur
		Query q = entityManager.createQuery("SELECT h FROM Event h WHERE h.topic like :topic");
		q.setParameter("topic", topic);
		return (List<String>)q.getResultList();
    }
}
