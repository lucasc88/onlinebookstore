package org.onlinebookstore.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.jpa.QueryHints;
import org.onlinebookstore.model.Book;

public class BookDAO {

	@PersistenceContext(unitName = "onlinebookstoreDS")
	private EntityManager em;

	public void save(Book book) {
		em.persist(book);
	}

	public List<Book> list() {
		String jpql = "select distinct(b) from Book b join fetch b.authors";
		return em.createQuery(jpql, Book.class).getResultList();
	}

	public List<Book> lastReleases() {
		String jpql = "select distinct(b) from Book b join fetch b.authors order by b.id desc";
		return em.createQuery(jpql, Book.class).setMaxResults(4).setHint(QueryHints.HINT_CACHEABLE, true)
				.getResultList();
	}

	public List<Book> otherBook() {
		String jpql = "select distinct(b) from Book b join fetch b.authors order by b.id desc";
		return em.createQuery(jpql, Book.class).setFirstResult(5).setHint(QueryHints.HINT_CACHEABLE, true)
				.getResultList();
	}

	public Book findById(Integer id) {
		String jpql = "select distinct(b) from Book b join fetch b.authors where b.id = :id";
		return em.createQuery(jpql, Book.class).setParameter("id", id).getSingleResult();
	}

	public List<Book> lastReleasesResource() {
		String jpql = "select distinct(b) from Book b join fetch b.authors order by b.id desc";
		return em.createQuery(jpql, Book.class).setMaxResults(4).getResultList();
	}

	public List<Book> findByKeyWord(String key) {
		String jpql = "select distinct(b) from Book b join fetch b.authors where b.title like :key or b.description like :key";
		return em.createQuery(jpql, Book.class).setParameter("key", '%' + key + '%').getResultList();
	}

	public void promoUpdatePrice(Integer id, BigDecimal discount) {
		String jpql = "update Book b set b.price = (b.price - (b.price*:discount/100.0)) WHERE b.id = :id";
		em.createQuery(jpql).setParameter("id", id).setParameter("discount", discount).executeUpdate();
	}
}
