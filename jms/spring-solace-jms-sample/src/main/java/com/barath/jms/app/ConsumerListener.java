package com.barath.jms.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

import javax.jms.Message;

@Service
public class ConsumerListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @JmsListener(containerFactory="queueListenerContainerFactory", destination = MessageConstants.QUEUE1)
    public void listenToQueue1(Message message){
    	logger.info("listener queue-1 called {}",message);

    }

    @JmsListener(containerFactory="queueListenerContainerFactory",destination = MessageConstants.QUEUE2)
    public void listenToQueue2(Message message){
    	logger.info("listener queue 2 called {} ",message);

    }
  
    
    @JmsListener(containerFactory="topicListenerContainerFactory" , destination = MessageConstants.DEMOTOPIC, subscription= MessageConstants.DEMOTOPIC)
    public void listenToDemoTopic(Message message){
    	logger.info("message from topic ====> {}",message);

    }
}
