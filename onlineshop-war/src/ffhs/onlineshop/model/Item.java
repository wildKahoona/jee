package ffhs.onlineshop.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author
 *
 * The persistent class for the ITEM database table.
 * 
 */
@Entity
@Table(schema="ONLINESHOP", name="ITEM")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
//@NamedQuery(name="Item.findAll", query="SELECT i, (SELECT AVG(r.seller_ratingstars) FROM Item r WHERE r.seller.id = i.seller.id) averageStars FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(
			name="ITEM_ID_GENERATOR", 
			sequenceName="SEQ_ITEM",
			schema="ONLINESHOP",
			allocationSize=1,
			initialValue=1)
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE, 
			generator="ITEM_ID_GENERATOR")
	private Long id;

	private String description;

	@Basic(fetch=FetchType.LAZY)
	@Lob
	private byte[] foto;

	private Double price;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sold;

	//bi-directional many-to-one association to Condition
	@ManyToOne
	private Condition condition;
	
	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;
	
//	private Long buyer_id;
	
	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer seller;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer buyer;

	private Integer seller_ratingstars;
	
	private String seller_ratingcomment;
	
	private transient boolean editable;
	
	public Item() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSold() {
		return this.sold;
	}

	public void setSold(Date sold) {
		this.sold = sold;
	}

	public Customer getSeller() {
		return this.seller;
	}

	public void setSeller(Customer seller) {
		this.seller = seller;
	}

	public Customer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Customer buyer) {
		this.buyer = buyer;
	}

	public Condition getCondition() {
		return this.condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getSeller_ratingstars() {
		return seller_ratingstars;
	}

	public void setSeller_ratingstars(Integer seller_ratingstars) {
		this.seller_ratingstars = seller_ratingstars;
	}
	
	public String getSeller_ratingcomment() {
		return seller_ratingcomment;
	}

	public void setSeller_ratingcomment(String seller_ratingcomment) {
		this.seller_ratingcomment = seller_ratingcomment;
	}
	
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
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
		return id + "-" + title + "-" + seller;
	}
}
