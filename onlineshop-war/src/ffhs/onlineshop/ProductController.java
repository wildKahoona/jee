package ffhs.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.model.Rating;
import ffhs.onlineshop.repository.CategoryDAO;
import ffhs.onlineshop.repository.ItemDAO;

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
	private List<Item> items;
	private List<Item> allItems;
	private List<Category> categories;
	private Item selectedItem;
	
    @PostConstruct
    public void init() {
    	System.out.println("!!! Products INIT !!!");
    	setCategories(getAllCatagories());
    	setAllItems(findAll());
    	setItems(getAllItems());
    }
	
	public List<Item> findAll() {
		try {
			return itemDAO.getAllItems();
		} catch (Exception e) {
			System.out.println("Error findAll Items!!! " + e.getMessage());
			e.printStackTrace();
		}	
		return new ArrayList<Item>();
	}
	
	public List<Category> getAllCatagories(){	
		try {
			return categoryDAO.findAll();
		} catch (Exception e) {
			System.out.println("Error getAllCatagories!!! " + e.getMessage());
			e.printStackTrace();
		}	
		return new ArrayList<Category>();
	}
	
	public void filterItems(Category category){
		items = allItems.stream().filter(x -> x.getCategory().equals(category)).collect(Collectors.toList());
	}

    private double calcAverageStars(Item item) { 
		Integer sum = 0;
		if(!item.getSeller().getTos().isEmpty()) {
		    for (Rating rating : item.getSeller().getTos()) {
		        sum += rating.getStars();
		    }
		    return sum.doubleValue() / item.getSeller().getTos().size();
		  }
		  return sum;
	}
    
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
		for(Item item : items){
			item.getSeller().setAverageStars(calcAverageStars(item));
		}
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
}
