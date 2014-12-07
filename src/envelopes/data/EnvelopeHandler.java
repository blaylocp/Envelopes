package envelopes.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EnvelopeHandler {
	
	private EntityManager em = null;
	
	public List<Envelope> getEnvelopes() {		
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		
		String url = "jdbc:mysql://" + env.get("OPENSHIFT_MYSQL_DB_HOST") + ":" + env.get("OPENSHIFT_MYSQL_DB_PORT") + "/Envelopes";
		
		configOverrides.put("javax.persistence.jdbc.url", url);
		
		em = Persistence.createEntityManagerFactory("EnvelopeJPA", configOverrides).createEntityManager();
		List<Envelope> envle = null;
		
		Query query = em.createQuery("SELECT e FROM Envelope e");
		envle = query.getResultList();
		
		em.close();
		
		return envle;
	}
	
	public void addEnvelope(String name) {
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		
		String url = "jdbc:mysql://" + env.get("OPENSHIFT_MYSQL_DB_HOST") + ":" + env.get("OPENSHIFT_MYSQL_DB_PORT") + "/Envelopes";
		
		configOverrides.put("javax.persistence.jdbc.url", url);
		
		em = Persistence.createEntityManagerFactory("EnvelopeJPA", configOverrides).createEntityManager();
		em.getTransaction().begin(); 

		Envelope envelope = new Envelope(name);
		
		em.persist(envelope);

		em.getTransaction().commit();
		
		em.close();
	}
	
	public void deleteEnvelope(String id) {
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		
		String url = "jdbc:mysql://" + env.get("OPENSHIFT_MYSQL_DB_HOST") + ":" + env.get("OPENSHIFT_MYSQL_DB_PORT") + "/Envelopes";
		
		configOverrides.put("javax.persistence.jdbc.url", url);
		
		em = Persistence.createEntityManagerFactory("EnvelopeJPA", configOverrides).createEntityManager();
		em.getTransaction().begin(); 
		Envelope envelope = em.find(Envelope.class, Integer.parseInt(id));
		em.remove(envelope);

		em.getTransaction().commit();
		
		em.close();
	}
	
	public void updateEnvelope(String id, String amount) {
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		
		String url = "jdbc:mysql://" + env.get("OPENSHIFT_MYSQL_DB_HOST") + ":" + env.get("OPENSHIFT_MYSQL_DB_PORT") + "/Envelopes";
		
		configOverrides.put("javax.persistence.jdbc.url", url);
		
		em = Persistence.createEntityManagerFactory("EnvelopeJPA", configOverrides).createEntityManager();
		em.getTransaction().begin(); 
		Envelope envelope = em.find(Envelope.class, Integer.parseInt(id));
		envelope.setEnvelope_amount(Double.parseDouble(amount));

		em.getTransaction().commit();
		
		em.close();
	}

	
}
