package org.onlinebookstore.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.onlinebookstore.model.Author;

public class AuthorDAO {

	@PersistenceContext(unitName = "onlinebookstoreDS")
	private EntityManager manager;

	public List<Author> list() {
		return manager.createQuery("select a from Author a", Author.class).getResultList();
	}
}
