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
import ffhs.onlineshop.model.Customer;

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
		try {
			ut.begin();
			emf.createEntityManager().persist(category);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCategory(Category category) {
        try {
        	System.out.println("DAO Speichern");
        	if(category != null)
        		System.out.println("Category: " + category.getDescription());
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(category);
            em.flush();
            ut.commit();
            System.out.println("DAO Gespeichert");
        	if(category != null)
        		System.out.println("Category: " + category.getDescription());
        } catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR Speichere Category: " + e.getMessage());
        } finally {
//            em.close();
        }
	}
	
	public void deleteCategory(Category category)
	{
		
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