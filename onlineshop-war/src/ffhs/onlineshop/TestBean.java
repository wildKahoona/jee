package ffhs.onlineshop;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.repository.CategoryDAO;
 
@ManagedBean(name="test")
@SessionScoped
public class TestBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CategoryDAO categoryDAO;
	
	private Category selectedOrder;
	private static List<Category> categoryList;

	@PostConstruct
    public void init() {
		System.out.println("INIT !!!!!!!");
		categoryList = categoryDAO.findAll();
    }
	
	public String update() {
		//get all existing value but set "editable" to false 
		for (Category order : categoryList){
			order.setEditable(false);
			System.out.println("Value: " + order.getDescription());
		}
		
		if(selectedOrder != null)
			System.out.println("xxxx: " + selectedOrder.getDescription());
		
		categoryDAO.updateCategory(selectedOrder);
		
		//return to current page
		return null;
		
	}
	
	public String edit(Category order) {
		selectedOrder = order;
		order.setEditable(true);
		return null;
	}
 
	public Category getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Category selectedOrder) {
		this.selectedOrder = selectedOrder;
	}
	
	public List<Category> getCategoryList() {
		return categoryList;
	}
}