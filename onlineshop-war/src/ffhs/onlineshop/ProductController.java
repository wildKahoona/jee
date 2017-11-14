package ffhs.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Item;
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
	private List<Category> categories;
	private Item selectedItem;

	
    @PostConstruct
    public void init() {
    	System.out.println("!!! Products INIT !!!");
    	setCategories(getAllCatagories());
    	items = findAll();
    }
	
	public List<Item> findAll() {
		try {
			return itemDAO.getAllItems();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Item>();
	}
	
	public List<Category> getAllCatagories(){	
		try {
			return categoryDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Category>();
	}
	
	public void loadItems(Category category){
		List<Item> items = new ArrayList<Item>();
		try {
			items = itemDAO.getItemsByCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		setItems(items);
	}
	
	public void showItemDetail(Long itemID){	
		System.out.println("showItemDetail: " + itemID);
		Optional<Item> item = items.stream().filter(x -> x.getId() == itemID).findFirst();
		if (item.isPresent())
			setSelectedItem(item.get());
	}
    
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
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
}
