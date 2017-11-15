package ffhs.onlineshop.repository;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Rating;

public class RatingDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	public void insertRating(Rating rating) {
		if(rating == null) return;
		try {
			ut.begin();
			emf.createEntityManager().persist(rating);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateRating(Rating rating){
		if(rating == null) return;
		try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(rating);
            em.flush();
            ut.commit();	
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
//            em.close();
        }
	}
}
