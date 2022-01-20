package org.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Customer customer;

	private String items;
	private String description;
	private String uuid;
	private BigDecimal total;

	@PrePersist // before to persist in the DB,this method will be performed
	public void createUUId() {
		this.uuid = UUID.randomUUID().toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Get in JSON pattern the sold items
	 * 
	 * @return
	 */
	public String getItems() {
		return items;
	}

	/**
	 * Save in JSON pattern the sold items
	 * 
	 * @param items
	 */
	public void setItems(String items) {
		this.items = items;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
