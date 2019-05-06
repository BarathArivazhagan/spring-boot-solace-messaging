package com.barath.app;

import javax.jms.ConnectionFactory;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class SolaceConfiguration {

    @Value("${solace.host:localhost}")
    private String solaceHost;


    @Value("${solace.username:admin}")
    private String solaceUserName;


    @Value("${solace.password:admin}")
    private String solacePassword;



    @Bean
    public ConnectionFactory connectionFactory(){

    	return new JmsConnectionFactory(solaceUserName,solacePassword);

    }
    
    /**
     * This method creates a jmstemplate by passing connection factory.
     * By setting  pubsub domain as true (for topics) and it is used for topic destination type messaging.
     * 
     * @param connectionFactory
     * @return an instance of jmsTemplate {@link JmsTemplate}
     */
    @Bean
    public JmsTemplate topicJmsTemplate(ConnectionFactory connectionFactory) {
    	
    	JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    	jmsTemplate.setPubSubDomain(true);
    	return jmsTemplate;
    	
    }
    
    /**
     * This method creates a jmstemplate by passing connection factory.
     * By default pubsub model is p2p (queues) and it is used for queue messaging.
     * 
     * @param connectionFactory
     * @return an instance of jmsTemplate {@link JmsTemplate}
     */
    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        return new JmsTemplate(connectionFactory);
    }
    
    /**
     * This method returns a listener container factory to listen to jms queues
     * 
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory queueListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
    
    /**
     * This method returns a listener container factory to listen to jms topics
     * 
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory topicListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(true);
        return factory;
    }
}
