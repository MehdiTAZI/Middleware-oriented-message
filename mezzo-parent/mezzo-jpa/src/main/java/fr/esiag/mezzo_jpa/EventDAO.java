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
	
	public  void persist(String nomTopic, String message){
		Event event = new Event(nomTopic,message);
		entityManager.persist(event);
	}
	@SuppressWarnings("unchecked")
	public synchronized List<String> getAllMessage(String topic)
    {
		//TODO : erreur
		Query q = entityManager.createQuery("SELECT h FROM MESSAGE h WHERE TOPIC = "+topic);
		return (List<String>)q.getResultList();
    }
}
