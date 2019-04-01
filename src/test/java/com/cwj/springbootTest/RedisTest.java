package com.cwj.springbootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.cwj.springbootTest.domain.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void t1 (){
		stringRedisTemplate.opsForValue().append("name", "tom");
	}
	@Test
	public void t2 (){
		User user = new User();
		user.setId(1L);
		user.setNickName("lilei");
		user.setPassWord("sdfsdhf");
		redisTemplate.opsForValue().set("user", user);
	}
	@Test
	public void t3 (){
		Object object = redisTemplate.opsForValue().get("user");
		User user = (User) object;
		
		System.out.println(user.getPassWord());
	}
}
