package ffhs.onlineshop;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import ffhs.onlineshop.model.Category;
import ffhs.onlineshop.model.Condition;
import ffhs.onlineshop.model.Customer;
import ffhs.onlineshop.model.Item;
import ffhs.onlineshop.repository.CategoryDAO;
import ffhs.onlineshop.repository.ConditionDAO;
import ffhs.onlineshop.repository.ItemDAO;
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
	
//	private final static Logger log = Logger.getLogger(SigninController.class.toString());
	
	@Inject
	private UserDAO userDao;
	@Inject
	private ItemDAO itemDao;
	@Inject
	private CategoryDAO categoryDAO;
	@Inject
	private ConditionDAO conditionDAO;
	
	@Inject
	private Item item;
	private String username;
	
	private List<Category> categories;
	private List<Condition> conditions;
	private Long selectedCategory; 
	private Long selectedCondition; 
	private Part file;
	
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
		item = new Item();
		setCategories(getAllCatagories());
		setConditions(getAllConditions());
    }

	public List<Category> getAllCatagories(){	
		try {
			return categoryDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Category>();
	}

	public List<Condition> getAllConditions() {		
		try {
			return conditionDAO.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return new ArrayList<Condition>();
	}

	public void persist(){
    	System.out.println("content-type:{0}" + file.getContentType());
    	System.out.println("filename:{0}" + file.getContentType());
    	System.out.println("submitted filename:{0}" + file.getSubmittedFileName());
    	System.out.println("size:{0}" + file.getSize());
    	try {
    		InputStream input = file.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			for (int length = 0; (length = input.read(buffer)) > 0;) {
				output.write(buffer, 0, length);
			}
			item.setFoto(scale(output.toByteArray()));
			
			Optional<Category> category = categories.stream().filter(x -> x.getId() == (long)4).findFirst();
			if(category.isPresent())
				item.setCategory(category.get());	
			
			Optional<Condition> condition = conditions.stream().filter(x -> x.getId() == (long)1).findFirst();
			if(condition.isPresent())
				item.setCondition(condition.get());	
		
			// Um eingeloggten User zu holen
			FacesContext ctx = FacesContext.getCurrentInstance();
			ELContext elc = ctx.getELContext();
			ELResolver elr = ctx.getApplication().getELResolver();
			SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
			Customer customer = signinController.getCustomer();
			item.setSeller(customer);
			
			itemDao.updateItem(item);
			
			setItem(new Item());
			setSelectedCategory(null);
			setSelectedCondition(null);
			
			FacesMessage message = new FacesMessage("Succesfully saved!","Your Offer was saved");
			FacesContext.getCurrentInstance().addMessage("sellForm", message);
		} catch (IOException e) {
		     e.printStackTrace();
		     System.out.println(" error @{0}" + e);
		     FacesMessage message = 
				new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					e.getMessage(), // ACHTUNG: nur im Entwicklerstadium anzeigen !!!
				e.getCause().getMessage());
		     FacesContext.getCurrentInstance().addMessage("sellForm",message);
		}    	
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
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}
}
