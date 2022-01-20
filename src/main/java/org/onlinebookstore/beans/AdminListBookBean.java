package org.onlinebookstore.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;

//@Named and @RequestedScope work together with @Model
@Model
public class AdminListBookBean {

	@Inject
	private BookDAO bookDAO;
	private List<Book> books = new ArrayList<>();

	@Transactional
	public List<Book> getBooks() {
		this.books = bookDAO.list();
		return books;
	}

}
