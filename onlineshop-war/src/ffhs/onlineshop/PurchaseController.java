package ffhs.onlineshop;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.model.Rating;
import ffhs.onlineshop.repository.ItemDAO;
import ffhs.onlineshop.repository.RatingDAO;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.inject.Inject;


@Named
//@ConversationScoped
@ViewScoped
public class PurchaseController implements Serializable {	
	private static final long serialVersionUID = 1L;

//	@PersistenceUnit
//	private EntityManagerFactory emf;
//	
//	@Resource
//	private UserTransaction ut;
	
//	@Inject
//    private Conversation conversation;
	
	@Inject
	private ItemDAO itemDAO;
	
	@Inject
	private RatingDAO ratingDAO;
	
	private String username;
	private List<Item> purchaseList;
	private Item selectedPurchase;
	private String comment;
	
    @PostConstruct
    public void init() {
    	//this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    	
//    	Customer customer = signinController.getCustomer();
//		customer = em.find(Customer.class,customer.getId());
		
    	setUsername("bbb@gmx.ch");
    	System.out.println("User: " + username);
    	List<Item> purchaseList = itemDAO.getPurchasesByCustomer(username);
    	System.out.println("Liste gelesen...");
    	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
    }
    
    public void rateSeller(Long purchaseId){
    	//conversation.begin();
    	System.out.println("RATE: " + purchaseId);
    	Optional<Item> item = purchaseList.stream().filter(x -> x.getId() == purchaseId).findFirst();
		if (item.isPresent()){
			System.out.println("RATE found: " + item.get().getId());
			setSelectedPurchase(item.get());			
		}
    }
    
    public void update(Long purchaseId){
    	if(purchaseId != null){
    		System.out.println("Selected Purchase ID: " + getSelectedPurchase().getId() + " comment: " + comment);
    		
    		// Update rate
    		System.out.println("Update rate Purchase ID: " + purchaseId + " comment: " + comment);
    		
    		Optional<Item> item = purchaseList.stream().filter(x -> x.getId() == purchaseId).findFirst();
    		if (item.isPresent()){
    			setSelectedPurchase(item.get());
    		}
        		
        	System.out.println("Update: " + selectedPurchase.getId());
        	
        	Rating rating = new Rating();
        	rating.setStars((int)4);
        	rating.setCommentary(comment);
        	
			// Um eingeloggten User zu holen
			FacesContext ctx = FacesContext.getCurrentInstance();
			ELContext elc = ctx.getELContext();
			ELResolver elr = ctx.getApplication().getELResolver();
			SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
			Customer customer = signinController.getCustomer();
			rating.setTo(customer);
			rating.setFrom(customer);
        	
			try {
				System.out.println("Persist Rating: " + rating.getStars() + ", " + rating.getCommentary());			
				ratingDAO.insertRating(rating);
				
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
			
//        	itemDAO.updateItem(selectedPurchase);
        	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
        	//conversation.end();
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
