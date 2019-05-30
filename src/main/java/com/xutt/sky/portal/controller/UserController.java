package com.xutt.sky.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xutt.sky.portal.common.base.RestResult;
import com.xutt.sky.portal.model.User;
import com.xutt.sky.portal.service.UserService;

/**
 * 门户首页定制
 * 
 * @author 01053895
 *
 */
@Controller
@RequestMapping("/user/operation")
public class UserController {

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/query/abc", method = RequestMethod.GET)
	@ResponseBody
	public String query01(HttpSession session) throws Exception {
		String datas = "success";
		log.debug("datas:" + datas);
		return datas;
	}

	/**
	 * 首页定制信息查询
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	@ResponseBody
	public RestResult<Map<String, Object>> queryById(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userService.getUserById("17");

		result.put("datas", user);

		log.debug("datas:" + user);

		return new RestResult<Map<String, Object>>(result);
	}

	/**
	 * 首页定制信息查询
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public RestResult<Map<String, Object>> query(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<Map<String, String>> userList = userService.getAllUser("0");

		result.put("datas", userList);

		return new RestResult<Map<String, Object>>(result);
	}
	
	/**
	 * 4.1. 新增交换方案
	 * 
	 * @param map
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRtdxScheme", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unused")
	public RestResult<Map<String, Object>> addRtdxScheme(@RequestBody Map<String, Object> body,@RequestHeader Map<String, Object> header,@PathVariable Map<String, Object> urlMap,  HttpSession session)
			throws Exception {
		System.out.println("===========>urlMap:" + urlMap);
		System.out.println("===========>header:" + header);
		System.out.println("===========>body:" + body);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("datas", "success");

		return new RestResult<Map<String, Object>>(result);
	}
	
	@RequestMapping(value = "/addRtdxScheme01", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unused")
	public RestResult<Map<String, Object>> addRtdxScheme(@RequestBody String body,@RequestHeader Map<String, Object> header,@PathVariable Map<String, Object> urlMap, HttpSession session)
			throws Exception {
		System.out.println("===========>urlMap:" + urlMap);
		System.out.println("===========>header:" + header);
		System.out.println("===========>body:" + body);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("datas", "success");

		return new RestResult<Map<String, Object>>(result);
	}

}
