package ffhs.onlineshop;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LocaleController implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private Locale locale;
//	private String lang;
	
	@PostConstruct
	public void init() {
		locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}
	
//	public String getLang() {
//		return lang;
//	}
//
//	public void setLang(String lang) {
//		this.lang = lang;
//	}
	
//	public String change(String lang) {
//		this.lang = lang;
//		return "/index.jsf";
//	}
}
