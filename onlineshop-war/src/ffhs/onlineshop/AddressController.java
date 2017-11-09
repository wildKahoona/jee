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
		System.out.println("INIT Adresse");
		FacesContext ctx = FacesContext.getCurrentInstance();
			ELContext elc = ctx.getELContext();
			ELResolver elr = ctx.getApplication().getELResolver();
			SigninController signinController = 
				(SigninController) elr.getValue(
					elc, null, "signinController");
			
			Customer customer = signinController.getCustomer();
			if(customer == null)
				System.out.println("Customer NULL");
			else
				System.out.println("Customer : " + customer.getFirstname());
			setCustomer(customer);
			
			try {
//				ut.begin();
//				EntityManager em = emf.createEntityManager();
//				Item item = em.find(Item.class, id);
//				item.setBuyer(customer);
//				item.setSold(new Date());
//				em.merge(item);
//				ut.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public String persist() {
		try {
			userDAO.updateCustomer(customer);

			FacesMessage message = 
				new FacesMessage(
					"Succesfully saved!",
					"Your address was saved under id " + customer.getId());
			FacesContext
				.getCurrentInstance()
				.addMessage("addressForm", message);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
			FacesMessage message = 
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(), // ACHTUNG: nur im Entwicklerstadium anzeigen !!!
					e.getCause().getMessage());
			FacesContext
				.getCurrentInstance()
				.addMessage("addressForm",message);
		}
		return "/user_dashboard.jsf";
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
