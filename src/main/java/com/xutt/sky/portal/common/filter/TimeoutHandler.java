package com.xutt.sky.portal.common.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会话超时判定处理类接口
 * @author wondersgroup
 *
 */
public interface TimeoutHandler {

	/**
	 * 会话超时判定方法
	 * @param request http请求对象
	 * @param response http响应对象
	 * @param whiteList 用户配置的uri白名单集合
	 * @return 会话超时则返回true，否则返回false
	 */
	boolean isSessionTimeout(HttpServletRequest request, HttpServletResponse response, List<String> whiteList);
	
}
