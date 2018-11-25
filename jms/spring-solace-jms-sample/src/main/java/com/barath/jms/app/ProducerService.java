package com.barath.jms.app;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.stream.IntStream;

@Service
public class ProducerService {

    private final JmsTemplate jmsTemplate;


    public ProducerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostConstruct
    public void init(){

        IntStream.range(0,100)
                .forEachOrdered( number -> {

                    /** classic sample to use message creator api to send messages **/
                    this.jmsTemplate.send("queue-1", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage("hello"+number);
                        }
                    });

                    this.jmsTemplate.send("queue-2", new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage("hello"+number);
                        }
                    });


                    /** technique to use java8 lambda expressions **/
                    this.jmsTemplate.send("queue-1", session -> session.createTextMessage("hello"+number));
                    this.jmsTemplate.send("queue-2", session -> session.createTextMessage("hello"+number));
                });
    }
}
