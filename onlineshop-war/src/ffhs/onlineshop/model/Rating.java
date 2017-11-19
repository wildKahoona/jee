package ffhs.onlineshop.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author
 *
 * The persistent class for the Rating database table.
 * 
 */
@Entity
@Table(schema="ONLINESHOP", name="RATING")
@NamedQuery(name="Rating.findAll", query="SELECT r FROM Rating r")
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(
			name="RATING_ID_GENERATOR", 
			sequenceName="SEQ_RATING",
			schema="ONLINESHOP",
			allocationSize=1,
			initialValue=1)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, 
			generator="RATING_ID_GENERATOR")
	private Long id;
	
	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer from;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer to;
	
	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="sellerrating")
	private Set<Item> sellerratings;
	
	private Integer stars;
	
	private String commentary;

	public Integer getStars() {
		return stars;
	}

	public Rating() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	
	public Set<Item> getSellerratings() {
		return this.sellerratings;
	}

	public void setSellerratings(Set<Item> sellerratings) {
		this.sellerratings = sellerratings;
	}
	
	public Customer getFrom() {
		return this.from;
	}

	public void setFrom(Customer from) {
		this.from = from;
	}

	public Customer getTo() {
		return this.to;
	}

	public void setTo(Customer to) {
		this.to = to;
	}
	
	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
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
		if (!(obj instanceof Rating)) {
			return false;
		}
		Rating other = (Rating) obj;
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
		return id + "-" + stars + "-" + commentary;
	}
}
