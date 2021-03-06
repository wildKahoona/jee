package ffhs.onlineshop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
/**
 * 
 * @author Birgit Sturzenegger
 *
 * The persistent class for the CONDITION database table.
 * 
 */
@Entity
@Table(schema="ONLINESHOP", name="CONDITION")
@NamedQuery(
		name="Condition.findAll", 
		query="SELECT c FROM Condition c")
public class Condition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(
			name="CONDITION_ID_GENERATOR", 
			sequenceName="SEQ_CONDITION",
			schema="ONLINESHOP",
			allocationSize=1,
			initialValue=1)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, 
			generator="CONDITION_ID_GENERATOR")
	private Long id;
	
	private String description;
	
	private transient boolean editable;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="condition")
	private Set<Item> items;
	
	public Condition() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Size(min=3, max=10, message="Min 3 and max 10 characters")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public Item addItem(Item item) {
		Set<Item> items = getItems();
		if(items == null) {
			items = new HashSet<Item>();
		}
		items.add(item);
		item.setCondition(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setCondition(null);

		return item;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Condition)) {
			return false;
		}
		Condition other = (Condition) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return id + "-" + description;
	}
}
