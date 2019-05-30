package com.xutt.sky.portal.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xutt.sky.portal.common.exception.BusinessException;

/**
 * String to map
 * 
 * @author 01053895
 *
 */
public class StringToMapUtil {

	public static Map<String, Object> jsonToObject(String jsonStr) {
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

}
