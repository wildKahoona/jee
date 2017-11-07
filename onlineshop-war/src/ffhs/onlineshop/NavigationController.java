package ffhs.onlineshop;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class NavigationController implements Serializable {
	private static final long serialVersionUID = 1L;
	
//	private String page="purchaseList.xhtml";

	private String page="register.xhtml";
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String currentPage) {
		this.page=currentPage;
	}
}
