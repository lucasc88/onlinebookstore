package org.onlinebookstore.model;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Promo {

	private String title;
	private BigDecimal discount;
	private Book book = new Book();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String toJSon() {
		JsonObjectBuilder promo = Json.createObjectBuilder();
		promo.add("title", title).add("id", book.getId());
		return promo.build().toString();
	}

}
