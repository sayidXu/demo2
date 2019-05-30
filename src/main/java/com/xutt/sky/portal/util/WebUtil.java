package com.xutt.sky.portal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xutt.sky.portal.common.exception.BusinessException;

/**
 * Web层工具类
 * 
 * @author Richard
 *
 */
public class WebUtil {
	/**
	 * 获取会话中的用户信息
	 * 
	 * @param session
	 *            会话对象
	 * @param propertyName
	 *            用户信息的key
	 * @return 用户信息字符串
	 */
	@SuppressWarnings("unchecked")
	public static String getSessionUserInfo(HttpSession session, String propertyName) {
		String sessionUserInfo = "";
		if (propertyName.equals("roleCode")) {
			Map<String, String> roleMap = (Map<String, String>) session.getAttribute("roleMap");
			String superRoleCode = session.getAttribute("superRoleCode").toString();
			if (roleMap.containsKey(superRoleCode)) {
				sessionUserInfo = "isManagement";
				return sessionUserInfo;
			}
		} else {
			sessionUserInfo = session.getAttribute(propertyName) == null ? ""
					: session.getAttribute(propertyName).toString();
			if (sessionUserInfo.equals("")) {
				throw new BusinessException("404", "获取用户登陆信息失败！");
			}
		}
		return sessionUserInfo;
	}

	/**
	 * 获取创建人信息
	 * 
	 * @param session
	 *            会话对象
	 * @return 创建人信息的map对象
	 * @throws Exception
	 *             会话中不存在相关信息时会抛出异常
	 */
	public static Map<String, String> getCreateUserMap(HttpSession session) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String userId = getSessionUserInfo(session, "userId");
		String userName = getSessionUserInfo(session, "userName");
		map.put("userCode", userId);
		map.put("createUserName", userName);
		return map;
	}

	/**
	 * 获取修改人信息
	 * 
	 * @param session
	 *            会话对象
	 * @return 修改人信息的map对象
	 * @throws Exception
	 *             会话中不存在相关信息时会抛出异常
	 */
	public static Map<String, String> getUpdateUserMap(HttpSession session) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String userId = getSessionUserInfo(session, "userId");
		String userName = getSessionUserInfo(session, "userName");
		map.put("updateUser", userId);
		map.put("updateUserName", userName);
		return map;
	}

	/**
	 * 获取创建人及其组织的信息
	 * 
	 * @param session
	 *            会话对象
	 * @return 创建人及组织信息的map对象
	 * @throws Exception
	 *             信息不存在时将抛出异常
	 */
	public static Map<String, String> getCreateUserAndCreateOrgMap(HttpSession session) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String orgId = getSessionUserInfo(session, "orgId");
		String orgName = getSessionUserInfo(session, "orgName");
		map.put("createOrg", orgId);
		map.put("createOrgName", orgName);
		map.putAll(getCreateUserMap(session));
		map.putAll(getUpdateUserMap(session));
		return map;
	}

	/**
	 * 将json字符串转map的方法
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return map对象
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			// outMap = mapper.readValue(jsonStr, new TypeReference<Map<String,
			// Object>>() {
			// });//不能解析utf-8编码的TXT文本
			byte[] b = jsonStr.getBytes("utf-8");
			outMap = mapper.readValue(b, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			throw new BusinessException("JSON格式错误！");
		}
		// 过滤非法字符
		// sqlValidate(outMap);
		return outMap;
	}
	
	/**
	 * 将map转json字符串的方法
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return map对象
	 */
	public static String mapToJson(Map map) {
		String jsonStr = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(map);
		} catch (Exception e) {
			throw new BusinessException("转换JSON格式出错！");
		}
		
		return jsonStr;
	}

	@SuppressWarnings("unused")
	private static void sqlValidate(Map<String, Object> outMap) {
		List<String> strList = new ArrayList<String>();
		List<String> strMapValue = getFileList(outMap, strList);
		String badStr = "'|select|delete|chr|sitename|net user|xp_cmdshell"
				+ "|like'|and|exec|execute|insert|create|drop|table|from|grant|use"
				+ "|group_concat|column_name|information_schema.columns|table_schema|union"
				+ "|where|update|order|by|count|mid|master|truncate|char|declare|*|or|;|-|--|+|,|like|//|/|%|#";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		if (strMapValue.size() > 0) {
			for (int i = 0; i < badStrs.length; i++) {
				if (strMapValue.indexOf(badStrs[i]) >= 0) {
					System.out.println(badStrs[i]);
					throw new BusinessException("请您避免输入非法字符:" + badStrs[i] + "!");
				}
			}
		}
	}

	/**
	 * 递归抽取map保存的文件路径信息到list中
	 * 
	 * @param map
	 *            保存文件及目录的map对象
	 * @param strList
	 *            空list对象
	 * @return 包含所有文件路径的list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getFileList(Map<String, Object> map, List<String> strList) {
		if (map != null) {
			for (Map.Entry entry : map.entrySet()) {
				Object mapValue = entry.getValue();
				if (mapValue instanceof Map) { // 判断是文件还是文件夹
					getFileList((Map<String, Object>) mapValue, strList); // 获取文件绝对路径
				} else {
					strList.add(mapValue.toString().toLowerCase());
				}
			}
		}
		return strList;
	}
}
