package com.barath.jms.app;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.barath.jms.app.model.Order;

@Service
public class OrderConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @JmsListener(containerFactory="orderTopicListenerContainerFactory", destination = MessageConstants.ORDERTOPIC, subscription =  MessageConstants.ORDERTOPIC)
    public void consumerOrder(Order order){
    	logger.info("order received {}",Objects.toString(order));

    }

}
