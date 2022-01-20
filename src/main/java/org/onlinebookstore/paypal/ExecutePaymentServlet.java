package org.onlinebookstore.paypal;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.onlinebookstore.beans.ShoppingCart;
import org.onlinebookstore.model.Customer;
import org.onlinebookstore.model.Purchase;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;

@WebServlet("/execute_payment")
public class ExecutePaymentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ShoppingCart shoppingCart;
	
	public ExecutePaymentServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");

		try {
			PaymentServices paymentServices = new PaymentServices();
			Payment payment = paymentServices.executePayment(paymentId, payerId);

			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);

			request.setAttribute("payer", payerInfo);
			request.setAttribute("transaction", transaction);
			
			Purchase p = new Purchase();
			Customer c = new Customer();
			c.setName(payerInfo.getFirstName() + " " + payerInfo.getLastName());
			c.setEmail(payerInfo.getEmail());
			c.setAddress(payerInfo.getBillingAddress().getLine1());
			p.setCustomer(c);
			shoppingCart.finish(p);
			
			request.getRequestDispatcher("receipt.xhtml").forward(request, response);

		} catch (PayPalRESTException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			request.getRequestDispatcher("error.xhtml").forward(request, response);
		}
	}

}