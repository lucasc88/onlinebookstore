package org.onlinebookstore.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.onlinebookstore.model.Purchase;

public class PurchaseDAO implements Serializable {//Serializable because this DAO is used by SessionScoped Bean (ShoppingCart)

	/**
	 * 
	 */
	private static final long serialVersionUID = 3817220496581438694L;
	
	@PersistenceContext
	private EntityManager em;

	public void save(Purchase p) {
		em.persist(p);
	}

	public Purchase findByUUID(String uuid) {
		return em.createQuery("select p from Purchase p where p.uuid = :uuid", Purchase.class).setParameter("uuid", uuid).getSingleResult();
	}

}
