package com.cwj.springbootTest.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwj.springbootTest.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
