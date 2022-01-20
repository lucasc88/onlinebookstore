package org.onlinebookstore.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.PurchaseDAO;
import org.onlinebookstore.model.CartItem;
import org.onlinebookstore.model.Purchase;

@Named // Named allows .xhtml access this controller
@SessionScoped // SessionScoped to keep all the book inside the shopping cart
public class ShoppingCart implements Serializable {// Serializable to compress the object in a SessionScoped

	private static final long serialVersionUID = 2739826505942842818L;
	@Inject
	private PurchaseDAO purchaseDao;
	private Set<CartItem> items = new HashSet<>();
	private String uuid;

	public void add(CartItem item) {
		items.add(item);
	}

	public List<CartItem> getItems() {
		return new ArrayList<CartItem>(items);
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem cartItem : items) {
			total = total.add(cartItem.getBook().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
		}
		return total;
	}

	public BigDecimal getTotal(CartItem item) {
		return item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity()));
	}

	public void remove(CartItem item) {
		this.items.remove(item);
	}

	public Integer getTotalQuantity() {
		return items.stream().mapToInt(item -> item.getQuantity()).sum();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Finish the Checkout
	 * 
	 * @param customer
	 */
	@Transactional
	public void finish(Purchase p) {
		p.setItems(this.toJson());
		p.setDescription(this.getDescription());
		p.setTotal(getTotal());
		purchaseDao.save(p);
		uuid = p.getUuid();
	}

	/**
	 * Parse to JSON all the sold items
	 * 
	 * @return
	 */
	public String toJson() {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (CartItem item : items) {
			builder.add(Json.createObjectBuilder().add("title", item.getBook().getTitle())
					.add("price", item.getBook().getPrice()).add("quantity", item.getQuantity())
					.add("total", getTotal(item)));
		}
		String json = builder.build().toString();
		return json;
	}
	
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		for (CartItem item : items) {
			sb.append(item.getBook().getTitle() + "(" + item.getQuantity() + ") Total: " + getTotal(item) + ", ");
		}
		return sb.toString();
	}
}