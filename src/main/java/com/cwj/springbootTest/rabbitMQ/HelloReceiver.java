package com.cwj.springbootTest.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwj.springbootTest.domain.entity.User;

@Component
@RabbitListener(queues="hello")
public class HelloReceiver {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@RabbitHandler
	public void process(String context){
		System.out.println("Receiver: "+context);
	}
	@RabbitHandler
	public void process(User user){
		System.out.println("Receiver: "+user);
	}
}
