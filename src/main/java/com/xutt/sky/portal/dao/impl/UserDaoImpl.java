package com.xutt.sky.portal.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xutt.sky.portal.common.base.BaseDao;
import com.xutt.sky.portal.dao.UserDao;
import com.xutt.sky.portal.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao {
	@Override
	public User getUserById(String id) {
		User user = (User) queryForObject("user.selectByPrimaryKey", id);

		return user;
	}

	@Override
	public List<Map<String, String>> getUser(String state) {
		List<Map<String, String>> userList = queryForList("user.queryAll", state);

		return userList;
	}
}
