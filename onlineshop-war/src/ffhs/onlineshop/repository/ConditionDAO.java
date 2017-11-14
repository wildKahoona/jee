package ffhs.onlineshop.repository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Condition;

public class ConditionDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	public List<Condition> findAll() {
		try {
			TypedQuery<Condition> query = emf.createEntityManager().
					createNamedQuery("Condition.findAll", Condition.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addCondition(Condition condition) {
		if(condition == null) return;
		try {
			ut.begin();
			emf.createEntityManager().persist(condition);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCondition(Condition condition) {
		if(condition == null) return;
		try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(condition);
            em.flush();
            ut.commit();
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
//            em.close();
        }
	}
	
	public void deleteCondition(Condition condition)
	{
		if(condition == null) return;
        try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	Condition deleteCondition = em.find(Condition.class, condition.getId());
        	if (deleteCondition != null){
        		em.remove(deleteCondition);
                em.flush();
                ut.commit();
        	}
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
        }
	}
}