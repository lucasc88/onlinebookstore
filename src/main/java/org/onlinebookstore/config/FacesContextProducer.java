package org.onlinebookstore.config;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * Class responsible for returning the FacesContext to produce messages
 * 
 * @author User
 *
 */
public class FacesContextProducer {
	
	@RequestScoped
	@Produces
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
