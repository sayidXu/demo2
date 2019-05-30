package com.xutt.sky.portal.service;

import java.util.List;
import java.util.Map;

import com.xutt.sky.portal.model.User;

public interface UserService {

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	/* void addUser(User user); */

	/**
	 * 根据用户id获取用户
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId);

	public List<Map<String, String>> getAllUser(String userId);
}
