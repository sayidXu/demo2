package com.xutt.sky.portal.dao;

import java.util.List;
import java.util.Map;

import com.xutt.sky.portal.model.User;

public interface UserDao {
	public User getUserById(String id);

	public List<Map<String, String>> getUser(String state);
}
