package org.onlinebookstore.paypal;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.onlinebookstore.beans.ShoppingCart;
import org.onlinebookstore.model.Customer;

import com.paypal.base.rest.PayPalRESTException;

@WebServlet("/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ShoppingCart shoppingCart;
	

	public AuthorizePaymentServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fullName = request.getParameter("fullname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		Customer customer = new Customer();
		customer.setName(fullName);
		customer.setEmail(email);
		customer.setAddress(address);

		OrderDetail orderDetail = new OrderDetail(shoppingCart.getDescription(), shoppingCart.getTotal().toString(), "0", "0", shoppingCart.getTotal().toString());
		try {
			PaymentServices paymentServices = new PaymentServices();
			String approvalLink = paymentServices.authorizePayment(orderDetail, customer);
			response.sendRedirect(approvalLink);
		} catch (PayPalRESTException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			request.getRequestDispatcher("error.xhtml").forward(request, response);
		}
	}

}