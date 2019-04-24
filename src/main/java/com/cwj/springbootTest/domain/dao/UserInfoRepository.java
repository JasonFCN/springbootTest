package com.cwj.springbootTest.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwj.springbootTest.domain.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	UserInfo findByUsername(String userName);
}
