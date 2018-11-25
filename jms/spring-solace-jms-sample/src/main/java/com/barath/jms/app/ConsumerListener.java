package com.barath.jms.app;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;

@Service
public class ConsumerListener {

    @JmsListener(destination = "queue-1")
    public void listenToQueue1(Message message){
        System.out.println("listener called "+message);

    }

    @JmsListener(destination = "queue-2")
    public void listenToQueue2(Message message){
        System.out.println("listener called "+message);

    }
}
