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

import ffhs.onlineshop.model.Condition;
import ffhs.onlineshop.repository.ConditionDAO;

@Named
@ViewScoped
public class DashboardConditionController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ConditionDAO conditionDAO;
	
	private List<Condition> conditionList;
	
    private String description;
    
    private boolean sortAscending = true;
   
    @PostConstruct
    public void init() {
    	conditionList = conditionDAO.findAll();
    	System.out.println("INIT Condition !!!!!!!");
    }

    // #### Actions manipulating the list ####
    public String add(){
    	Condition newCondition = new Condition();
    	newCondition.setDescription(description);
    	conditionList.add(newCondition);
    	setDescription("");
    	conditionDAO.addCondition(newCondition);
    	
    	return null;
    }

    public String update(Condition condition){
		try {
			if(condition != null)
	    		System.out.println("Speichern Condition: " + condition.getDescription());
			
			conditionDAO.updateCondition(condition);
	        cancelEdit(condition);
	        //conditionList.add(condition);
	        
	        FacesMessage m = new FacesMessage("Succesfully saved!","id " + condition.getId());
			FacesContext.getCurrentInstance().addMessage("conditionForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("conditionForm", fm);
		}
        return null;
    }
    
    public String edit(Condition condition){
		try {
	    	System.out.println("edit");
	    	for (Condition existing : getConditionList()){
	            existing.setEditable(false);
	        }
	    	condition.setEditable(true);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("conditionForm", fm);
		}
        return null;
    }

    public void cancelEdit(Condition condition){
    	System.out.println("cancelEdit");
    	condition.setEditable(false);
    }

    public void remove(Condition condition){
    	System.out.println("remove");
    	conditionDAO.deleteCondition(condition);
    	conditionList.remove(condition);
    }

	//sort by order no
	public String sortByDescription() {
		
	   if(sortAscending){

		//ascending order
		Collections.sort(conditionList, new Comparator<Condition>() {
			@Override
			public int compare(Condition c1, Condition c2) {
				return c1.getDescription().compareTo(c2.getDescription());
			}
		});
		sortAscending = false;
	   }else{

		//descending order
		Collections.sort(conditionList, new Comparator<Condition>() {
			@Override
			public int compare(Condition c1, Condition c2) {
				return c2.getDescription().compareTo(c1.getDescription());
			}
		});
		sortAscending = true;
	   }
	   return null;
	}
	
    // #### getters and setters ####
    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   
}
