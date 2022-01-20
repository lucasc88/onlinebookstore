package org.onlinebookstore.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;

@Path("books")
public class BookResource {

	@Inject
    private BookDAO bookDao;
	
	@GET
	@Path("releases")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Wrapped(element = "books")
	@Transactional
	public List<Book> lastReleasesServiceJson() {
	    return bookDao.lastReleasesResource();
	}
	
}
