package ffhs.onlineshop.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;

public class ItemDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	@Inject
	private UserDAO userDao;
	
	public List<Item> getAllItems(){
		try {
			EntityManager em = emf.createEntityManager();
			TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}
	
	public void updateItem(Item item){
		try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(item);
            em.flush();
            ut.commit();	
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
//            em.close();
        }
	}
	
    /**
     * get purchase by email from customer
     * 
     * @param email from customer
     * @return
     * @throws SQLException
     */
    public List<Item> getPurchasesByCustomer(String email) {
    	Customer customer = userDao.findUser(email);
    	if(customer != null)
    		return getPurchasesByCustomer(customer);
		return null;
    }
 
    public List<Item> getItemsByCategory(Category category) {
    	if(category == null) return new ArrayList<Item>();
    	
    	EntityManager em = emf.createEntityManager();
    	TypedQuery<Item> query = em.createQuery(
    			"SELECT i FROM Item i WHERE i.category= :category", Item.class);
		query.setParameter("category", category);
		return query.getResultList();
    }
    
    /**
     * get purchase by customer
     * 
     * @param Customer
     * @return
     * @throws SQLException
     */
    public List<Item> getPurchasesByCustomer(Customer customer) {
    	if(customer == null) return new ArrayList<Item>();
		
    	EntityManager em = emf.createEntityManager();
		TypedQuery<Item> query = em.createQuery(
			"SELECT i FROM Item i WHERE i.buyer= :buyer ", Item.class);
		query.setParameter("buyer", customer);
		return query.getResultList();
    }
    
    /**
     * get offers by email from customer
     * 
     * @param email from customer
     * @return
     * @throws SQLException
     */
    public List<Item> getOffersByCustomer(String email) {
    	Customer customer = userDao.findUser(email);
    	if(customer != null)
    		return getOffersByCustomer(customer);		
		return new ArrayList<Item>();
    }    
 
    /**
     * get offers by customer
     * 
     * @param Customer
     * @return
     * @throws SQLException
     */
    public List<Item> getOffersByCustomer(Customer customer) {
    	if(customer != null)
    	{
    		EntityManager em = emf.createEntityManager();
    		TypedQuery<Item> query = em.createQuery(
    			"SELECT i FROM Item i WHERE i.seller= :seller ", Item.class);
    		query.setParameter("seller", customer);
    		List<Item> list = query.getResultList();
    		return list;
    	}
		return null;
    } 
    
	public void deleteItem(Item item) {		
		if(item == null) return;
        try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	Item deleteItem = em.find(Item.class, item.getId());
        	if (deleteItem != null){
        		em.remove(deleteItem);
                em.flush();
                ut.commit();
        	}
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
        }
	}
}