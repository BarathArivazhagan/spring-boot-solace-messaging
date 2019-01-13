package com.barath.jms.app;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.solacesystems.jcsmp.JCSMPDestinationSession;
import com.solacesystems.jcsmp.JCSMPSession;

@Service
public class ProducerService {

    private final JmsTemplate jmsTemplate;
    private final JmsTemplate topicJmsTemplate;


    public ProducerService(@Qualifier("jmsTemplate") JmsTemplate jmsTemplate,
    		@Qualifier("topicJmsTemplate") JmsTemplate topicJmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.topicJmsTemplate=topicJmsTemplate;
    }

    @PostConstruct
    public void init(){
    		
        IntStream.range(0,10)
                .forEachOrdered( number -> {

                    /** classic sample to use message creator api to send messages **/
                    this.jmsTemplate.send(MessageConstants.QUEUE1, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage("hello"+number);
                        }
                    });

                    this.jmsTemplate.send(MessageConstants.QUEUE2, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage("hello"+number);
                        }
                    });


                    
                    this.jmsTemplate.send(MessageConstants.QUEUE1, session -> session.createTextMessage("hello"+number));
                    this.jmsTemplate.send(MessageConstants.QUEUE2, session -> session.createTextMessage("hello"+number)); 
                    
                 
                    this.topicJmsTemplate.send(MessageConstants.DEMOTOPIC,  session -> {
                    		
                    	Topic topic = session.createTopic(MessageConstants.DEMOTOPIC);
                    	session.createDurableSubscriber(topic, MessageConstants.DEMOTOPIC);                    
                    	return session.createTextMessage("hello"+number);
                    });
                });
    }
}
