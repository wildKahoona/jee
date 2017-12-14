package ffhs.onlineshop.repository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Customer;

public class UserDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	public Customer getCustomerById(long customerId){
		EntityManager em = emf.createEntityManager();
		return em.find(Customer.class,customerId);
	}
	
//    /**
//     * get customer by email
//     * 
//     * @param email
//     * @return
//     * @throws SQLException
//     */
//    public String getCustomerByLogin(String email) {
//    	EntityManager em = emf.createEntityManager();
//		TypedQuery<Customer> query = em.createQuery(
//			"SELECT c FROM Customer c WHERE c.email= :email ", Customer.class);
//		query.setParameter("email", email);
//		List<Customer> list = query.getResultList();
//		if(list != null && list.size() > 0) {
//			String password = list.get(0).getPassword();
//            return password;
//		}		
//		return null;
//    }

    public Customer findUser(String email) {
    	EntityManager em = emf.createEntityManager();
		TypedQuery<Customer> query = em.createQuery(
			"SELECT c FROM Customer c WHERE c.email= :email ", Customer.class);
		query.setParameter("email", email);
		List<Customer> list = query.getResultList();
		if(list != null && list.size() > 0) {
            return list.get(0);
		}		
		return null;
    }
    
	public void insertCustomer(Customer customer){
		if(customer == null) return;
		try {
			ut.begin();
			emf.createEntityManager().persist(customer);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
    
	public void updateCustomer(Customer customer){
		if(customer == null) return;
		try {
        	ut.begin();
        	EntityManager em = emf.createEntityManager();
        	em.merge(customer);
            em.flush();
            ut.commit();	
        } catch (Exception e) {
			e.printStackTrace();
        } finally {
//            em.close();
        }
	}
}

