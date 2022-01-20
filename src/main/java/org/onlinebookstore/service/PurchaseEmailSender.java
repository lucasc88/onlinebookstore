package org.onlinebookstore.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.onlinebookstore.dao.PurchaseDAO;
import org.onlinebookstore.infra.MailSender;
import org.onlinebookstore.model.Purchase;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/TopicShoppingCart"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class PurchaseEmailSender implements MessageListener {

	@Inject
	private MailSender mailSender;
	@Inject
	private PurchaseDAO purchaseDao;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			Purchase p = purchaseDao.findByUUID(textMessage.getText());

			String invoice = buildInvoice(p);
			// send the email for customer
			mailSender.send("purchases@onlinebookstore.com.br", p.getCustomer().getEmail(),
					"New Purchase in OnlineBookStore", "Your purchase was successful. " + invoice);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private String buildInvoice(Purchase p) {
		StringBuffer sb = new StringBuffer();
		sb.append("Order number: " + p.getUuid() + " | ");
		sb.append("Description :" + p.getDescription() + " | ");
		sb.append("Total :" + p.getTotal() + " | ");
		sb.append("Customer :" + p.getCustomer().getName() + " | ");
		sb.append("Email :" + p.getCustomer().getEmail());
		return sb.toString();
	}
}
