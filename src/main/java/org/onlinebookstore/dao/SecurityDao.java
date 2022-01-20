package org.onlinebookstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.onlinebookstore.model.SystemUser;

public class SecurityDao {

	@PersistenceContext
	private EntityManager em;

	public SystemUser findByEmail(String email) {
		return em.createQuery("select su from SystemUser su " + "where su.email = :email", SystemUser.class)
				.setParameter("email", email).getSingleResult();
	}
}
