package ffhs.onlineshop;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.repository.CategoryDAO;

@Named
@ViewScoped
public class AdminController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoryDAO categoryDAO;
	
	private List<Category> categoryList;
	
    private String description;
    
    private boolean sortAscending = true;
   
    @PostConstruct
    public void init() {
    	categoryList = categoryDAO.findAll();
    	System.out.println("INIT Category !!!!!!!");
    }

    @PreDestroy
    void destroy() {
        System.out.println("Destroying Category Bean.");
    }
    
    public String add(){
    	Category newCategory = new Category();
    	newCategory.setDescription(description);
    	categoryList.add(newCategory);
    	setDescription("");
    	categoryDAO.addCategory(newCategory);
    	//categoryList = categoryDAO.findAll();
    	return null;
    }

    public String update(Category category){
		try {
			if(category != null)
	    		System.out.println("Speichern Category: " + category.getDescription());
			
			categoryDAO.updateCategory(category);
	        cancelEdit(category);
	        //categoryList.add(category);
	        
	        FacesMessage m = new FacesMessage("Succesfully saved!","id " + category.getId());
			FacesContext.getCurrentInstance().addMessage("categoryForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("categoryForm", fm);
		}
        return null;
    }

    public String edit(Category category){
		try {
	    	System.out.println("edit");
	    	for (Category existing : getCategoryList()){
	            existing.setEditable(false);
	        }
	        category.setEditable(true);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("categoryForm", fm);
		}
        return null;
    }

    public void cancelEdit(Category category){
    	System.out.println("cancelEdit");
    	category.setEditable(false);
    }

    public void remove(Category category){
    	System.out.println("remove");
    	categoryList.remove(category);
    	categoryDAO.deleteCategory(category);
    	//FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("CategoryController");
    	
    }

	//sort by order no
	public String sortByDescription() {
		
	   if(sortAscending){

		//ascending order
		Collections.sort(categoryList, new Comparator<Category>() {
			@Override
			public int compare(Category c1, Category c2) {
				return c1.getDescription().compareTo(c2.getDescription());
			}
		});
		sortAscending = false;
	   }else{

		//descending order
		Collections.sort(categoryList, new Comparator<Category>() {
			@Override
			public int compare(Category c1, Category c2) {
				return c2.getDescription().compareTo(c1.getDescription());
			}
		});
		sortAscending = true;
	   }
	   return null;
	}
	
    // #### getters and setters ####
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   
}

