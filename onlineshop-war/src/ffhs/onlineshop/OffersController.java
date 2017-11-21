package ffhs.onlineshop;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Condition;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.CategoryDAO;
import ffhs.onlineshop.repository.ConditionDAO;
import ffhs.onlineshop.repository.ItemDAO;

@Named
@ViewScoped
public class OffersController implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Inject
	private ItemDAO itemDAO;
	@Inject
	private CategoryDAO categoryDAO;
	@Inject
	private ConditionDAO conditionDAO;
	
	private List<Item> offerList;
	private List<Category> categories;
	private List<Condition> conditions;
	private Long selectedCategory; 
	private Long selectedCondition; 
	
    @PostConstruct
    public void init() {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	setOfferList(itemDAO.getOffersByCustomer(username));
    }

	public List<Category> getAllCatagories(){	
		try {
			return categoryDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Category>();
	}

	public List<Condition> getAllConditions() {		
		try {
			return conditionDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Condition>();
	}
	
    public String update(Item offer){
		try {
			if(offer != null)
	    		System.out.println("Speichern Offer: " + offer.getTitle());
			
			Optional<Category> category = categories.stream().filter(x -> x.getId() == selectedCategory).findFirst();
			if(category.isPresent())
				offer.setCategory(category.get());	
			
			Optional<Condition> condition = conditions.stream().filter(x -> x.getId() == selectedCondition).findFirst();
			if(condition.isPresent())
				offer.setCondition(condition.get());	
			
			itemDAO.updateItem(offer);
	        cancelEdit(offer);
	        
	        System.out.println("Gespeichert: " + offer.getTitle());
	        FacesMessage m = new FacesMessage("Succesfully saved!","id " + offer.getId());
			FacesContext.getCurrentInstance().addMessage("offerForm", m);
		} catch (Exception e) {
			System.out.println(" ex @{0}" + e);
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("offerForm", fm);
		}
        return null;
    }
    
    public String edit(Item offer){
		try {
	    	for (Item existing : getOfferList()){
	            existing.setEditable(false);
	        }
	    	offer.setEditable(true);
	    	setCategories(getAllCatagories());
	    	setConditions(getAllConditions());
	    	
	    	setSelectedCategory(offer.getCategory().getId());
	    	setSelectedCondition(offer.getCondition().getId());
	    	
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("offerForm", fm);
		}
		return null;
    }

    public void cancelEdit(Item offer){
    	offer.setEditable(false);
    }

    public void remove(Item offer){
    	offerList.remove(offer);
    	itemDAO.deleteItem(offer);
    }
    
    // #### getters and setters ####
    	
	public List<Item> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<Item> offerList) {
		this.offerList = offerList;
	}
	
	public List<Category> getCategories() {
		if(this.categories != null)
			return this.categories;
		return getAllCatagories();
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public List<Condition> getConditions() {		
		if(this.conditions != null)
			return this.conditions;
		return getAllConditions();
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
	public Long getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Long selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public Long getSelectedCondition() {
		return selectedCondition;
	}

	public void setSelectedCondition(Long selectedCondition) {
		this.selectedCondition = selectedCondition;
	}
}
