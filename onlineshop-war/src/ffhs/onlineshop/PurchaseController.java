package ffhs.onlineshop;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

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
		
	private List<Item> purchaseList;
	private Item selectedPurchase;
    private String selectedStars;
	
    @PostConstruct
    public void init() {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	setPurchaseList(itemDAO.getPurchasesByCustomer(username));
    }
    
    public void rate(Item selectedPurchase){
    	if(selectedPurchase != null){
    		selectedPurchase.setSeller_ratingstars(Integer.parseInt(selectedStars));		   	
			try {
				itemDAO.updateItem(selectedPurchase);				
				FacesMessage m = new FacesMessage("Erfolgreich bewertet!", "");
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
