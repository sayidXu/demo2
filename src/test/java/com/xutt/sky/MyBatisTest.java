package com.xutt.sky;

import java.util.List;
import java.util.Map;

//import me.gacl.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xutt.sky.portal.service.UserService;

public class MyBatisTest {

	private UserService userService;

	/**
	 * 这个before方法在所有的测试方法之前执行，并且只执行一次 所有做Junit单元测试时一些初始化工作可以在这个方法里面进行
	 * 比如在before方法里面初始化ApplicationContext和userService
	 */
	@Before
	public void before() {
		// 使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { "spring-core.xml" });
		// 从Spring容器中根据bean的id取出我们要使用的userService对象
		userService = (UserService) ac.getBean("userService");
	}

	@Test
	public void testAddUser() {
		// ApplicationContext ac = new ClassPathXmlApplicationContext(new
		// String[]{"spring.xml","spring-mybatis.xml"});
		// UserService userService = (UserService) ac.getBean("userService");
		/*
		 * User user = new User();
		 * user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		 * user.setUserName("白虎神皇xdp"); user.setUserBirthday(new Date());
		 * user.setUserSalary(10000D); userService.addUser(user);
		 */
		String state = "0";
		List<Map<String, String>> userList = userService.getAllUser(state);
		System.out.println("userList:" + userList);
	}

}
