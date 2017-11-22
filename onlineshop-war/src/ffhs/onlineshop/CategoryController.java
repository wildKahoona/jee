package ffhs.onlineshop;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.repository.CategoryDAO;

@Named
@ViewScoped
public class CategoryController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoryDAO categoryDAO;
	
	private List<Category> categoryList;
	
    private String description;
    
    private boolean sortAscending = true;
   
    @PostConstruct
    public void init() {
    	categoryList = categoryDAO.findAll();
    }
    
    public void add(){
    	try {
	    	Category newCategory = new Category();
	    	newCategory.setDescription(description);
	    	categoryList.add(newCategory);
	    	setDescription("");
	    	categoryDAO.addCategory(newCategory);
	        FacesMessage m = new FacesMessage("Erfolgreich hinzugefügt!");
    		FacesContext.getCurrentInstance().addMessage("categoryForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("categoryForm", fm);
		}
    }

    public void update(Category category){
		try {
			categoryDAO.updateCategory(category);
	        cancelEdit(category);
	        
	        FacesMessage m = new FacesMessage("Erfolgreich aktualisiert!");
			FacesContext.getCurrentInstance().addMessage("categoryForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("categoryForm", fm);
		}
    }

    public void edit(Category category){
		try {
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
    }

    public void cancelEdit(Category category){
    	category.setEditable(false);
    }

    public void remove(Category category){
    	categoryList.remove(category);
    	categoryDAO.deleteCategory(category);
    }

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
