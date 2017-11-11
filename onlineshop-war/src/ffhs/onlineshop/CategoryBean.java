package ffhs.onlineshop;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.repository.CategoryDAO;


@ManagedBean(name="category")
@SessionScoped
public class CategoryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoryDAO categoryDAO;
	
	private List<Category> categoryList;
	
    private String description;
    
    private Category selectedCategory;
   
    @PostConstruct
    public void init() {
    	categoryList = categoryDAO.findAll();
    	System.out.println("INIT !!!!!!!");
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

    public String update(Category category){
    	System.out.println("Speichern");
    	
    	if(selectedCategory != null)
    		System.out.println("selectedCategory: " + selectedCategory.getDescription());
    	
    	if(category != null)
    		System.out.println("Category: " + category.getDescription());
    	
    	Optional<Category> cat = categoryList.stream().filter(x -> x.getId() == category.getId()).findFirst();
		if (cat.isPresent()){
			System.out.println("Category found: " + cat.get().getDescription());
		}
    	categoryDAO.updateCategory(cat.get());
        cancelEdit(category);
        
        return null;
    }

    public String edit(Category category){
    	System.out.println("edit");
    	for (Category existing : getCategoryList()){
            existing.setEditable(false);
        }
        category.setEditable(true);
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

	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
}
