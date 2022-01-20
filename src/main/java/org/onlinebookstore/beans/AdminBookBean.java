package org.onlinebookstore.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.AuthorDAO;
import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.infra.FileSaver;
import org.onlinebookstore.model.Author;
import org.onlinebookstore.model.Book;

//CDI (Context Dependecy Injection)
@Named
@RequestScoped
public class AdminBookBean {

	private Book book = new Book();

	@Inject
	private BookDAO bookDAO;
	@Inject
	private AuthorDAO authorDAO;
	@Inject
	private FacesContext context;
	private Part bookCover;
	
	public AdminBookBean() {
		context = FacesContext.getCurrentInstance();
	}

	@Transactional
	public String saveBook() throws IOException {
		//save the book cover
		FileSaver fileSaver = new FileSaver();
		String relativePath = fileSaver.writeFile(bookCover, "books");
		book.setBookCoverPath(relativePath);
		
		bookDAO.save(book);
		
		//FlashScope to keep the msg live to appear in the list.xhtml
		context.getExternalContext().getFlash().setKeepMessages(true);
		// add msg to list.xhtml using
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Book registered successfully!"));

		return "/books/list?faces-redirect=true";
	}

	@Transactional
	public List<Author> getAuthors() {
		return authorDAO.list();
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Part getBookCover() {
		return bookCover;
	}

	public void setBookCover(Part bookCover) {
		this.bookCover = bookCover;
	}

}
