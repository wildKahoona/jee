package ffhs.onlineshop.repository;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Category;

public class CategoryDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	public List<Category> findAll() {
		try {
			TypedQuery<Category> query = emf.createEntityManager().
					createNamedQuery("Category.findAll", Category.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addCategory(Category category) {
		if(category == null) return;
		try {
			ut.begin();
			emf.createEntityManager().persist(category);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCategory(Category category) {
		if(category == null) return;
        try {
    		ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(category);
            em.flush();
            ut.commit();
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
//            em.close();
        }
	}
	
	public void deleteCategory(Category category)
	{		
		if(category == null) return;
        try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	Category deleteCategory = em.find(Category.class, category.getId());
        	if (deleteCategory != null){
        		em.remove(deleteCategory);
                em.flush();
                ut.commit();
        	}
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
        }
	}
}