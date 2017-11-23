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
 * @author 
 *
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
@Table(schema="ONLINESHOP", name="CUSTOMER")
@NamedQuery(
		name="Customer.findAll", 
		query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(
			name="CUSTOMER_ID_GENERATOR", 
			sequenceName="SEQ_CUSTOMER",
			schema="ONLINESHOP",
			allocationSize=1,
			initialValue=1)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, 
			generator="CUSTOMER_ID_GENERATOR")
	private Long id;

	private String role;
	
	private String firstname;
	
	private String lastname;
	
	private String street;
	
	private Integer zip;
	
	private String city;
	
	private String country;
	
	private String email;

	private String password;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="seller")
	private Set<Item> offers;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="buyer")
	private Set<Item> purchases;

	private transient String name;
	
	private transient boolean editable;
	
	private transient double averageStars;
	
	private transient Integer countComments;	
	
	public Customer() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Size(min=2, max=40, message="Min 2 and max 40 characters")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Size(min=2, max=40, message="Min 2 and max 40 characters")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Size(min=2, max=100, message="Min 2 and max 100 characters")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	@Size(min=2, max=40, message="Min 2 and max 40 characters")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Size(min=2, max=40, message="Min 2 and max 40 characters")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Size(min=2, max=40, message="Min 2 and max 40 characters")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(min=6, max=10, message="Min 6 and max 10 characters")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Item> getOffers() {
		return this.offers;
	}

	public void setOffers(Set<Item> offers) {
		this.offers = offers;
	}

	public Item addOffer(Item offer) {
		Set<Item> offers = getOffers();
		if(offers == null) {
			offers = new HashSet<Item>();
		}
		offers.add(offer);
		offer.setSeller(this);

		return offer;
	}

	public Item removeOffer(Item offer) {
		getOffers().remove(offer);
		offer.setSeller(null);

		return offer;
	}

	public Set<Item> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Set<Item> purchases) {
		this.purchases = purchases;
	}

	public Item addPurchase(Item purchase) {
		Set<Item> purchases = getPurchases();
		if(purchases == null) {
			purchases = new HashSet<Item>();
		}
		purchases.add(purchase);
		purchase.setBuyer(this);
		return purchase;
	}	

	public Item removePurchase(Item purchase) {
		getPurchases().remove(purchase);
		purchase.setBuyer(null);

		return purchase;
	}
	
	public String getName() {
		return this.firstname + " " + this.lastname;
	}
	
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

	public double getAverageStars() {
		return averageStars;
	}

	public void setAverageStars(double averageStars) {
		this.averageStars = averageStars;
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
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
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
		return id + "-" + email + "-" + password;
	}

	public Integer getCountComments() {
		return countComments;
	}

	public void setCountComments(Integer countComments) {
		this.countComments = countComments;
	}
}

