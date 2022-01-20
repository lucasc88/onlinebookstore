package org.onlinebookstore.security;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.onlinebookstore.dao.SecurityDao;
import org.onlinebookstore.model.SystemUser;

@Model
public class CurrentUser {

	@Inject
	private HttpServletRequest request;

	@Inject
	private SecurityDao securityDao;

	private SystemUser systemUser;

	
	@PostConstruct//to load the user only once
	private void loadSystemUser() {

		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			String email = request.getUserPrincipal().getName();
			systemUser = securityDao.findByEmail(email);
		}
	}
	
	public SystemUser getUser() {
        return systemUser;
    }
	
	/**
	 * To check if the user has that role
	 * 
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role) {
		return request.isUserInRole(role);
	}
	
	public String logout() {
		request.getSession().invalidate();
		return "/index.xhtml?faces-redirect=true";
	}
}
