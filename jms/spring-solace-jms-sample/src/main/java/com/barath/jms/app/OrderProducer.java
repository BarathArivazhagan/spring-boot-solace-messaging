package com.barath.jms.app;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.barath.jms.app.model.Order;

//@Service
public class OrderProducer {
	
	  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	    private final JmsTemplate orderTemplate;


	    public OrderProducer(@Qualifier("orderJmsTemplate")JmsTemplate orderTemplate) {
	        this.orderTemplate = orderTemplate;
	    }

	    public void publishOrder(Order order){
	        Assert.notNull(order,"order cannot be null");
	        logger.info("order to be sent {}",Objects.toString(order));
	        this.orderTemplate.convertAndSend("order-topic",order);
	    }
	    
	    //@PostConstruct
	    public void init() {
	    	
	    	IntStream.range(0, 10)
	    		.forEachOrdered(  i ->{
	    			this.publishOrder(new Order(Long.valueOf(i), "TV", i*2, 1000*1));
	    		});
	    }
}
