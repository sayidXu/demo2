package com.xutt.sky.portal.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 会话超时处理过滤器
 * @author wondersgroup
 *
 */
public class SessionFilter implements Filter, TimeoutHandler {

	private final Logger logger = Logger.getLogger(SessionFilter.class);

	private static final String SESSION_USER_KEY = "user.key";
	private static final String LOGIN_URI = "login.uri";
	private static final String EXCLUDE_URI= "exclude.uri";
	private static final String EXCLUDE_FILE= "exclude.file";
	private static final String TIMEOUT_HANDLER = "timeout.handler";

	private String userKey = "userInfo";
	private String loginUri = null;
	private String[] excludeUrArr = null;
	private TimeoutHandler timeoutHandler = null;

	/**
	 * 初始化通过读取web.xml的初始化参数对用户会话属性名、登陆地址、白名单、超时处理器等字段进行设置
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		if(filterConfig.getInitParameter(SESSION_USER_KEY) != null){
			userKey = filterConfig.getInitParameter(SESSION_USER_KEY);
		}
		loginUri = filterConfig.getInitParameter(LOGIN_URI);
		String excludeString = filterConfig.getInitParameter(EXCLUDE_URI);
		String excludeFile = filterConfig.getInitParameter(EXCLUDE_FILE);
		
		StringBuilder sb = new StringBuilder();
		appendExcludeString(excludeString, sb);
		
		if(excludeFile != null && !excludeFile.trim().equals("")) {
			excludeString = readExcludeUriFromFile(excludeFile);
			appendExcludeString(excludeString, sb);
		}
		
		createWhiteList(sb.toString());
		initTimeoutHandler(filterConfig);
	}

	protected void appendExcludeString(String excludeString, StringBuilder sb) {
		if(excludeString != null && !excludeString.trim().equals("")) {
			if(sb.length() == 0) {
				sb.append(excludeString);
			}else{
				sb.append(",").append(excludeString);
			}
		}
	}

	protected String readExcludeUriFromFile(String excludeFile) {
		InputStream is = null;
		if(excludeFile.startsWith("/")) {
			is = SessionFilter.class.getResourceAsStream(excludeFile);
		}else{
			is = SessionFilter.class.getResourceAsStream("/" + excludeFile);
		}
		Properties prop = new Properties();
		try {
			prop.load(is);
			return prop.getProperty(EXCLUDE_URI);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(is);
		}
	}

	protected void close(InputStream is) {
		if( is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	protected void createWhiteList(String excludeString) {
		logger.info("Session filter white list");
		logger.info(loginUri);
		if(excludeString != null && !excludeString.trim().equals("")) {
			excludeUrArr = excludeString.split("\\s+|,");	
			for(String uri : excludeUrArr) {
				logger.info(uri);
			}
		}
	}

	protected void initTimeoutHandler(FilterConfig filterConfig) {
		String className = filterConfig.getInitParameter(TIMEOUT_HANDLER);
		if (className == null || className.trim().equals("")) {
			timeoutHandler = this;
		} else {
			try {
				timeoutHandler = (TimeoutHandler) Class.forName(className).newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 初始化包含登陆地址的白名单集合，并调用超时处理类来判定是否会话超时，一旦超时则返回，否则使请求继续访问
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		/**
		 * 准备白名单数据
		 */
		List<String> whiteList = new ArrayList<String>();
		if(loginUri != null) {
			whiteList.add(loginUri);
		}
		if(excludeUrArr != null) {
			whiteList.addAll(Arrays.asList(excludeUrArr));
		}
		/**
		 * 调用处理器判断是否会话超时
		 */
		if (timeoutHandler.isSessionTimeout(httpServletRequest, httpServletResponse, whiteList)) {
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * session超时判定和处理类，如果超时则跳转到用户定义的登陆uri
	 */
	@Override
	public boolean isSessionTimeout(HttpServletRequest request, HttpServletResponse response, List<String> whiteList) {
		// TODO Auto-generated method stub
		/**
		 * 登陆页面不需检查
		 */
		if (request.getRequestURI().contains(loginUri)) {
			return false;
		}
		/**
		 * 可排除的页面不需检查
		 */
		if(excludeUrArr != null) {
			for (String uri : excludeUrArr) {
				if(request.getRequestURI().contains(uri)){
					return false;
				}
			}
		}
		/**
		 * 判断会话是否有效
		 */
		if (request.getSession().getAttribute(userKey) == null) {
			logger.debug("filter：sessionstatus timeout！");
			// 只过滤了ajax请求时session超时
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setStatus(401);
				response.setHeader("sessionStatus", "timeout");
			} else {
				redirectLoginUri(response, request, loginUri);
			}
			return true;
		}

		return false;
	}

	protected void redirectLoginUri(HttpServletResponse response, HttpServletRequest request, String loginUri) {
		try {
			if (loginUri != null && !loginUri.trim().equals("")) {
				response.sendRedirect(request.getContextPath() + loginUri);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
