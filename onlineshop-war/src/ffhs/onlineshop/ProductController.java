package ffhs.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContextHolder;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.CategoryDAO;
import ffhs.onlineshop.repository.ItemDAO;
import ffhs.onlineshop.repository.UserDAO;

/**
 * 
 * @author 
 *
 */
@Named
@ViewScoped
public class ProductController implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoryDAO categoryDAO;	
	@Inject
	private ItemDAO itemDAO;
	@Inject
	private UserDAO userDAO;
	private List<Item> items;
	private List<Item> allItems;
	private List<Category> categories;
	private Item selectedItem;
	private String search;
	private Customer customer;
	private Customer selectedSeller;	
	private List<Item> offers;
	
    @PostConstruct
    public void init() {
    	setCategories(getAllCatagories());
    	setAllItems(findAll());
    	setItems(getAllItems());
    	
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	setCustomer(userDAO.findUser(username));
    }
	
	public List<Item> findAll() {
		try {
			List<Item> items = itemDAO.getAllItems();
			for(Item item : items){
				Customer seller = item.getSeller();
				List<Item> offers = seller.getOffers().stream().filter(i -> i.getSeller_ratingstars() != null).collect(Collectors.toList());
				Integer sum = offers.stream().mapToInt(o -> o.getSeller_ratingstars()).sum();
				Integer size = offers.size();
				double average = 0;
				Integer count = 0;
				if(size > 0){
					average = sum/size;
					count = size;
				}
				seller.setAverageStars(average);
				seller.setCountComments(count);
			}		
			return items;
		} catch (Exception e) {
			System.out.println(" ex @{0}" + e);
			e.printStackTrace();
		}	
		return new ArrayList<Item>();
	}
	
	public List<Category> getAllCatagories(){	
		try {
			return categoryDAO.findAll();
		} catch (Exception e) {
			System.out.println(" ex @{0}" + e);
			e.printStackTrace();
		}	
		return new ArrayList<Category>();
	}
	
	public void searchItems(){
		items = allItems.stream().filter(x -> x.getTitle().equals(search)).collect(Collectors.toList());
	}
	
	public void filterItems(Category category){
		items = allItems.stream().filter(x -> x.getCategory().equals(category)).collect(Collectors.toList());
	}

	public void buyItem(Item item){	
		if(getCustomer() == null)
			return;
		try {		
			item.setBuyer(getCustomer());
			item.setSold(new Date());
			itemDAO.updateItem(item);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" ex @{0}" + e);
		}
	}
	
//    private double calcAverageStars(Item item) { 
//		Integer sum = 0;
//		if(!item.getSeller().getTos().isEmpty()) {
//		    for (Rating rating : item.getSeller().getTos()) {
//		        sum += rating.getStars();
//		    }
//		    return sum.doubleValue() / item.getSeller().getTos().size();
//		  }
//		  return sum;
//	}
    
//    public void setAverageStars(Customer seller){
//    	Integer sum = 0;
//    	
//    	List<Item> items = allItems.stream().filter(x -> x.getSeller().equals(seller)).collect(Collectors.toList());
//    	
//    	
//    	
////		if(!item.getSeller().getTos().isEmpty()) {
////		    for (Rating rating : item.getSeller().getTos()) {
////		        sum += rating.getStars();
////		    }
////		    return sum.doubleValue() / item.getSeller().getTos().size();
////		  }
////		  return sum;
//    }
    
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
//		for(Item item : items){
//			item.getSeller().setAverageStars(calcAverageStars(item));
//		}
	}
	
	public Item getSelectedItem() {
		return selectedItem;
	}
	
	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Item> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<Item> allItems) {
		this.allItems = allItems;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Customer getSelectedSeller() {
		return selectedSeller;
	}

	public void setSelectedSeller(Customer selectedSeller) {
		offers = allItems.stream().filter(x -> x.getSeller().equals(selectedSeller) && x.getSeller_ratingstars() != null).collect(Collectors.toList());		
		this.selectedSeller = selectedSeller;
	}

	public List<Item> getOffers() {
		return offers;
	}

	public void setOffers(List<Item> offers) {
		this.offers = offers;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
