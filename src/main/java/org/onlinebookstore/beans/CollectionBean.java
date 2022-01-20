package org.onlinebookstore.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;

@Model
public class CollectionBean {

//	montar xhtml para mostrar atraves de pesquisa de palavra chave os livros relacionados
	@Inject
	private BookDAO bookDao;
	private String key;
	private List<Book> books = new ArrayList<>();

	@Transactional
	public List<Book> findByKeyWord() {
		books.addAll(bookDao.findByKeyWord(key));
		return books;
	}

	@Transactional
	public String findByKeyWordSearch() {
		books.addAll(bookDao.findByKeyWord(key));
		return "collection.xhtml?key=" + key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
