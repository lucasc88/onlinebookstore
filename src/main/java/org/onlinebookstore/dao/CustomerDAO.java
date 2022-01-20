package org.onlinebookstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.onlinebookstore.model.Customer;

public class CustomerDAO {

	@PersistenceContext
    private EntityManager em;
	
	public void save(Customer user) {
		em.persist(user);
    }
}
