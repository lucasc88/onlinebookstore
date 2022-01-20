package org.onlinebookstore.config;

import javax.ejb.Singleton;
import javax.jms.JMSDestinationDefinition;

/**
 * Destination Settings
 * 
 * @author User
 *
 */
@JMSDestinationDefinition(
		name = "java:/jms/topic/TopicShoppingCart",
		interfaceName = "javax.jms.Topic",
		destinationName = "java:/jms/topic/TopicShoppingCart")
@Singleton
public class ConfigureJMSDestination {

}
