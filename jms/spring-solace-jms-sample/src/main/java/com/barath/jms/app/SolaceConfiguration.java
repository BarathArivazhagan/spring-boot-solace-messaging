package com.barath.jms.app;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolConnectionFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class SolaceConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(){

        SolConnectionFactory connectionFactory = new SolConnectionFactoryImpl();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("barath");
        connectionFactory.setPassword("barath");
        connectionFactory.setVPN("default");

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
