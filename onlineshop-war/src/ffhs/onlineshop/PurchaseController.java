package ffhs.onlineshop;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.ItemDAO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@Named
@ViewScoped
public class PurchaseController implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Inject
	private ItemDAO itemDAO;
		
	private String username;
	private List<Item> purchaseList;
	private Item selectedPurchase;
    private String selectedStars;
	
    @PostConstruct
    public void init() {
    	//this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
//    	Customer customer = signinController.getCustomer();
//		customer = em.find(Customer.class,customer.getId());
		
    	setUsername("bbb@gmx.ch");
    	System.out.println("User: " + username);
    	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
    }
    
    public void update(Item selectedPurchase){
    	if(selectedPurchase != null){
    		System.out.println("Kommentar: " + selectedPurchase.getSeller_ratingcomment());
    		System.out.println("Selected item: " + selectedStars);
//    		Optional<Item> item = purchaseList.stream().filter(x -> x.getId() == selectedPurchase.getId()).findFirst();
//    		if (item.isPresent()){
//    			setSelectedPurchase(item.get());
//    		}
    		//selectedPurchase.setSeller_ratingcomment(getSelectedPurchase().getSeller_ratingcomment());
    		selectedPurchase.setSeller_ratingstars(Integer.parseInt(selectedStars));
			   	
			try {
				itemDAO.updateItem(selectedPurchase);				
				FacesMessage m = new FacesMessage("Succesfully rate!");
				FacesContext.getCurrentInstance().addMessage("purchaseForm", m);
			} catch (Exception e) {
				e.printStackTrace();
				FacesMessage m = 
					new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						e.getMessage(), // ACHTUNG: nur im Entwicklerstadium anzeigen !!!
						e.getCause().getMessage());
				FacesContext.getCurrentInstance().addMessage("purchaseForm",m);
			}
    	}
    }
    
    // #### getters and setters ####   
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Item> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<Item> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public Item getSelectedPurchase() {
		return selectedPurchase;
	}

	public void setSelectedPurchase(Item selectedPurchase) {
		this.selectedPurchase = selectedPurchase;
	}

	public String getSelectedStars() {
		return selectedStars;
	}

	public void setSelectedStars(String selectedStars) {
		this.selectedStars = selectedStars;
	}
}
