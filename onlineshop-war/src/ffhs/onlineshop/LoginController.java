package ffhs.onlineshop;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//@Named
//public class LoginController implements Serializable {	
//	private static final long serialVersionUID = 1L;
//	
////	private String username;
////	
////	private String password;
////	
////	public String getUsername() {
////		return "aaa@gmx.ch";
////	}
////
////	public void setUsername(String username) {
////		this.username = username;
////	}
////
////	public String getPassword() {
////		return this.password;
////	}
////
////	public void setPassword(String password) {
////		this.password = password;
////	}
////	
////    public String login() throws ServletException, IOException {
////    	 //do any job with the associated values that you've got from the user, like persisting attempted login, etc.
//////        FacesContext facesContext = FacesContext.getCurrentInstance();
//////        ExternalContext extenalContext = facesContext.getExternalContext();
//////        RequestDispatcher dispatcher = ((ServletRequest)extenalContext.getRequest()).getRequestDispatcher("/j_spring_security_check");
//////        dispatcher.forward((ServletRequest)extenalContext.getRequest(), (ServletResponse)extenalContext.getResponse());
//////        facesContext.responseComplete();
//////        return null;
////        //String url = "/j_spring_security_check?j_username=" + username + "&j_password=" + password;
////    	System.out.println("login!!! ");
////        String url = "/j_spring_security_check?j_username=" + "user1" + "&j_password=" + "12345";
////        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
////        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher(url);
////        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
////        FacesContext.getCurrentInstance().responseComplete();
////        return null;
////    }
//}
