package ffhs.onlineshop;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.repository.UserDAO;

import javax.enterprise.context.RequestScoped;

/**
 * 
 * @author
 *
 */
@Named
@RequestScoped
public class RegisterController implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void persist() {
		try {
			userDAO.insertCustomer(customer);
			
			FacesMessage m = new FacesMessage("Erfolgreich registriert!");
			FacesContext.getCurrentInstance().addMessage("registerForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage m = 
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(), // ACHTUNG: nur im Entwicklerstadium anzeigen !!!
					e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("registerForm",m);
		}
	}
}