package com.xutt.sky.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtils {
	private final static Logger log = Logger.getLogger(HttpClientUtils.class);
	private final static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50000)
			.setConnectTimeout(50000).setSocketTimeout(180000).build();
	private static HttpClientUtils instance = null;

	private HttpClientUtils() {
	}

	public static HttpClientUtils getInstance() {
		if (instance == null) {
			instance = new HttpClientUtils();
		}
		ContentBody b;
		return instance;
	}

	/**
	 * 发送报问题传送参数请求 PUT
	 * 
	 * @param httpUrl
	 * @param params
	 * @param sentType
	 * @return
	 */
	public String sendHttpPut(String httpUrl, Map<String, Object> parMap) throws Exception {
		URI uri = null;
		HttpPut httpPut = new HttpPut();// 创建httpPost
		Map<String, String> headerMap = (null == parMap.get("headerParams")
				|| "".equals(parMap.get("headerParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("headerParams");
		Map<String, String> urlMap = (null == parMap.get("urlParams")
				|| "".equals(parMap.get("urlParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("urlParams");
		String bodyMap = (null == parMap.get("bodyParams") || "".equals(parMap.get("bodyParams").toString().trim()))
				? null : parMap.get("bodyParams").toString();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// head param
		if (headerMap != null && headerMap.size() > 0) {
			for (String headerKey : headerMap.keySet()) {
				httpPut.setHeader(headerKey, headerMap.get(headerKey));
			}
		}
		// url param
		if (urlMap != null && urlMap.size() > 0) {
			for (String key : urlMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, urlMap.get(key)));
			}
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			if (httpUrl.indexOf("?") > 0) {
				uri = new URI(httpUrl + "&" + str);
			} else {
				uri = new URI(httpUrl + "?" + str);
			}
		} else {
			uri = new URI(httpUrl);
		}
		httpPut.setURI(uri);
		// body param
		if (null != bodyMap && !bodyMap.equals("")) {
			httpPut.setEntity(new StringEntity(bodyMap, "UTF-8"));
		}
		return sendHttpRequest(httpPut, parMap);
	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 * @param maps
	 *            参数
	 */
	public String sendHttpPost(String httpUrl, Map<String, Object> parMap) throws Exception {
		URI uri = null;
		HttpPost httpPost = new HttpPost();// 创建httpPost
		Map<String, String> headerMap = (null == parMap.get("headerParams")
				|| "".equals(parMap.get("headerParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("headerParams");
		Map<String, String> urlMap = (null == parMap.get("urlParams")
				|| "".equals(parMap.get("urlParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("urlParams");
		String bodyMap = (null == parMap.get("bodyParams") || "".equals(parMap.get("bodyParams").toString().trim()))
				? null : parMap.get("bodyParams").toString();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// head param
		if (headerMap != null && headerMap.size() > 0) {
			for (String headerKey : headerMap.keySet()) {
				httpPost.setHeader(headerKey, headerMap.get(headerKey));
			}
		}
		// url param
		if (urlMap != null && urlMap.size() > 0) {
			for (String key : urlMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, urlMap.get(key)));
			}
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			if (httpUrl.indexOf("?") > 0) {
				uri = new URI(httpUrl + "&" + str);
			} else {
				uri = new URI(httpUrl + "?" + str);
			}
		} else {
			uri = new URI(httpUrl);
		}
		httpPost.setURI(uri);
		// body param
		if (null != bodyMap && !bodyMap.equals("")) {
			httpPost.setEntity(new StringEntity(bodyMap, "gbk"));
		}
		return sendHttpRequest(httpPost, parMap);
	}

	/**
	 * 发送 get请求带参数
	 * 
	 * @param httpUrl
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	public String sendHttpGet(String httpUrl, Map<String, Object> parMap) throws Exception {
		URI uri = null;
		HttpGet httpGet = new HttpGet();// 创建get请求
		Map<String, String> headerMap = (null == parMap.get("headerParams")
				|| "".equals(parMap.get("headerParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("headerParams");
		Map<String, String> urlMap = (null == parMap.get("urlParams")
				|| "".equals(parMap.get("urlParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("urlParams");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// head param
		if (headerMap != null && headerMap.size() > 0) {
			for (String headerKey : headerMap.keySet()) {
				httpGet.setHeader(headerKey, headerMap.get(headerKey));
			}
		}
		// url param
		if (urlMap != null && urlMap.size() > 0) {
			for (String key : urlMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, urlMap.get(key)));
			}
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			if (httpUrl.indexOf("?") > 0) {
				uri = new URI(httpUrl + "&" + str);
			} else {
				uri = new URI(httpUrl + "?" + str);
			}
		} else {
			uri = new URI(httpUrl);
		}
		httpGet.setURI(uri);
		return sendHttpRequest(httpGet, parMap);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param httpPost
	 * @return
	 */
	private String sendHttpRequest(HttpRequestBase httpRequest, Map<String, Object> parMap) throws Exception {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			httpRequest.setConfig(requestConfig);
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpRequest);
			int code = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			// 请求失败
			if (200 != code)
				throw new Exception("http code error,code:" + code + ",res:" + responseContent);
		} finally {
			this.closeResource(httpClient, response);
		}
		return responseContent;
	}

	/**
	 * 传递参数的DELETE 方法
	 * 
	 * @param url
	 * @param delParam
	 * @return
	 */
	public String sendHttpDelete(String httpUrl, Map<String, Object> parMap) throws Exception {
		URI uri = null;
		HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody();
		Map<String, String> headerMap = (null == parMap.get("headerParams")
				|| "".equals(parMap.get("headerParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("headerParams");
		Map<String, String> urlMap = (null == parMap.get("urlParams")
				|| "".equals(parMap.get("urlParams").toString().trim())) ? null
						: (Map<String, String>) parMap.get("urlParams");
		String bodyMap = (null == parMap.get("bodyParams") || "".equals(parMap.get("bodyParams").toString().trim()))
				? null : parMap.get("bodyParams").toString();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// head param
		if (headerMap != null && headerMap.size() > 0) {
			for (String headerKey : headerMap.keySet()) {
				httpDeleteWithBody.setHeader(headerKey, headerMap.get(headerKey));
			}
		}
		// url param
		if (urlMap != null && urlMap.size() > 0) {
			for (String key : urlMap.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, urlMap.get(key)));
			}
			String str = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			if (httpUrl.indexOf("?") > 0) {
				uri = new URI(httpUrl + "&" + str);
			} else {
				uri = new URI(httpUrl + "?" + str);
			}
		} else {
			uri = new URI(httpUrl);
		}
		httpDeleteWithBody.setURI(uri);
		// body param
		if (null != bodyMap && !"".equals(bodyMap)) {
			httpDeleteWithBody.setEntity(new StringEntity(bodyMap, "utf-8"));
		}
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpDeleteWithBody);
			int code = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			// 请求失败
			if (200 != code)
				throw new Exception("http code error,code:" + code + ",res:" + responseContent);
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
		}
		return responseContent;
	}

	private static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response) throws Exception {
		// 关闭连接,释放资源
		if (response != null) {
			response.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

	public static String transmit(Map<String, Object> map) throws Exception {
		String returnData = "";
		HttpClientUtils client = HttpClientUtils.getInstance();
		String method = map.get("method").toString();
		String httpUrl = map.get("url").toString();
		log.info("#测试APi " + method + "方法开始执行# httpUrl==" + httpUrl);
		if (method.equals("POST")) {
			returnData = client.sendHttpPost(httpUrl, map);
		} else if (method.equals("GET")) {
			returnData = client.sendHttpGet(httpUrl, map);
		} else if (method.equals("PUT")) {
			returnData = client.sendHttpPut(httpUrl, map);
		} else if (method.equals("DELETE")) {
			returnData = client.sendHttpDelete(httpUrl, map);
		}
		log.info("#测试API " + method + "方法执行完成# " + returnData);
		return returnData;
	}

	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			String httpUrl = "http://172.16.90.158:8686/queryServiceList";
			HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
			httpPost.setHeader("appCode", "DNmuKg");
			httpPost.setHeader("sign", "B65823CE33590CABD31AB7AFEC9639AD");
			httpPost.setHeader("apiCode", "c110bae1-bf7c-40be-be12-9556f9502fad");
			httpPost.setEntity(new StringEntity(
					"{\"map\":{\"pageBean\":{\"page\":\"1\",\"rows\":\"10\"},\"paramObj\":{}}}", "UTF-8"));
			httpPost.setConfig(requestConfig);
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseContent = EntityUtils.toString(entity, "UTF-8");
			System.out.println(responseContent);
		} finally {
			closeResource(httpClient, response);
		}

	}

	class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

		public static final String METHOD_NAME = "DELETE";

		public String getMethod() {
			return METHOD_NAME;
		}

		public HttpDeleteWithBody(final String uri) {
			super();
			setURI(URI.create(uri));
		}

		public HttpDeleteWithBody(final URI uri) {
			super();
			setURI(uri);
		}

		public HttpDeleteWithBody() {
			super();
		}

	}

}
