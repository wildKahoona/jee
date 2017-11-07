package ffhs.onlineshop;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.repository.UserDAO;

/**
 * 
 * @author 
 *
 */
@Named
@SessionScoped
public class SigninController implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	@Inject
	private UserDAO userDao;
	
	private boolean loggedIn;
	
	private String email;
	
	private String password;

	@Inject
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public String find() {
		try {
			loggedIn = false;
			
			String pwd = userDao.getCustomerByLogin(email);
			
			EntityManager em = emf.createEntityManager();
			TypedQuery<Customer> query = em.createQuery(
				"SELECT c FROM Customer c "
				+ "WHERE c.email= :email "
				+ "AND c.password= :password", 
				Customer.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			List<Customer> list = query.getResultList();
			if(list != null && list.size() > 0) {
				customer = list.get(0);
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", customer.getEmail());
				loggedIn = true;
				return "/search.jsf";
//				FacesMessage m = 
//					new FacesMessage(
//						"Succesfully signed in!",
//						"You signed in under id " + customer.getId());
//				FacesContext
//					.getCurrentInstance()
//					.addMessage("signinForm", m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext
				.getCurrentInstance()
				.addMessage("signinForm", fm);
		}
		// To to login page
		return "/signin.jsf";
	}
}
