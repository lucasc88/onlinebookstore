package org.onlinebookstore.model;

import java.math.BigDecimal;

public class Book {

	private String title;
	private String description;
	private BigDecimal price;
	private Integer numberOfPages;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", description=" + description + ", price=" + price + ", numberOfPages="
				+ numberOfPages + "]";
	}

}
