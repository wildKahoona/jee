package ffhs.onlineshop;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.ItemDAO;

@Named
@RequestScoped
public class OffersController implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Inject
	private ItemDAO itemDAO;
	private String username;
	private List<Item> offerList;
	
    @PostConstruct
    public void init() {
    	System.out.println("!!! Offers INIT !!!");
    	//this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
//    	Customer customer = signinController.getCustomer();
//		customer = em.find(Customer.class,customer.getId());
		
    	setUsername("aaa@gmx.ch");
    	setOfferList(itemDAO.getOffersByCustomer(username));	
    }
    
	public String getUsername() {
		return username;
	}

	// #### getters and setters ####
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Item> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<Item> offerList) {
		this.offerList = offerList;
	}
}
