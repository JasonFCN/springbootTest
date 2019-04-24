package com.cwj.springbootTest.rabbitMQ;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwj.springbootTest.domain.entity.User;

@Component
public class HelloSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void send(){
		String context = "hello " + new Date();
		System.out.println("sender: "+context);
		User user = new User();
		user.setEmail("1980647842@qq.com");
		user.setNickName("sdkf");
		user.setUserName("tom");
		this.amqpTemplate.convertAndSend("hello",user);
	}
}
