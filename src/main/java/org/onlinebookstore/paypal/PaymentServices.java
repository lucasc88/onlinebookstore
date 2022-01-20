package org.onlinebookstore.paypal;

import java.util.ArrayList;
import java.util.List;

import org.onlinebookstore.model.Customer;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaymentServices {
	
	private static final String CLIENT_ID = "ATdR5OIygHyzBJt7eEJanpyJh322Xv4rAII_DDf0VIoCprRKbufM2K8qjX_JkBvPGNm-sk3FPGh9Nvnf";
	private static final String CLIENT_SECRET = "EOjpAumUP2Hvi1kiE0c0rD9JKFtd41vC0ZRcqStcLtzM1qsuP9NN-18C5aiv3vIlfXBCcf978i6tTcq7";
	private static final String MODE = "sandbox";

	public String authorizePayment(OrderDetail orderDetail, Customer customer) throws PayPalRESTException {
		Payer payer = getPayerInformation(customer);
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(orderDetail);

		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction);
		requestPayment.setRedirectUrls(redirectUrls);
		requestPayment.setPayer(payer);
		requestPayment.setIntent("authorize");

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		Payment approvedPayment = requestPayment.create(apiContext);
		
		System.out.println(approvedPayment);
		
		return getApprovalLink(approvedPayment);
	}

	private Payer getPayerInformation(Customer customer) {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Address a = new Address();
		a.setCountryCode("IE");
		a.setCity("Dublin");
		a.setLine1(customer.getAddress());

		PayerInfo payerInfo = new PayerInfo();
		String[] split = customer.getName().trim().split(" ");
		payerInfo.setFirstName(split[0])
			.setLastName(split[split.length - 1])
			.setEmail(customer.getEmail())
			.setBillingAddress(a);

		payer.setPayerInfo(payerInfo);

		return payer;
	}

	private RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/onlinebookstore/cancel.xhtml");
		redirectUrls.setReturnUrl("http://localhost:8080/onlinebookstore/review_payment");
		return redirectUrls;
	}

	private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
		Details details = new Details();
		details.setShipping(orderDetail.getShipping());
		details.setSubtotal(orderDetail.getSubtotal());
		details.setTax(orderDetail.getTax());

		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setTotal(orderDetail.getTotal());
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(orderDetail.getProductName());

		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();

		Item item = new Item();
		item.setCurrency("EUR");
		item.setName(orderDetail.getProductName());
		item.setPrice(orderDetail.getSubtotal());
		item.setTax(orderDetail.getTax());
		item.setQuantity("1");

		items.add(item);
		itemList.setItems(items);
		transaction.setItemList(itemList);

		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);

		return listTransaction;
	}

	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;

		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
				break;
			}
		}

		return approvalLink;
	}

	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		return payment.execute(apiContext, paymentExecution);
	}
}
