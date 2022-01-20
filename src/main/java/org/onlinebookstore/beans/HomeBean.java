package org.onlinebookstore.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;

@Model
public class HomeBean {

	@Inject
	private BookDAO bookDao;

	@Transactional
	public List<Book> lastReleases() {
		return bookDao.lastReleases();
	}

	@Transactional
	public List<Book> otherBooks() {
		return bookDao.otherBook();
	}
}
