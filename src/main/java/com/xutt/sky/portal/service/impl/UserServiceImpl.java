package com.xutt.sky.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xutt.sky.portal.dao.UserDao;
import com.xutt.sky.portal.model.User;
import com.xutt.sky.portal.service.UserService;

/**
 * @author gacl 使用@Service注解将UserServiceImpl类标注为一个service service的id是userService
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	/**
	 * 使用@Autowired注解标注userMapper变量， 当需要使用UserMapper时，Spring就会自动注入UserMapper
	 */
	/*
	 * @Autowired private UserMapper userMapper;//注入dao
	 */

	@Autowired
	private UserDao userDao;// 注入dao

	/*
	 * @Override public void addUser(User user) { userMapper.insert(user); }
	 */

	@Override
	public User getUserById(String userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public List<Map<String, String>> getAllUser(String state) {
		return userDao.getUser(state);
	}
}
