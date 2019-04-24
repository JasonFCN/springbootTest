package com.cwj.springbootTest.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwj.springbootTest.domain.dao.UserRepository;
import com.cwj.springbootTest.domain.entity.User;
import com.cwj.springbootTest.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	@Override
	public void updateTestTran(User user) {
		userRepository.save(user);
		//userRepository.deleteById(10L);
		System.out.println(this);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User getById(Long id) {
		/*Optional<User> optional = userRepository.findById(id);
		return optional.get();*/
		return userRepository.getOne(id);
	}
}
