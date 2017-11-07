package ffhs.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Item;

/**
 * 
 * @author 
 *
 */
@Named
@RequestScoped
public class ProductController implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;			
	private List<Item> items;	
	private Item selectedItem;

	public List<Item> getItems() {
		items = findAll();
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public List<Item> findAll() {
		try {
			TypedQuery<Item> query = emf.createEntityManager().
			createNamedQuery("Item.findAll", Item.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}
	
	public void showItemDetail(Long itemID){	
		Optional<Item> item = items.stream().filter(x -> x.getId() == itemID).findFirst();
		if (item.isPresent())
			setSelectedItem(item.get());
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}
	
}
