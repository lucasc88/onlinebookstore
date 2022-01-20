package org.onlinebookstore.beans;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.transaction.Transactional;

@Model
public class EmailSenderBean {

	@Inject
	private JMSContext jmsContext;
	@Resource(name = "java:/jms/topic/TopicShoppingCart")
	private Destination destination;// resource created and set by server

	@Inject
	private ShoppingCart shoppingCart;

	@Transactional
	public String sendEmail() {
		JMSProducer jmsProducer = jmsContext.createProducer();// Java Message Service
		jmsProducer.send(destination, shoppingCart.getUuid());// send(destination , body)
		return "index.xhtml?faces-redirect=true";
	}

}
