package org.onlinebookstore.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.model.Customer;
import org.onlinebookstore.model.Purchase;

@Model
public class CheckoutBean {

	private Customer customer = new Customer();

	@Inject
	private ShoppingCart shoppingCart;

	@Transactional
	public void finish() {
		Purchase p = new Purchase();
		p.setCustomer(customer);
		shoppingCart.finish(p);

		shoppingCart.getItems().clear();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
