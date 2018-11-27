package com.zefu.tiol.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.yr.gap.engine.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * 
	 * @Title: download
	 * @Description:
	 * @param url
	 *            请求的地址
	 * @param filepath
	 *            下载保存的地址
	 * @throws IOException
	 * @author lxc
	 * @date 2018年7月24日
	 */
	public static File download(String url, String filepath, Map<String, Object> paramMap) throws Exception {
		File file = null;
		StringBuffer paramBuffer = new StringBuffer();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			if (paramMap != null && !paramMap.isEmpty()) {
				for (String name : paramMap.keySet()) {
					paramBuffer.append("&" + name + "=" + paramMap.get(name).toString());
				}
				paramBuffer.delete(0, 1);
				url = url + "?" + paramBuffer.toString();
			}
			HttpPost httpPost = new HttpPost(url);
			response = httpClient.execute(httpPost);
			file = saveFile(response, filepath);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
			if (response != null) {
				response.close();
			}
		}
		return file;
	}

	public static File saveFile(HttpResponse response, String filepath) throws Exception {
		InputStream is = null;
		FileOutputStream fileout = null;
		File file = null;
		try {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("文件下载失败：" + response.getStatusLine().getStatusCode());
				return file;
			}
			HttpEntity entity = response.getEntity();
			String fileName = getFileName(response);
			if (StringUtils.isEmpty(fileName)) {// 不存在，则表示当前无文件流访问
				String result = EntityUtils.toString(entity, "UTF-8");
				JSONObject jsonObj = JSONObject.parseObject(result);
				logger.error("文件下载失败，失败原因：" + jsonObj.get("MSG"));
				return file;
			}
			// 获取文件流
			is = entity.getContent();
			// 创建文件目录
			file = new File(filepath);
			if (!file.exists()) {
				file.setWritable(true, false);
				file.mkdirs();
			}
			// 拼接完整文件路径
			filepath = filepath + File.separator + getFileName(response);
			// 设置输出流
			fileout = new FileOutputStream(filepath);
			byte[] buffer = new byte[4096];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			logger.info("文件:" + fileName + "下载成功！");
		} finally {
			if (is != null) {
				is.close();
			}
			if (fileout != null) {
				fileout.flush();
				fileout.close();
			}
		}
		return new File(filepath);
	}

	/**
	 * 
	 * @title: upload
	 * @description: 上传文件
	 * @param url
	 *            请求地址
	 * @param filePath
	 *            文件地址
	 * @param paramMap
	 *            请求参数
	 * @return
	 * @throws Exception
	 * @author: lxc
	 * @date: 2018年11月5日
	 */
	public static String upload(String url, String filePath, Map<String, Object> paramMap) throws Exception {
		String result = "";
		// 默认设置为错误
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClientBuilder.create().build();
			FileBody file = new FileBody(new File(filePath));
			StringBuffer paramBuffer = new StringBuffer();
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.addPart("files", file);
			if (paramMap != null && !paramMap.isEmpty()) {
				for (String name : paramMap.keySet()) {
					paramBuffer.append("&" + name + "=" + paramMap.get(name).toString());
				}
				paramBuffer.delete(0, 1);
				url = url + "?" + paramBuffer.toString();
			}
			logger.info("上传地址为:{}", url);
			HttpPost httppost = new HttpPost(url);
			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);
			response = httpClient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(response.getEntity());
				EntityUtils.consume(resEntity);
			} else {
				throw new Exception("文件上传失败,地址为:" + url + ";错误编码:" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
			if (response != null) {
				response.close();
			}
		}
		return result;
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * 
	 * @param response
	 * @return
	 */
	public static String getFileName(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			}
		}
		return filename;
	}

	public static String sendPost(Map<String, Object> paramMap, String urlStr) throws Exception {
		String result = null;
		// 有可能传的url地址中，参数包含了特殊字符，所以需要经过转出url，再转出uri，否则将报异常
		URL url = new URL(urlStr);
		URI uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(uri);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(10000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (paramMap != null && !paramMap.isEmpty()) {
				List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
				for (Entry<String, Object> entry : paramMap.entrySet()) {
					paramList.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
				}
				if (paramList != null && paramList.size() > 0) {
					// 解决中文乱码问题
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
					httpPost.setEntity(entity);
				}
			}
			response = httpClient.execute(httpPost);
			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 读取服务器返回过来的json字符串数据
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				throw new Exception("接口调用失败!错误编码：" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	/**
	 * post请求
	 * 
	 * @param paramMap
	 *            请求参数
	 * @param urlStr
	 *            请求地址
	 * @return
	 * @throws Exception
	 */
	public static void sendAsyncPost(Map<String, Object> paramMap, String urlStr) throws Exception {
		CloseableHttpAsyncClient httpClient = HttpAsyncClientBuilder.create().build();
		httpClient.start();
		RequestBuilder reqBuilder = RequestBuilder.post(urlStr);
		if (paramMap != null && !paramMap.isEmpty()) {
			for (Entry<String, Object> entry : paramMap.entrySet()) {
				reqBuilder.addParameter(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		HttpUriRequest request = reqBuilder.build();
		httpClient.execute(request, new FutureCallback<HttpResponse>() {
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void completed(HttpResponse response) {
				// TODO Auto-generated method stub
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Exception exception) {
				// TODO Auto-generated method stub
				try {
					if (httpClient != null) {
						httpClient.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("RWID", "1");
		HttpClientUtils.sendAsyncPost(paramMap, "http://localhost:9000/dezj/services/dataAuto");
	}
}
