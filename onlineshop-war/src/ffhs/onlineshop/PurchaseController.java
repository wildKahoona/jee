package ffhs.onlineshop;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.ItemDAO;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@Named
//@RequestScoped
@ConversationScoped
public class PurchaseController implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Inject
    private Conversation conversation;
	
	@Inject
	private ItemDAO itemDAO;
	private String username;
	private List<Item> purchaseList;
	private Item selectedPurchase;
	
    @PostConstruct
    public void init() {
    	System.out.println("!!! INIT !!!");
    	//this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
//    	Customer customer = signinController.getCustomer();
//		customer = em.find(Customer.class,customer.getId());
		
    	setUsername("aaa@gmx.ch");
    	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
    	
    	
    }
 
//    public void rateSeller(Item purchase){
//    	System.out.println("RATE: " + purchase.getId());
//    	setSelectedPurchase(purchase);
//    }
    
    public void rateSeller(Long purchaseId){
    	conversation.begin();
    	System.out.println("RATE: " + purchaseId);
    	Optional<Item> item = purchaseList.stream().filter(x -> x.getId() == purchaseId).findFirst();
		if (item.isPresent()){
			System.out.println("RATE found: " + item.get().getId());
			setSelectedPurchase(item.get());
			
		}
		
    	//setSelectedPurchase(purchase);
    }
    
    public void update(Long purchaseId){
    	if(purchaseId != null){
    		// Update rate
    		System.out.println("Update rate Purchase ID: " + purchaseId);
    		
    		Optional<Item> item = purchaseList.stream().filter(x -> x.getId() == purchaseId).findFirst();
    		if (item.isPresent()){
    			setSelectedPurchase(item.get());
    		}
        		
        	System.out.println("Update: " + selectedPurchase.getId());
        	
        	selectedPurchase.setTitle("aaaa");
        	itemDAO.updateItem(selectedPurchase);
        	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
        	conversation.end();
    	}
    }
    
	public String getUsername() {
		return username;
	}

	// #### getters and setters ####
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
}
