package com.xutt.sky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xutt.sky.portal.model.User;
import com.xutt.sky.portal.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// 配置了@ContextConfiguration注解并使用该注解的locations属性指明spring和配置文件之后，
@ContextConfiguration(locations = {
		"classpath:spring-core.xml"/* , "classpath:spring-mvc.xml" */ })
public class MyBatisTestBySpringTestFramework {

	// 注入userService
	@Autowired
	private UserService userService;

	/*
	 * @Test public void testAddUser(){ User user = new User();
	 * user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
	 * user.setUserName("xdp_gacl_白虎神皇"); userService.addUser(user); }
	 */

	@Test
	public void testGetUserById() {
		String userId = "17";
		User user = userService.getUserById(userId);
		System.out.println(user.getUserName());
	}
}
