package ffhs.onlineshop;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Condition;
import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.UserDAO;

/**
 * 
 * @author 
 *
 */
@Named
@RequestScoped
public class SellController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static int MAX_IMAGE_LENGTH = 400;
	
	private final static Logger log = 
			Logger.getLogger(SigninController.class.toString());

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Resource
	private UserTransaction ut;
	
	@Inject
	private UserDAO userDao;
	
	@Inject
	private Item item;
	private String username;
	
	private List<Category> categories;
	private List<Condition> conditions;
	// You can pass only Strings and basic types in JSF inputs. For complex Objects you need a converter.
	private Long selectedCategory; 
	private Long selectedCondition; 
	private Condition condition;
	private Part part;	
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Category> getCategories() {
		if(this.categories != null)
			return this.categories;
		return getAllCatagories();
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Condition> getConditions() {		
		if(this.conditions != null)
			return this.conditions;
		return getAllConditions();
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Long getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Long selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public Long getSelectedCondition() {
		return selectedCondition;
	}

	public void setSelectedCondition(Long selectedCondition) {
		this.selectedCondition = selectedCondition;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}
	
	public List<Category> getAllCatagories(){
		List<Category> items = new ArrayList<Category>();		
		try {
			TypedQuery<Category> query = emf.createEntityManager().
					createNamedQuery("Category.findAll", Category.class);
			return query.getResultList();
//			List<Category> categoryList = query.getResultList();
//			for(Category category: categoryList){
//				items.add(new Category(category.getId(), category.getDescription()));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return items;
	}
	
	public List<Condition> getAllConditions() {		
		try {
			TypedQuery<Condition> query = emf.createEntityManager().
					createNamedQuery("Condition.findAll", Condition.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Condition>();
	}
	
	@PostConstruct
    public void init(){
//		this.username = SecurityContextHolder.getContext().getAuthentication().getName();
//		try {
//			HttpSession session = SessionUtils.getSession();
//			Object xxx = session.getAttribute("username");
//			if(xxx != null)
//			{
//				String username = xxx.toString();
//			}else{
//				FacesContext.getCurrentInstance().getExternalContext().redirect("signin.jsf");
//			}
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		setCategories(getAllCatagories());
		setConditions(getAllConditions());
    }
	
	public String persist(SigninController signinController) {
		System.out.println("!!! Persist !!!");
		
		try {
			ut.begin();
			EntityManager em = emf.createEntityManager();
//			InputStream input = part.getInputStream();
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			byte[] buffer = new byte[10240];
//			for (int length = 0; (length = input.read(buffer)) > 0;) {
//				output.write(buffer, 0, length);
//			}
//			item.setFoto(scale(output.toByteArray()));
			
			System.out.println("Item: " + item.getTitle());
			
			Optional<Category> category = categories.stream().filter(x -> x.getId() == selectedCategory).findFirst();
			if(category.isPresent())
				item.setCategory(category.get());	
			
			Optional<Condition> condition = conditions.stream().filter(x -> x.getId() == selectedCondition).findFirst();
			if(condition.isPresent())
				item.setCondition(condition.get());	
			
			Customer customer = signinController.getCustomer();
			customer = em.find(Customer.class,customer.getId());
//			String email = SecurityContextHolder.getContext().getAuthentication().getName();			
//			Customer customer = userDao.findUser(email);
			item.setSeller(customer);
			
//			TypedQuery<Customer> query = em.createQuery(
//				"SELECT c FROM Customer c WHERE c.email= :email ", Customer.class);
//			query.setParameter("email", email);
//			List<Customer> list = query.getResultList();
//			if(list != null && list.size() > 0) {
//				customer = list.get(0);
				
			em.persist(item);
			ut.commit();

			System.out.println("Item gespeichert: " + item.getTitle());
			
			log.info("Offered item: " + item);

			FacesMessage m = 
				new FacesMessage(
					"Succesfully saved item!",
					"You offered the item " + 
					item);
			FacesContext
				.getCurrentInstance()
				.addMessage("sellForm", m);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage fm = new FacesMessage(
					FacesMessage.SEVERITY_WARN, 
					e.getMessage(),
					e.getCause().getMessage());
				FacesContext
					.getCurrentInstance()
					.addMessage(
					"sellForm", 
					fm);
		}
		return "/sell.jsf";
	}
	
	public byte[] scale(byte[] foto) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(foto);
		BufferedImage originalBufferedImage = ImageIO.read(byteArrayInputStream);	        		
		
		double originalWidth = (double) originalBufferedImage.getWidth();
		double originalHeight = (double) originalBufferedImage.getHeight();
		double relevantLength = originalWidth > originalHeight ? originalWidth : originalHeight; 
		
    	double transformationScale = MAX_IMAGE_LENGTH / relevantLength;
    	int width = (int) Math.round( transformationScale * originalWidth );
    	int height = (int) Math.round( transformationScale * originalHeight );

        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        AffineTransform affineTransform = AffineTransform.getScaleInstance(transformationScale, transformationScale);
        g2d.drawRenderedImage(originalBufferedImage, affineTransform);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "PNG", baos);
        return baos.toByteArray();
	}
}
