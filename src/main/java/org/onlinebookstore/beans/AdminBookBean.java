package org.onlinebookstore.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.onlinebookstore.model.Book;


//CDI (Context Dependecy Injection)
@Named
@RequestScoped
public class AdminBookBean {

	private Book book = new Book();

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void save() {
		System.out.println(book);
	}
}
