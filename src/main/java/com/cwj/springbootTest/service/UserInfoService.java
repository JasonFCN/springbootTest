package com.cwj.springbootTest.service;

import com.cwj.springbootTest.domain.entity.UserInfo;

public interface UserInfoService {
	UserInfo findByUsername(String userName);
	void save(UserInfo userInfo);
	UserInfo getUserInfobyId(Integer id);
}
