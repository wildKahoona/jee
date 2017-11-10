package ffhs.onlineshop;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
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
   
    @PostConstruct
    public void init() {
    	categoryList = categoryDAO.findAll();
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

    // #### Actions manipulating the list ####
    public void add(){
    	Category newCategory = new Category();
    	newCategory.setDescription(description);
    	categoryDAO.addCategory(newCategory);
    	categoryList.add(newCategory);
    }

    public void save(Category category){
    	System.out.println("Speichern");
    	if(category != null)
    		System.out.println("Category: " + category.getDescription());
    	categoryDAO.updateCategory(category);
        cancelEdit(category);
    }

    public void edit(Category category){
        for (Category existing : getCategoryList()){
            existing.setEditable(false);
        }
        category.setEditable(true);
    }

    public void cancelEdit(Category category){
    	category.setEditable(false);
    }

    public void remove(Category category){
    	categoryDAO.deleteCategory(category);
    	categoryList.remove(category);
    }
}
