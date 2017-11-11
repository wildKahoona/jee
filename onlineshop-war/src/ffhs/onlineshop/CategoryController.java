package ffhs.onlineshop;

import java.io.Serializable;
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
    
//    private Category selectedCategory;
   
    @PostConstruct
    public void init() {
    	categoryList = categoryDAO.findAll();
    	System.out.println(" Category INIT !!!!!!!");
    }

    // #### Actions manipulating the list ####
    public void add(){
    	Category newCategory = new Category();
    	newCategory.setDescription(description);
    	categoryDAO.addCategory(newCategory);
    	categoryList.add(newCategory);
    }

    public String update(Category category){
		try {
			if(category != null)
	    		System.out.println("Speichern Category: " + category.getDescription());
			
			categoryDAO.updateCategory(category);
	        cancelEdit(category);
	        
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
        
//    	System.out.println("Speichern");
    	
//    	if(selectedCategory != null)
//    		System.out.println("selectedCategory: " + selectedCategory.getDescription());

//    	Optional<Category> cat = categoryList.stream().filter(x -> x.getId() == category.getId()).findFirst();
//		if (cat.isPresent()){
//			System.out.println("Category found: " + cat.get().getDescription());
//		}
    }

    public String edit(Category category){
		try {
	    	System.out.println("edit");
	    	for (Category existing : getCategoryList()){
	            existing.setEditable(false);
	        }
	        category.setEditable(true);
	        FacesMessage m = new FacesMessage("Succesfully edit!","id " + category.getId());
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

    public void cancelEdit(Category category){
    	System.out.println("cancelEdit");
    	category.setEditable(false);
    }

    public void remove(Category category){
    	System.out.println("remove");
    	categoryDAO.deleteCategory(category);
    	categoryList.remove(category);
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
    
//	public Category getSelectedCategory() {
//		return selectedCategory;
//	}
//
//	public void setSelectedCategory(Category selectedCategory) {
//		this.selectedCategory = selectedCategory;
//	}
}
