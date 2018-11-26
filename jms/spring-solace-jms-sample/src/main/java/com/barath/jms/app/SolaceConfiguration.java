package com.barath.jms.app;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolConnectionFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

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

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){

        System.out.println("connection factory "+connectionFactory);

        return new JmsTemplate(connectionFactory);
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
}
