package com.zefu.tiol.web.bussiness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.zefu.tiol.util.FileUtils;

/**
   * @工程名 : szyd 解析XML文件
   * @文件名 : XmlParsingBussiness.java
   * @工具包名：com.zefu.tiol.web.bussiness
   * @功能描述: TODO
   * @创建人 ：林鹏
   * @创建时间：2018年11月16日 下午3:22:47
   * @版本信息：V1.0
 */
public class XmlParsingBussiness {
	
	
	
	public static List<File> downloadFile(String urlStr,String savePath, String fileName) {
		List<File> files = new ArrayList<File>();
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			//设置超时间为3秒
			conn.setConnectTimeout(3*1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//得到输入流
			InputStream inputStream = conn.getInputStream(); 
			//获取自己数组
	        byte[] getData = FileUtils.readInputStream(inputStream);  
	        //文件保存位置
	        File fileDir  = new File(savePath);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
	        File file = new File(fileDir + File.separator + fileName + ".zip");    
	        FileOutputStream fos = new FileOutputStream(file);     
	        fos.write(getData); 
	        if(fos!=null){
	            fos.close();  
	        }
	        if(inputStream!=null){
	            inputStream.close();
	        }
	        FileUtils.unZip(fileDir + File.separator + fileName + ".zip");
	        file.delete();
	        //进入
	        File localDir = new File(fileDir + File.separator + fileName);
	        File[] localFiles = localDir.listFiles();
	        for (File data : localFiles) {
	        	files.add(data);
				System.out.println(data.getName());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;  	
	}
	
	
}
