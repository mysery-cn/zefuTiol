package com.zefu.tiol.util;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.GapFsParam;
import com.yr.gap.common.util.HttpClientHelper;
import com.yr.gap.common.util.StringUtils;
import com.yr.gap.common.vo.AttachmentVo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * FastDFS工具类
 * @author zkq
 */


@Component
@Scope
public class FastDfsUtils {
	/**
	 * 将数据库查询到的数据记录转换成AttachmentVo对象
	 * @param list
	 * @return
	 */
	public static List<AttachmentVo> convertToAttachmentVo(List<Map<String,Object>> list) {
		List<AttachmentVo> attachmentVos = new ArrayList<AttachmentVo>();
		
		for (Map<String, Object> map : list) {
			Iterator<String> it = map.keySet().iterator();
			AttachmentVo attachmentVo = new AttachmentVo();
			while(it.hasNext()){
				String key = it.next();
				Object value = map.get(key); 
				key = StringUtils.toCamel("set_"+key);
				try {
					Method[] methods = AttachmentVo.class.getMethods();
					for (int i = 0; i < methods.length; i++) {
						if(key.equals(methods[i].getName())){
							Class<?>[] parameterTypes = methods[i].getParameterTypes();
							if("int".equals(parameterTypes[0].getSimpleName())){
								methods[i].invoke(attachmentVo, ((BigDecimal)value).intValue());
							}else{
								methods[i].invoke(attachmentVo, String.valueOf(value));
							}
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			attachmentVos.add(attachmentVo);
		}
		return attachmentVos;
	}
	/**
	 * 设置本次附件的存储上级目录名称
	 * @return
	 */
	public static void setParentDir(List<AttachmentVo> attachmentVoList) {
		String uuid = CommonUtil.getUUID();
		for (AttachmentVo attachmentVo : attachmentVoList) {
			attachmentVo.setparentDir(uuid);
		}
	}
	/**
	 * 下载文件
	 * @param fastDfsHostUrl fastdfs文件系统访问地址
	 * @param attachmentVo f封装的附件对象
	 *                     @return  是否下载成功  文件默认下载在    String webRootPath = CommonUtil.getContextRealPath() + "temp/";路径
	 * @throws Exception 
	 */
    public static boolean download(String fastDfsHostUrl,AttachmentVo attachmentVo) {
    	String urlpath = fastDfsHostUrl + "/gapServlet?action=fsOpenManageServlet&pm=1";
		URL url = null;
		HttpURLConnection httpCon = null;
		HttpURLConnection httpDownCon = null;
		try {
			url = new URL(urlpath);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(5 * 1000);
			httpCon.setReadTimeout(180 * 1000);
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setUseCaches(false);
			httpCon.setAllowUserInteraction(false);
			httpCon.setRequestProperty("Content-type", "text/html;charset=UTF-8");
			httpCon.setRequestProperty("requesttype", "download");
			httpCon.setRequestProperty("fid",setValue(attachmentVo.getFileId()));
			httpCon.setRequestProperty("fname",setValue(attachmentVo.getFileName()));
			httpCon.setRequestProperty("ftype",setValue(attachmentVo.getFileType()));
//			httpCon.setRequestProperty("fc",setValue(vo.getVersionControl()));
			httpCon.setRequestProperty("fv",String.valueOf(attachmentVo.getFileVersion()));
			httpCon.setRequestProperty("fu",GapFsParam.ACC_USER);
//			httpCon.setRequestProperty("fp",setValue(vo.getProperties()));
			httpCon.setInstanceFollowRedirects(false);
			//httpCon.connect();
			BufferedWriter out = null;
			try {
			  out = new BufferedWriter(new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8"));
			  out.flush();
			}finally {
		       if (out != null)
				 out.close();
			}
			int rscode = httpCon.getResponseCode();
			if (rscode == 200) {
				InputStream is = httpCon.getInputStream();
				saveToFile(is, attachmentVo);
			}
			else if(rscode==302){
				String referer=httpCon.getHeaderField("Location");
				url = new URL(referer);
				httpDownCon=(HttpURLConnection) url.openConnection();
				rscode = httpDownCon.getResponseCode();
				if(rscode==200){
					InputStream is = httpDownCon.getInputStream();
					saveToFile(is, attachmentVo);
				}else{
					return false;
					/*
					map.put("messageCode","-1");
					map.put("message","连接文件服务器:"+mailAttachVo.getAttachUrl()+"失败 ResonseCode="+rscode);
					map.put("success","false");
					*/							
				}
			}else{
				//下载失败
				return false;
				/*
				map.put("messageCode","-1");
				map.put("message","连接文件服务器:"+mailAttachVo.getAttachUrl()+"失败 ResonseCode="+rscode);
				map.put("success","false");
				*/
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (httpCon != null)
				//httpCon.disconnect();
			if(httpDownCon!=null){
				//httpDownCon.disconnect();
			}
		}
		
		return true;
    }
    /**
     * 根据文件下载地址获取302跳转后的文件下载地址
     * @param urlPath
     * @return
     */
    public static String getDownloadUrl(String urlPath){
    	String downloadUrl = "";
    	URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setInstanceFollowRedirects(false);
			
			conn.connect();
			int resCode = conn.getResponseCode();
			if(resCode==HttpURLConnection.HTTP_MOVED_TEMP){
				downloadUrl = conn.getHeaderField("Location");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null){conn.disconnect();}
		}
		
		return downloadUrl;
    }
    /**
     * 获取fs系统的下载地址
     * @param attachmentVo
     * @return
     */
    public static String getDownloadUrl(String fastDfsHostUrl,AttachmentVo attachmentVo){
    	String downloadUrl = "";
    	String urlpath = fastDfsHostUrl + "/gapServlet?action=fsOpenManageServlet&pm=1";
		URL url = null;
		HttpURLConnection httpCon = null;
		try {
			url = new URL(urlpath);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(5 * 1000);
			httpCon.setReadTimeout(5 * 1000);
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setUseCaches(false);
			httpCon.setAllowUserInteraction(false);
			httpCon.setRequestProperty("Content-type", "text/html;charset=UTF-8");
			httpCon.setRequestProperty("requesttype", "download");
			httpCon.setRequestProperty("fid",setValue(attachmentVo.getFileId()));
			httpCon.setRequestProperty("fname",setValue(attachmentVo.getFileName()));
			httpCon.setRequestProperty("ftype",setValue(attachmentVo.getFileType()));
//			httpCon.setRequestProperty("fc",setValue(vo.getVersionControl()));
			httpCon.setRequestProperty("fv",String.valueOf(attachmentVo.getFileVersion()));
			httpCon.setRequestProperty("fu",GapFsParam.ACC_USER);
//			httpCon.setRequestProperty("fp",setValue(vo.getProperties()));
			httpCon.setInstanceFollowRedirects(false);
			//httpCon.connect();
			BufferedWriter out = null;
			try {
			  out = new BufferedWriter(new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8"));
			  out.flush();
			}finally {
		       if (out != null)
				 out.close();
			}
			int rscode = httpCon.getResponseCode();
			if(rscode==302){
				downloadUrl = httpCon.getHeaderField("Location");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (httpCon != null) 
				httpCon.disconnect();
		}
		
		return downloadUrl;
    }
    
    public static String setValue(String value){
		if(value==null){
			return "";
		}
		return value;
	}
    /**
     * 保存下载的流到文件
     * @param is
     * @throws Exception 
     */
    private static void saveToFile(InputStream is, AttachmentVo attachmentVo) {
    	BufferedInputStream bis = null;
    	FileOutputStream fos = null;
    	
		try {
			bis = new BufferedInputStream(is);
	    	String webRootPath = CommonUtil.getContextRealPath() + "temp/";
	    	File rootPath = new File(webRootPath);
	    	if(!rootPath.exists()){
	    		rootPath.mkdirs();
	    	}
	    	File attachFile = new File(webRootPath + attachmentVo.getFileId() + "." + attachmentVo.getFileType());
	    	fos = new FileOutputStream(attachFile);
	    	
	    	byte[] bData = new byte[4096];
	    	int bRead = 0;
	    	while((bRead = bis.read(bData, 0, 4096))!=-1){
	    		fos.write(bData, 0, bRead);
	    	}
	    	fos.flush();
	    	fos.close();
	    	attachmentVo.setAttachment(attachFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos!=null){fos.close();}
				if(bis!=null){bis.close();}
				if(is!=null){is.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 清空发邮件生成的临时文件
     * @param attachmentVoList
     * @return
     */
    public static boolean removeTmpFiles(List<AttachmentVo> attachmentVoList){
    	for (AttachmentVo attachmentVo : attachmentVoList) {
			attachmentVo.getAttachment().delete();
		}
    	return true;
    }
	/**
	 * 上传文件
	 * @param uploadUrlpath 上传服务地址
	 * @param downloadUrlpath 下载地址
	 * @param attachmentVo 封装的上传对象
	 * @throws IOException 
	 * @throws Exception 
	 */
    public static String upload(String uploadUrlpath,String downloadUrlpath,AttachmentVo attachmentVo) throws IOException {
		URL url = null;
		HttpURLConnection httpCon = null;
		BufferedWriter out = null;
		OutputStream dos = null;
		InputStream fis = null;
		
		try {
			url = new URL(uploadUrlpath);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(5 * 1000);
			httpCon.setReadTimeout(600 * 1000);
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setUseCaches(false);
			httpCon.setAllowUserInteraction(false);
			httpCon.setRequestProperty("Content-type", "text/html;charset=UTF-8");
			httpCon.setRequestProperty("requesttype", "upload");
			httpCon.setRequestProperty("fname",setValue(attachmentVo.getFileName()));
			httpCon.setRequestProperty("ftype",setValue(attachmentVo.getFileType()));
			httpCon.setRequestProperty("fu",GapFsParam.ACC_USER);
			
			httpCon.connect();
			
			dos = httpCon.getOutputStream();
			fis = (InputStream) downloadFile(downloadUrlpath,attachmentVo);
			int count = 0;
			byte[] buff = new byte[1024 * 8];
			while ((count = fis.read(buff)) != -1) {
				dos.write(buff, 0, count);
			}
			fis.close();
			dos.flush();
			
			int rscode = httpCon.getResponseCode();
			if (rscode == 200) {
				InputStream is = httpCon.getInputStream();
				String returnString = inputStream2String(is);
				return returnString;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (dos != null) dos.close();
		    if (out != null) out.close();
			if (httpCon != null) httpCon.disconnect();
		}
		return "-1";
    }
    
	private static String inputStream2String(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
        return sb.toString(); 
	}
	
	public static InputStream downloadFile(String urlpath,
			AttachmentVo attachmentVo) throws Exception {
		URL url = null;
		HttpURLConnection httpCon = null;
		OutputStream dos = null;
		FileInputStream fis = null;
		InputStream is = null;
		url = new URL(urlpath);
		httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setConnectTimeout(5 * 1000);
		httpCon.setReadTimeout(180 * 1000);
		httpCon.setDoOutput(true);
		httpCon.setDoInput(true);
		httpCon.setRequestMethod("POST");
		httpCon.setUseCaches(false);
		httpCon.setAllowUserInteraction(false);
		httpCon.setRequestProperty("Content-type", "text/html;charset=UTF-8");
		httpCon.setRequestProperty("requesttype", "download");
		httpCon.setRequestProperty("fid",setValue(attachmentVo.getFileId()));
		httpCon.setRequestProperty("fname",setValue(attachmentVo.getFileName()));
		httpCon.setRequestProperty("ftype",setValue(attachmentVo.getFileType()));
		httpCon.setRequestProperty("fv","1");
		httpCon.setRequestProperty("fu",GapFsParam.ACC_USER);
		httpCon.setInstanceFollowRedirects(false);
		//httpCon.connect();
		BufferedWriter out = null;
		try {
		  out = new BufferedWriter(new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8"));
		  out.flush();
		}finally {
	       if (out != null)
			 out.close();
		}
		int rscode = httpCon.getResponseCode();
		if (rscode == 200) {
			is = httpCon.getInputStream();
		}else if(rscode==302){
			String referer=httpCon.getHeaderField("Location");
			url = new URL(referer);
			HttpURLConnection httpDownCon = null;
			httpDownCon=(HttpURLConnection) url.openConnection();
			rscode = httpDownCon.getResponseCode();
			if(rscode==200){
				is = httpDownCon.getInputStream();
			}
		}
		return is;
	}


	/**
	 * 根据文件访问url下载文件并上传至fastdf文件系统
	 * @param fastDfsHostUrl fastdfs文件系统访问路径  必填
	 * @param downUrl  文件下载url地址 必填
	 * @param filename  文件名称（包括后缀） 必填
	 * @param fileId  指定的文件ID 非必填
	 * @return  返回处理结果json串
	 * @throws IOException
	 */
	public static String downAndUpload(String fastDfsHostUrl,String downUrl,String filename, String fileId) throws IOException {
		String urlPath = fastDfsHostUrl + "/gapServlet?action=fsOpenManageServlet&pm=1";
		URL url = null;
		HttpURLConnection conn = null;
		BufferedWriter out = null;
		OutputStream os = null;
		InputStream fis = null;
		String name = CommonUtil.getFilename(filename);
		String type = CommonUtil.getFiletype(filename);

		try {
			url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(600 * 1000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestProperty("Content-type", "text/html;charset=UTF-8");
			conn.setRequestProperty("requesttype", "upload");
			conn.setRequestProperty("fname",name);
			conn.setRequestProperty("ftype",type);
			if(fileId!=null){
				conn.setRequestProperty("fid", fileId);
			}
			conn.setRequestProperty("fu",GapFsParam.ACC_USER);

			conn.connect();

			os = conn.getOutputStream();
			HttpClientHelper httpClient = new HttpClientHelper();
			InputStream downloadIs = httpClient.download(downUrl);
			BufferedInputStream bis = new BufferedInputStream(downloadIs);
			int len = 8 * 1024;
			int byteRead = 0;
			byte[] buf = new byte[len];
			while((byteRead = bis.read(buf, 0, len))!=-1){
				os.write(buf, 0, byteRead);
			}
			os.flush();
			os.close();
			bis.close();
			downloadIs.close();

			int rscode = conn.getResponseCode();
			if (rscode == 200) {
				InputStream is = conn.getInputStream();
				String returnString = inputStream2String(is);
				return returnString;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (os != null) os.close();
			if (out != null) out.close();
			if (conn != null) conn.disconnect();
		}
		return "-1";
	}
}
