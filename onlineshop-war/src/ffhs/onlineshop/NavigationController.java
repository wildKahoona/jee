package ffhs.onlineshop;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class NavigationController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String page="address.xhtml";
	private String adminPage="category.xhtml";
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String currentPage) {
		this.page=currentPage;
	}

	public String getAdminPage() {
		return adminPage;
	}

	public void setAdminPage(String adminPage) {
		this.adminPage = adminPage;
	}
}
