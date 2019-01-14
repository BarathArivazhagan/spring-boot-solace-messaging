package com.barath.jms.app;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolConnectionFactoryImpl;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class SolaceConfiguration {

    @Value("${solace.host:localhost}")
    private String solaceHost;


    @Value("${solace.username:admin}")
    private String solaceUserName;


    @Value("${solace.password:admin}")
    private String solacePassword;


    @Value("${solace.vpn:default}")
    private String solaceVPN;



    @Bean
    public ConnectionFactory connectionFactory(){

        SolConnectionFactory connectionFactory = new SolConnectionFactoryImpl();
        connectionFactory.setHost(solaceHost);
        connectionFactory.setUsername(solaceUserName);
        connectionFactory.setPassword(solacePassword);
        connectionFactory.setVPN(solaceVPN);

        return connectionFactory;
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
    	jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
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
        factory.setSubscriptionDurable(true);
        factory.setPubSubDomain(true);
        return factory;
    }

    /**
     * This method creates a jmstemplate by passing connection factory for sending orders.
     * By setting  pubsub domain as true (for topics) and it is used for topic destination type messaging.
     *
     * @param connectionFactory
     * @return an instance of jmsTemplate {@link JmsTemplate}
     */
    @Bean
    public JmsTemplate orderJmsTemplate(ConnectionFactory connectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setMessageConverter(jackson2MessageConverter());
        jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return jmsTemplate;

    }

    /**
     * This method returns a listener container factory to listen to order topics
     *
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory orderTopicListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setSubscriptionDurable(true);
        factory.setMessageConverter(jackson2MessageConverter());
        factory.setPubSubDomain(true);
        return factory;
    }


    @Bean
    public MessageConverter jackson2MessageConverter(){

        MappingJackson2MessageConverter jackson2MessageConverter = new MappingJackson2MessageConverter();
        jackson2MessageConverter.setTypeIdPropertyName("orderId");
        return jackson2MessageConverter;
    }
   
}
