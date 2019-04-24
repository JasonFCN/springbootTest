package com.cwj.springbootTest.service;


import java.util.List;

import com.cwj.springbootTest.domain.entity.User;

public interface UserService {
	void updateTestTran(User user);
	void save(User user);
	User findByUserName(String userName);
	List<User> findAll();
	User getById(Long id);

}
