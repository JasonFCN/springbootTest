package com.cwj.springbootTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cwj.springbootTest.domain.dao.UserInfoRepository;
import com.cwj.springbootTest.domain.entity.UserInfo;
import com.cwj.springbootTest.service.UserInfoService;
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Override
	@Cacheable(value="cache1",key="#root.methodName.concat(':').concat(#userName)")
	public UserInfo findByUsername(String userName) {
		// TODO Auto-generated method stub
		return userInfoRepository.findByUsername(userName);
	}
	@Override
	public void save(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoRepository.save(userInfo);
	}
	@Override
	public UserInfo getUserInfobyId(Integer id) {
		return userInfoRepository.getOne(id);
	}

}
