package com.cwj.springbootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cwj.springbootTest.domain.dao.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;
	@Test
	public void test(){
		//User user = new User("aa1", "123aa", "123a@qq.com", "jack", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		//userRepository.save(user);
		//User user = userRepository.getOne(1L);
		//System.out.println(user.getUserType());
	}
}
