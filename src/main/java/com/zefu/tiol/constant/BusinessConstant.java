package com.zefu.tiol.constant;

import java.io.IOException;
import java.util.Properties;

import com.yr.gap.engine.util.StringUtils;

public class BusinessConstant {
	public final static String HTTP_REQ_HEAD="http://";
	public final static String COLON_COODE=":";
	public final static String UNDERLINE_COODE="_";
	public final static String DOT_CODE=".";
	public final static String FILE_NOTICE = "NOTICE";//会议通知
	public final static String FILE_SUMMARY = "SUMMARY";//会议纪要
	public final static String FILE_SUBJECT = "SUBJECT";//议题材料
	public final static String FILE_OPINION = "OPINION";//听取意见情况
	public final static String FILE_FORMAL = "FORMAL";//正式文件
	public final static String FILE_EVIDENCE = "EVIDENCE";//佐证文件
	
	public final static String PATH_TYPE_REPORT = "REPORT";
	public final static String PATH_TYPE_DOWNLOAD = "DOWNLOAD";
	
	public final static String PATH_URL = "URL";
	public final static String PATH_RESULT = "RESULT";
	public final static String PATH_MAP = "MAP";
	
	/**
	 * 0004下发同步的文件夹名称
	 */
	public final static String SYNC_0004_FILE_NAME="sync";
	
	public final static String XML_SUFFIX="xml";
	public final static String ZIP_SUFFIX="zip";
	/**
	 * 版本号
	 */
	public final static String VERSION="1000";
	/**
	 * 接口编码
	 */
	public final static String API_CODE="SZ01";
	
	/**
	 * 接口地址
	 */
	
	/*public static String uploadUrl="";
	public static String uploadUser="";
	public static String uploadPassword="";
	static {
			// TODO Auto-generated method stub
			Properties properties = new Properties();
			try {
				properties.load(BusinessConstant.class.getResourceAsStream("/platform.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			uploadUrl = StringUtils.isEmpty(properties.getProperty("uploadUrl")) ? ""
					: properties.getProperty("uploadUrl");
			uploadUser = StringUtils.isEmpty(properties.getProperty("uploadUser")) ? ""
					: properties.getProperty("uploadUser");
			uploadPassword = StringUtils.isEmpty(properties.getProperty("uploadPassword")) ? ""
					: properties.getProperty("uploadPassword");

	}
	*/
	
}
