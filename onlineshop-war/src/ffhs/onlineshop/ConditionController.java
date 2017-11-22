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
public class ConditionController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ConditionDAO conditionDAO;
	
	private List<Condition> conditionList;
	
    private String description;
    
    private boolean sortAscending = true;
   
    @PostConstruct
    public void init() {
    	conditionList = conditionDAO.findAll();
    }

    public void add(){
    	try {
	    	Condition newCondition = new Condition();
	    	newCondition.setDescription(description);
	    	conditionList.add(newCondition);
	    	setDescription("");
	    	conditionDAO.addCondition(newCondition);
	    	
	    	FacesMessage m = new FacesMessage("Erfolgreich hinzugefügt!");
			FacesContext.getCurrentInstance().addMessage("conditionForm", m);
    	} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("conditionForm", fm);
		}
    }

    public void update(Condition condition){
		try {
			conditionDAO.updateCondition(condition);
	        cancelEdit(condition);
	        
	        FacesMessage m = new FacesMessage("Erfolgreich aktualisiert!");
			FacesContext.getCurrentInstance().addMessage("conditionForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
				FacesMessage.SEVERITY_WARN, 
				e.getMessage(),
				e.getCause().getMessage());
			FacesContext.getCurrentInstance().addMessage("conditionForm", fm);
		}
    }
    
    public void edit(Condition condition){
		try {
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
    }

    public void cancelEdit(Condition condition){
    	condition.setEditable(false);
    }

    public void remove(Condition condition){
    	conditionDAO.deleteCondition(condition);
    	conditionList.remove(condition);
    }

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
