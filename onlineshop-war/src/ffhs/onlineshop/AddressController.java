package ffhs.onlineshop;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.repository.UserDAO;

@Named
@RequestScoped
public class AddressController implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Inject
	private Customer customer;

	@Inject
	private UserDAO userDAO;
	
	@PostConstruct
    public void init() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = userDAO.findUser(username);
		setCustomer(customer);
	}

    public String edit(){
		try {
	    	customer.setEditable(true);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("addressForm", fm);
		}
        return "/address.jsf";
    }
    
    
    public void cancelEdit(){
    	customer.setEditable(false);
    }
    
	public String persist() {
		try {
			userDAO.updateCustomer(customer);
			customer.setEditable(false);
			
			FacesMessage message = new FacesMessage("Succesfully saved!","Your address was updated");
			FacesContext.getCurrentInstance().addMessage("addressForm", message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" error @{0}" + e);
			FacesMessage message = 
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(), // ACHTUNG: nur im Entwicklerstadium anzeigen !!!
					e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("addressForm",message);
		}
		return "/address.jsf";
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
