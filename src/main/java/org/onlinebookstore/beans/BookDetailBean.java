package org.onlinebookstore.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;

@Model
public class BookDetailBean {

	@Inject
	private BookDAO bookDao;
	private Book book;
	private Integer id;
	
	@Transactional
	public void loadDetail() {
		this.setBook(bookDao.findById(id));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
}
