package com.xutt.sky.learn.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xutt.sky.util.HttpClientUtils;

public class CodeTest {
	public static void main(String[] args) throws Exception {
		//testGetBytes();
		//test00();
		// test01();
//		 test02();
		test03();
		
//		System.out.println(Charset.availableCharsets().values());
	}
	
	public static void testGetBytes() throws Exception {
		String str = "中文";
		
		byte[] bytes = str.getBytes("gb2312");
		System.out.print("gb2312:\t");
		CodeUtil.printHexadecimal(bytes);
		
		bytes = str.getBytes("gbk");
		System.out.print("\ngbk:\t");
		CodeUtil.printHexadecimal(bytes);
		
		bytes = str.getBytes("utf-8");
		System.out.print("\nutf-8:\t");
		CodeUtil.printHexadecimal(bytes);
	}

	/**
	 * 编码和解码(1)
	 * 
	 * //１）将字符串用指定的编码集合解析成字节数组，完成Unicode－〉//charsetName转换 public byte[]
	 * getBytes(String charsetName) throws UnsupportedEncodingException
	 * //２）将字节数组以指定的编码集合构造成字符串，完成charsetName－〉Unicode转换 public String(byte[]
	 * bytes, String charsetName) throws UnsupportedEncodingException
	 * 
	 * @throws Exception
	 */
	public static void test00() throws Exception {
		String s = "你好";
		// 编码
		byte[] utf = s.getBytes("utf-8");
		byte[] gbk = s.getBytes("gbk");
		System.out.println("utf-8编码：" + Arrays.toString(utf));// [-28,-67,-96,-27,-91,-67]
																// 6个字节
		System.out.println("gbk编码：" + Arrays.toString(gbk));// [-60,-29,-70,-61]
															// 4个字节
		// 解码
		String s1 = new String(utf, "utf-8"); // 你好
		String s2 = new String(utf, "gbk"); // gbk解码： gbk用2个字节解码，所以会多一个字符
		String s3 = new String(gbk, "utf-8"); // gbk用utf-8解码：??? utf-8解码需要6个字节
		System.out.println("--------------------");
		System.out.println("utf-8解码：" + s1);
		System.out.println("gbk解码：" + s2);
		System.out.println("gbk用utf-8解码：" + s3);
		System.out.println("---------------------");
		System.out.println("用utf-8编码回去");
		s3 = new String(s3.getBytes("utf-8"), "gbk"); // gbk用utf-8解码后无法编回去
		System.out.println(s3);
	}

	/**
	 * 编码和解码(2)
	 * @throws Exception
	 */
	public static void test01() throws Exception {
		String str = "张三";
		byte[] jiema = str.getBytes("gb2312");
		String bianma = new String(jiema, "gb2312");
		System.out.println("bianma:" + bianma);
	}

	/**
	 * 指定编码读写文件
	 * 
	 * @throws Exception
	 */
	public static void test02() throws Exception {
		List<String> list = new ArrayList<String>();

		/**
		 * 1、读文件(使用gb2312解码文件内容)
		 */
		File file = new File("input.txt");
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gb2312"));

			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}

		/**
		 * 2、写文件(使用utf-8编码文件内容)
		 */
		file = new File("output.txt");
		BufferedWriter writer = null;
		try {
			file.delete();
			file.createNewFile();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			for (String content : list) {
				writer.write(content + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
	/**
	 * 指定编码调用http服务
	 * @throws Exception 
	 */
	public static void test03() throws Exception{
//		readHttp();
		writeHttp();
	}
	
	/**
	 * 读取http请求返回数据
	 * @throws Exception
	 */
	public static void readHttp() throws Exception{
		HttpClientUtils httpClient = HttpClientUtils.getInstance();
		
		String httpUrl = "http://localhost:9083/test/service/user/query";
		Map<String, Object> parMap = new HashMap<String,Object>();
		Map<String, String> headerMap = new HashMap<String,String>();
		parMap.put("headerParams",headerMap);
		Map<String, String> urlMap = new HashMap<String,String>();
		parMap.put("urlParams",urlMap);
		String response = httpClient.sendHttpGet(httpUrl, parMap);
		System.out.println("=====>response:" + response);
		
		
	}
	
	/**
	 * 调取http请求发送数据
	 * @throws Exception
	 */
	public static void writeHttp() throws Exception {
		HttpClientUtils httpClient = HttpClientUtils.getInstance();

		String httpUrl = "http://localhost:9083/test/service/user/regist?aaa=雍正&bbb=222";
		httpUrl = encodeUrlParams(httpUrl, "gbk");

		Map<String, Object> parMap = new HashMap<String, Object>();
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json;charset=gbk");
		headerMap.put("realName", "多尔衮");
		parMap.put("headerParams", headerMap);
		Map<String, String> urlMap = new HashMap<String, String>();
		parMap.put("urlParams", urlMap);
		String body = "{\"name\":\"爱新觉罗\",\"age\":\"20\"}";
		parMap.put("bodyParams", body);

		String response = httpClient.sendHttpPost(httpUrl, parMap);
		System.out.println("=====>response:" + response);
	}
	
	/**
	 * 对url参数进行编码
	 * 
	 * @param httpUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUrlParams(String httpUrl, String charset) throws UnsupportedEncodingException {
		StringBuilder suffix = new StringBuilder();
		int index = httpUrl.indexOf("?");
		if (index >= 0) {
			String prefixStr = httpUrl.substring(0, index);
			String suffixStr = httpUrl.substring(index + 1);
			
			String[] paramObjs = suffixStr.split("&");
			for(String paramObj:paramObjs){
				String[] param = paramObj.split("=");
				String key = param[0];
				String value = param[1];
				value = URLEncoder.encode(value, charset);
				suffix.append(key).append("=").append(value).append("&");
			}
			httpUrl = prefixStr + "?" + suffix;
		}

		return httpUrl;
	}
}
