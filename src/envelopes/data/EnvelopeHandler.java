package envelopes.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Parameter;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EnvelopeHandler {
	
	private EntityManager em = null;
	
	public List<Envelope> getEnvelopes() {
		em = Persistence.createEntityManagerFactory("EnvelopeJPA").createEntityManager();
		List<Envelope> envle = null;
		
		Query query = em.createQuery("SELECT e FROM Envelope e");
		envle = query.getResultList();
		
		em.close();
		
		return envle;
	}
	
	public void addEnvelope(String name) {
		em = Persistence.createEntityManagerFactory("EnvelopeJPA").createEntityManager();
		em.getTransaction().begin(); 

		Envelope envelope = new Envelope(name);
		
		em.persist(envelope);

		em.getTransaction().commit();
		
		em.close();
	}
	
	public void deleteEnvelope(String id) {
		em = Persistence.createEntityManagerFactory("EnvelopeJPA").createEntityManager();
		em.getTransaction().begin(); 
		Envelope envelope = em.find(Envelope.class, Integer.parseInt(id));
		em.remove(envelope);

		em.getTransaction().commit();
		
		em.close();
	}
	
	public void updateEnvelope(String id, String amount) {
		em = Persistence.createEntityManagerFactory("EnvelopeJPA").createEntityManager();
		em.getTransaction().begin(); 
		Envelope envelope = em.find(Envelope.class, Integer.parseInt(id));
		envelope.setEnvelope_amount(Double.parseDouble(amount));

		em.getTransaction().commit();
		
		em.close();
	}

	
}
