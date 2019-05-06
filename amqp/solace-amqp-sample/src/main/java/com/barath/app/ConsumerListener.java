package com.barath.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

import javax.jms.Message;

@Service
public class ConsumerListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @JmsListener(containerFactory="queueListenerContainerFactory", destination = "queue-1")
    public void listenToQueue1(Message message){
    	logger.info("listener called queue1 {}  ",message);

    }

    @JmsListener(containerFactory="queueListenerContainerFactory",destination = "queue-2")
    public void listenToQueue2(Message message){
    	logger.info("listener called queue2 {}  ",message);

    }
  
    
    @JmsListener(containerFactory="topicListenerContainerFactory" , destination = "demo-topic",subscription = "demo-topic")
    public void listenToDemoTopic(Message message){
        System.out.println("topic subscribed ====> "+message);

    }
}
